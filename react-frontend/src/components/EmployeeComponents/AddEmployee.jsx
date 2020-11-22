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

function AddEmployee(props) {
 
    const classes = useStyles();
    const [firstName, setFirstName] = useState('');
    const [secondName, setSecondName] = useState('');
    const [salary, setSalary] = useState('');
    const [firstNameValidation,setFirstNameValidation] = useState(false);
    const [secondNameValidation,setSecondNameValidation] = useState(false);
	const [salaryValidation,setSalaryValidation] = useState(false);
	
    const acceptClick=()=>{
		setFirstNameValidation(false);
        setSecondNameValidation(false);
        setSalaryValidation(false);
        const employee = {
            firstName: firstName,
            secondName: secondName,
            salary: salary
        }
        
        axios.post("http://localhost:8080/api/v1/employee", employee).then(data =>{
			if(data.data["message"] == "Wrong fields"){
				if(data.data["wrongFields"].includes("salary")) {setSalaryValidation(true);}
				if(data.data["wrongFields"].includes("secondName")) {setSecondNameValidation(true);}
				if(data.data["wrongFields"].includes("firstName")) {setFirstNameValidation(true);}
			}
			props.getEmployee();
        })
        
    }
    
    return (
        <div className="container" align="center" style={{margin:"10px"}}>
            <h2>Add employee</h2>
            <form className={classes.root} noValidate autoComplete="off" >
            <div>
			<TextField id="standard-basic"
						label="First name"
						error = {firstNameValidation}
             			onChange={e => {
							setFirstName(e.target.value);
							setFirstNameValidation(false);
						}}/>
            </div>
            <div>
			<TextField id="standard-basic"
						label="Second name" 
						error={secondNameValidation}
            			onChange={e => {
							setSecondName(e.target.value);
							setSecondNameValidation(false);
						}}/>
            </div>
            <div>
			<TextField id="standard-basic"
						label="Salary" 
						error={salaryValidation}
            			onChange={e => {
							setSalary(e.target.value);
							setSalaryValidation(false);
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

export default AddEmployee;