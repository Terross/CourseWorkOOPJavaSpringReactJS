import Button from '@material-ui/core/Button';
import { makeStyles } from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';
import React, {useState} from 'react'
import axios from 'axios';
import HeaderComponent from './HeaderComponent';
import { useHistory, useLocation } from 'react-router-dom';
import Typography from '@material-ui/core/Typography';
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

export default function Pi(props) {
	const [pi, setPi] = useState('');
	React.useEffect(() => {
		axios.get("http://localhost:8080/api/v1/pi").then(data => {
			console.log(data.data["message"]);
			setPi(data.data["message"]);
			console.log("Pi:" + pi);
		});
	}, [])
    return (
        <Container maxWidth={2440}>
        
            <HeaderComponent title="Pi"></HeaderComponent>
            <Container align="center">
            
            <Typography>
				{pi}
			</Typography>
            </Container>
        </Container>
    );
}
