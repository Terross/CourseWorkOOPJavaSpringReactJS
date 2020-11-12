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
        const Customer = {
            firstName: firstName,
            secondName: secondName,
            adress: adress
        }
        const id = props.match.params.id;
        axios.put(CUSTOMER_API_BASE_URL + '/' + id, Customer);
        
        history.push("/Customers");
        
    }

  
    const id = props.match.params.id;
    console.log(id);
    let emp ;
    props.location.state.stateCustomers.forEach(element => {
      
      if(element.id==id) {
        console.log("element: "+ element.id);
        console.log("id :" + id);
        emp = element;
  
      }
    });
   console.log(props.location.state.stateCustomer);
   
    return (
        <Container maxWidth={2440}>
        
            <HeaderComponent title="Edit Customer"></HeaderComponent>
            <Container align="center">
            
            <form className={classes.root} noValidate autoComplete="off" >
            <div style={{marginTop:"20px"}}>
            <TextField id="standard-basic" label="First name"
             defaultValue={emp.firstName}
             
             onChange={e => setFirstName(e.target.value)}/>
            </div>
            <div>
            <TextField id="standard-basic" label="Second name" 
            defaultValue={emp.secondName}
            onChange={e => setSecondName(e.target.value)}/>
            </div>
            <div>
            <TextField id="standard-basic" label="Adress" 
            defaultValue={emp.adress}
            onChange={e => setAdress(e.target.value)}/>
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
