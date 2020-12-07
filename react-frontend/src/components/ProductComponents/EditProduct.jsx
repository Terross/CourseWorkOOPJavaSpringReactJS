import Button from '@material-ui/core/Button';
import { makeStyles } from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';
import React, {useState} from 'react'
import axios from 'axios';
import HeaderComponent from '../HeaderComponent';
import { useHistory, useLocation } from 'react-router-dom';

import { Container } from '@material-ui/core';

const PRODUCT_API_BASE_URL = "http://localhost:8080/api/products";
const useStyles = makeStyles((theme) => ({
    root: {
      '& > *': {
        margin: theme.spacing(1),
        width: '25ch',
      },
    },
  }));

export default function EditProduct(props) {
    const history = useHistory();
    const classes = useStyles();
    const [name, setName] = useState('');
    const [price, setPrice] = useState('');
	const [count, setCount] = useState('');
	const [nameValidation,setNameValidation] = useState(false);
    const [priceValidation,setPriceValidation] = useState(false);
	const [countValidation,setCountValidation] = useState(false);

    const cancelClick = () => {
        history.push("/products");
	}
	
    const getProductById=(id)=>{
        axios.get(PRODUCT_API_BASE_URL+'/'+id).then(data=>{
        	let product = data.data;
			setName(product.name);
			setPrice(product.price);
			setCount(product.count);
        }) 
	}
	
    React.useEffect(() => {
        let id = props.match.params.id;
        getProductById(id);
	  }, []);
	  
    const acceptClick=()=>{

		setNameValidation(false);
        setPriceValidation(false);
        setCountValidation(false);
        const product = {
            name: name,
            price: price,
            count: count
		}
        const id = props.match.params.id;
        axios.put(PRODUCT_API_BASE_URL + '/' + id, product).then(data =>{
			
			if(data.data["message"] == "Wrong fields"){
				if(data.data["wrongFields"].includes("name")) {setNameValidation(true);}
				if(data.data["wrongFields"].includes("price")) {setPriceValidation(true);}
				if(data.data["wrongFields"].includes("count")) {setCountValidation(true);}
			}
			if(data.data["message"] == "Success"){
				history.push("/products");
			}
		});
    }
    const id = props.match.params.id;
    console.log(id);
    let prod ;
    props.location.state.stateProducts.forEach(element => {
      if(element.id==id) {
        prod = element;
      }
    });

    return (
        <Container maxWidth={2440}>
        
            <HeaderComponent title="Edit Product"></HeaderComponent>
            <Container align="center">
            
            <form className={classes.root} noValidate autoComplete="off" >
            <div style={{marginTop:"20px"}}>
            <TextField id="standard-basic"
						label="Name"
						error = {nameValidation}
						defaultValue={prod.name}
             			onChange={e => {
							setName(e.target.value);
							setNameValidation(false);
						}}/>
            </div>
            <div>
            <TextField id="standard-basic"
						label="Price" 
						error={priceValidation}
						defaultValue={prod.price}
            			onChange={e => {
							setPrice(e.target.value);
							setPriceValidation(false);
						}}/>
            </div>
            <div>
            <TextField id="standard-basic"
						label="Count" 
						error={countValidation}
						defaultValue={prod.count}
            			onChange={e => {
							setCount(e.target.value);
							setCountValidation(false);
						}}/>
            <Button variant="contained" color="primary" style={{marginLeft:"10px",
            marginTop:"10px"}}
            onClick={acceptClick}
            >
            Accept
            </Button>
            <Button variant="contained" color="secondary" style={{marginLeft:"10px",
            marginTop:"10px"}}
            onClick={cancelClick}
            >
            Cancel
            </Button>
            </div>
            </form>
            </Container>
        </Container>
    );
}

