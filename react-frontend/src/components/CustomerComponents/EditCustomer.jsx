import Button from '@material-ui/core/Button';
import { makeStyles } from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';
import React, {useState} from 'react'
import axios from 'axios';
import HeaderComponent from '../HeaderComponent';
import { useHistory, useLocation } from 'react-router-dom';

import { Container } from '@material-ui/core';



const CUSTOMER_API_BASE_URL = "http://localhost:8080/api/v1/customer";
const useStyles = makeStyles((theme) => ({
    root: {
      '& > *': {
        margin: theme.spacing(1),
        width: '25ch',
      },
    },
  }));
  
function EditCustomer(props) {
    const history = useHistory();
    const classes = useStyles();
    const [firstName, setFirstName] = useState('');
    const [secondName, setSecondName] = useState('');
	const [adress, setAdress] = useState('');
	const [firstNameValidation,setFirstNameValidation] = useState(false);
    const [secondNameValidation,setSecondNameValidation] = useState(false);
	const [adressValidation,setAdressValidation] = useState(false);
	
    const cancelClick = () => {
        history.push("/Customers");
	}
	
    const getCustomerById=(id)=>{
        axios.get(CUSTOMER_API_BASE_URL+'/'+id).then(data=>{
        	let Customer = data.data;
			setFirstName(Customer.firstName);
			setSecondName(Customer.secondName);
			setAdress(Customer.salary);
        })
        
	}
	
    React.useEffect(() => {
        let id = props.match.params.id;
        getCustomerById(id);
	  }, []);
	  
    const acceptClick=()=>{
		setFirstNameValidation(false);
        setSecondNameValidation(false);
        setAdressValidation(false);
        const Customer = {
            firstName: firstName,
            secondName: secondName,
            adress: adress
        }
        const id = props.match.params.id;
        axios.put(CUSTOMER_API_BASE_URL + '/' + id, Customer).then(data =>{
			if(data.data["message"] == "Wrong fields"){
				if(data.data["wrongFields"].includes("adress")) {setAdressValidation(true);}
				if(data.data["wrongFields"].includes("secondName")) {setSecondNameValidation(true);}
				if(data.data["wrongFields"].includes("firstName")) {setFirstNameValidation(true);}
			}
			if(data.data["message"] == "Success") {
				history.push("/Customers");
			}	
        });
    }

  
    const id = props.match.params.id;
    let emp ;
    props.location.state.stateCustomers.forEach(element => {
      if(element.id==id) {
        emp = element;
      }
    });
   
    return (
        <Container maxWidth={2440}>
        
            <HeaderComponent title="Edit Customer"></HeaderComponent>
            <Container align="center">
            
            <form className={classes.root} noValidate autoComplete="off" >
            <div style={{marginTop:"20px"}}>
            <TextField id="standard-basic" label="First name"
				defaultValue={emp.firstName}
				error={firstNameValidation}
				onChange={e => {
					setFirstName(e.target.value);
					setFirstNameValidation(false);
				}}
			/>
            </div>
            <div>
            <TextField id="standard-basic" label="Second name" 
				defaultValue={emp.secondName}
				error={secondNameValidation}
				onChange={e => {
					setSecondName(e.target.value);
					setSecondNameValidation(false);
				}}
			/>
            </div>
            <div>
            <TextField id="standard-basic" label="Adress" 
				defaultValue={emp.adress}
				error={adressValidation}
				onChange={e => {
					setAdress(e.target.value);
					setAdressValidation(false);
				}}
			/>
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
export default  EditCustomer;
