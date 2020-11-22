import Button from '@material-ui/core/Button';
import { makeStyles } from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';
import React, {useState} from 'react'
import axios from 'axios';

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
    const [firstNameValidation,setFirstNameValidation] = useState(false);
    const [secondNameValidation,setSecondNameValidation] = useState(false);
    const [adressValidation,setAdressValidation] = useState(false);

    const acceptClick=()=>{
        setFirstNameValidation(false);
        setSecondNameValidation(false);
        setAdressValidation(false);
        const customer = {
            firstName: firstName,
            secondName: secondName,
            adress: adress
		}
		
		axios.post("http://localhost:8080/api/v1/customer", customer).then(data =>{
			if(data.data["message"] == "Wrong fields"){
				if(data.data["wrongFields"].includes("adress")) {setAdressValidation(true);}
				if(data.data["wrongFields"].includes("secondName")) {setSecondNameValidation(true);}
				if(data.data["wrongFields"].includes("firstName")) {setFirstNameValidation(true);}
			}
			props.getCustomers();
        });
    }
    
    return (
        <div className="container" align="center" style={{margin:"10px"}}>
            <h2>Add Customer</h2>
            <form className={classes.root} noValidate autoComplete="off" >
            <div>
            <TextField error={firstNameValidation} id="standard-basic" label="First name"
            	onChange={e => {
					setFirstName(e.target.value);
					setFirstNameValidation(false);
             	}}/>
            </div>
            <div>
            <TextField error={secondNameValidation} id="standard-basic" label="Second name" 
            	onChange={e => {
					setSecondName(e.target.value);
					setSecondNameValidation(false);
                }}/>
            </div>
            <div>
            <TextField error={adressValidation} id="standard-basic" label="Adress" 
            	onChange={e => {
					setAdress(e.target.value);
					setAdressValidation(false);
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

export default AddCustomer;
