import Button from '@material-ui/core/Button';
import { makeStyles } from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';
import React, {useState} from 'react'
import axios from 'axios';
import HeaderComponent from '../HeaderComponent';
import { useHistory, useLocation } from 'react-router-dom';

import { Container } from '@material-ui/core';



const EMPLOYEE_API_BASE_URL = "http://localhost:8080/api/v1/employee";
const useStyles = makeStyles((theme) => ({
    root: {
      '& > *': {
        margin: theme.spacing(1),
        width: '25ch',
      },
    },
  }));
function EditEmployee(props) {
    const history = useHistory();
    const classes = useStyles();
    const [employeeState, setEmployeeState] = useState('');
    const [firstName, setFirstName] = useState('');
    const [secondName, setSecondName] = useState('');
    const [salary, setSalary] = useState('');
    const cancelClick = () => {
        history.push("/employees");
    }
    const getEmployeeById=(id)=>{
        axios.get(EMPLOYEE_API_BASE_URL+'/'+id).then(data=>{
            let employee = data.data;
            
           setFirstName(employee.firstName);
           setSecondName(employee.secondName);
           setSalary(employee.salary);
        })
        
    }
    React.useEffect(() => {
        let id = props.match.params.id;
        getEmployeeById(id);
      }, []);
    const acceptClick=()=>{
        const employee = {
            firstName: firstName,
            secondName: secondName,
            salary: salary
        }
        const id = props.match.params.id;
        axios.put(EMPLOYEE_API_BASE_URL + '/' + id, employee);
        
        history.push("/employees");
        
    }

  
    const id = props.match.params.id;
    console.log(id);
    let emp ;
    props.location.state.stateEmployee.forEach(element => {
      
      if(element.id==id) {
        console.log("element: "+ element.id);
        console.log("id :" + id);
        emp = element;
  
      }
    });
   console.log(props.location.state.stateEmployee);
   
    return (
        <Container maxWidth={2440}>
        
            <HeaderComponent title="Edit Employee"></HeaderComponent>
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
            <TextField id="standard-basic" label="Salary" 
            defaultValue={emp.salary}
            onChange={e => setSalary(e.target.value)}/>
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

export default EditEmployee;