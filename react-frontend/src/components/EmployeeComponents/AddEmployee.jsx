import Button from '@material-ui/core/Button';
import { makeStyles } from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';
import React, {useState} from 'react'
import axios from 'axios';


const EMPLOYEE_API_BASE_URL = "http://localhost:8080/api/v1/employee";
const useStyles = makeStyles((theme) => ({
    root: {
      '& > *': {
        margin: theme.spacing(1),
        width: '25ch',
      },
    },
  }));
function AddEmployee(props) {
 
    const classes = useStyles();
    const [firstName, setFirstName] = useState('');
    const [secondName, setSecondName] = useState('');
    const [salary, setSalary] = useState('');
    
    const acceptClick=()=>{
        const employee = {
            firstName: firstName,
            secondName: secondName,
            salary: salary
        }
        
        axios.post(EMPLOYEE_API_BASE_URL, employee).then(data =>{
          props.getEmployee();
        })
        
    }
    
    return (
        <div className="container" align="center" style={{margin:"10px"}}>
            <h2>Add employee</h2>
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
            <TextField id="standard-basic" label="Salary" 
            onChange={e => setSalary(e.target.value)}/>
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

export default AddEmployee;