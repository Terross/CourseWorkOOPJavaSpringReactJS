import Button from '@material-ui/core/Button';
import { makeStyles } from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';
import React, {useState} from 'react'
import axios from 'axios';


const PRODUCT_API_BASE_URL = "http://localhost:8080/api/products";
const useStyles = makeStyles((theme) => ({
    root: {
      '& > *': {
        margin: theme.spacing(1),
        width: '25ch',
      },
    },
  }));
function AddProduct(props) {
 
    const classes = useStyles();
    const [nameState, setNameState] = useState('');
	const [priceState, setPriceState] = useState('');
	const [countState, setCountState] = useState('');
	const [nameStateValidation, setNameStateValidation] = useState(false);
	const [priceStateValidation, setPriceStateValidation] = useState(false);
	const [countStateValidation, setCountStateValidation] = useState(false);
    
    const acceptClick=()=>{
		setNameStateValidation(false);
		setPriceStateValidation(false);

        const product = {
            name: nameState,
			price: priceState,
			count: countState
        }
        
        axios.post(PRODUCT_API_BASE_URL, product).then(data =>{
			console.log(data.data);
			if(data.data["message"] == "Wrong fields"){
				if(data.data["wrongFields"].includes("name")) {setNameStateValidation(true);}
				if(data.data["wrongFields"].includes("price")) {setPriceStateValidation(true);}
				if(data.data["wrongFields"].includes("count")) {setCountStateValidation(true);}
			}
          	props.getProducts();
        })
        
    }
    
    return (
        <div className="container" align="center" style={{margin:"10px"}}>
            <h2>Add product</h2>
            <form className={classes.root} noValidate autoComplete="off" >
            <div>
			<TextField id="standard-basic"
						label="Name"
						error={nameStateValidation}
             			onChange={e => {
							setNameState(e.target.value);
							setNameStateValidation(false);
						}}/>
            </div>
            <div>
			<TextField id="standard-basic" 
					   label="Price"
					   error={priceStateValidation}
            		   onChange={e => {
						   setPriceState(e.target.value);
						   setPriceStateValidation(false);
						}}/>
            </div>
			<div>
			<TextField id="standard-basic" 
					   label="Count"
					   error={countStateValidation}
            		   onChange={e => {
						   setCountState(e.target.value);
						   setCountStateValidation(false);
						}}/>
            </div>
            <Button variant="contained" color="primary" style={{marginLeft:"10px"}}
            onClick={acceptClick}
            >
            Accept
            </Button>
            </form>
            
            
        </div>
        
        
    );
}

export default AddProduct;