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
    
    
    const acceptClick=()=>{
        const product = {
            name: nameState,
            price: priceState
        }
        
        axios.post(PRODUCT_API_BASE_URL, product).then(data =>{
          props.getProducts();
        })
        
    }
    
    return (
        <div className="container" align="center" style={{margin:"10px"}}>
            <h2>Add product</h2>
            <form className={classes.root} noValidate autoComplete="off" >
            <div>
            <TextField id="standard-basic" label="Name"
             onChange={e => setNameState(e.target.value)}/>
            </div>
            <div>
            <TextField id="standard-basic" label="Price" 
            onChange={e => setPriceState(e.target.value)}/>
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