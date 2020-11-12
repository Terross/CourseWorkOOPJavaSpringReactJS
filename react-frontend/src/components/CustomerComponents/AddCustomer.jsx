import Button from '@material-ui/core/Button';
import { makeStyles } from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';
import React, {useState} from 'react'
import axios from 'axios';


const CUSTOMER_API_BASE_URL = "http://localhost:8080/api/v1/сustomer";
const useStyles = makeStyles((theme) => ({
    root: {
      '& > *': {
        margin: theme.spacing(1),
        width: '25ch',
      },
    },
  }));
function AddCustomer(props) {
 
    const classes = useStyles();
    const [firstName, setFirstName] = useState('');
    const [secondName, setSecondName] = useState('');
    const [adress, setAdress] = useState('');
    
    const acceptClick=()=>{
        const customer = {
            firstName: firstName,
            secondName: secondName,
            adress: adress
        }
        console.log(customer)
          axios.post("http://localhost:8080/api/v1/customer", customer).then(data =>{
          props.getCustomers();
        })
        
    }
    
    return (
        <div className="container" align="center" style={{margin:"10px"}}>
            <h2>Add Customer</h2>
            <form className={classes.root} noValidate autoComplete="off" >
            <div>
            <TextField id="standard-basic" label="First name"
             onChange={e => setFirstName(e.target.value)}/>
            </div>
            <div>
            <TextField id="standard-basic" label="Second name" 
            onChange={e => setSecondName(e.target.value)}/>
            </div>
            <div>
            <TextField id="standard-basic" label="Adress" 
            onChange={e => setAdress(e.target.value)}/>
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

export default AddCustomer;
