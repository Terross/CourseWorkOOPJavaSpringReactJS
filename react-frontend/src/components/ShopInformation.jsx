import Button from '@material-ui/core/Button';
import { makeStyles } from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';
import React, {useEffect, useState} from 'react'
import axios from 'axios';
import HeaderComponent from './HeaderComponent';
import { useHistory, useLocation } from 'react-router-dom';
import Typography from '@material-ui/core/Typography';
import { Container, Grid } from '@material-ui/core';
import { Update } from '@material-ui/icons';

const useStyles = makeStyles((theme) => ({
    root: {
      '& > *': {
        margin: theme.spacing(1),
        width: '25ch',
      },
    },
  }));

export default function ShopInformation(props) {
	const [directorNameValidation, setDirectorNameValidation] = React.useState('');
	const [adressValidation, setAdressValidation] = React.useState('')
	const [nameValidation, setNameValidation] = React.useState('')
	const [state, setstate] = useState(false);
	const [directorName, setDirectorName] = React.useState('');
	const [adress, setAdress] = React.useState('')
	const [name, setName] = React.useState('')
	const [specialization, setSpecialization] = React.useState('')
	const [directorNameField, setDirectorNameField] = React.useState('');
	const [adressField, setAdressField] = React.useState('')
	const [nameField, setNameField] = React.useState('')
	const [specializationField, setSpecializationField] = React.useState('')
	const addClick = () => {
		const shop = {
            name: name,
			directorName: directorName,
			adress: adress,
			specialization: specialization
        }
        
        axios.post("http://localhost:8080/api/shop", shop);
	}
	const updateClick = () => {
		const shop = {
            name: name,
			directorName: directorName,
			adress: adress,
			specialization: specialization
        }
        
		axios.put("http://localhost:8080/api/shop", shop);
		window.location.reload();
	}
	React.useEffect(() => {
		axios.get("http://localhost:8080/api/shop").then(data => {
			var arr = new Array(data.data[0]);
			
			if(data.data[0] != undefined){
				setstate(true);
				console.log(data.data[0].adress);
				setDirectorNameField(data.data[0].directorName);
				setAdressField(data.data[0].adress);
				setNameField(data.data[0].name);
				setSpecializationField(data.data[0].specialization);
			}
			
		});
	}, [])
    return (
        <Container maxWidth={2440}>
        
            <HeaderComponent title="Shop"></HeaderComponent>
			<Grid container>
				<Grid item xs={3}>
				<div className="container" align="center" style={{margin:"10px"}}>
				<h2>Update Shop</h2>
				<form  noValidate autoComplete="off" >
				<div>
				<TextField id="standard-basic"
							label="Director name"
					
							onChange={e => {
								setDirectorName(e.target.value);
							}}/>
				</div>
				<div>
				<TextField id="standard-basic" 
						label="Adress"
						
						onChange={e => {
								setAdress(e.target.value);
							}}/>
				</div>
				<div>
				<TextField id="standard-basic" 
						label="Name"
						
						onChange={e => {
								setName(e.target.value);
							}}/>
				</div>
				<div>
				<TextField id="standard-basic" 
						label="Specialization"
						
						onChange={e => {
								setSpecialization(e.target.value);
							}}/>
				</div>
				<Button variant="contained" color="primary" style={{marginLeft:"10px",
																	marginTop:"10px"}}
				onClick={addClick}
				disabled={state}
				>
				Add
				</Button>
				<Button variant="contained" color="primary" style={{marginLeft:"10px",
																	marginTop:"10px"}}
				onClick={updateClick}
				>
				Update
				</Button>
				</form>
				
				
				</div>
				</Grid>
				<Grid item xs={9}>
				<Container align="center">
            
            <Typography style={{marginTop:"30px"}} variant="h5" >
				Director name: {directorNameField}
			</Typography>
			<Typography style={{marginTop:"30px"}} variant="h5" >
				Adress: {adressField}
			</Typography>
			<Typography style={{marginTop:"30px"}} variant="h5" >
				Name: {nameField}
			</Typography>
			<Typography style={{marginTop:"30px"}} variant="h5" >
				Specialization: {specializationField}
			</Typography>
            </Container>
				</Grid>
			</Grid>
			
            
        </Container>
    );
}
