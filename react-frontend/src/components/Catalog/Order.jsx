import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Paper from '@material-ui/core/Paper';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TablePagination from '@material-ui/core/TablePagination';
import TableRow from '@material-ui/core/TableRow';
import axios from 'axios';
import {Button, Grid } from '@material-ui/core';
import ProductCard from './ProductCard';
import { useHistory } from 'react-router-dom';
import HeaderComponent from '../HeaderComponent';
import IconButton from '@material-ui/core/IconButton';
import DeleteIcon from '@material-ui/icons/Delete';
import EditIcon from '@material-ui/icons/Edit';
import { Container } from '@material-ui/core';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemAvatar from '@material-ui/core/ListItemAvatar';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemSecondaryAction from '@material-ui/core/ListItemSecondaryAction';
import ListItemText from '@material-ui/core/ListItemText';

const useStyles = makeStyles((theme) => ({
    root: {
      flexGrow: 1,
      maxWidth: 752,
    },
    demo: {
      backgroundColor: theme.palette.background.paper,
    },
    title: {
      margin: theme.spacing(4, 0, 2),
    },
  }));

export default function Order(props) {
    const classes = useStyles();
    let map = new Map();
    let unicProducts = [];
    
    const addOrder = () => {
        console.log(map);
        let productOrders =[];
        unicProducts.forEach(product => {
            let productOrder = {
                product : {
                    id: product.id,
                    name: product.name,
                    price: product.price
                },
                quantity: map.get(product)
            }
            productOrders.push(productOrder);
        });
        let order ={
            productOrders: productOrders
        }
        console.log(order);
        axios.post("http://localhost:8080/api/orders",order);
    }
    
    return(
        
    <Paper align="center">
        
        <h2>Order card</h2>
        <List >
            {
                props.orderState.forEach(element => {
                    if(!unicProducts.includes(element)){
                        unicProducts.push(element);
                    }
                })
            }
            {
                props.orderState.forEach(product => {
                    if(map.has(product)){
                        map.set(product,map.get(product) +1);
                    } else{
                        map.set(product,1);
                    }
                })
            }
            {
                unicProducts.map((product)=>{
                                
                    return(
                        <ListItem >
                            <ListItemText align="center" primary={product.name +" x" +map.get(product)}>

                            </ListItemText>
                        </ListItem>
                                
                    )
                    })
            }
        </List>
        <Button variant="contained"
                    color="primary"
                    onClick={()=> addOrder()}
                    style={{marginBottom:"15px"}}>
                Pay
            </Button>
    </Paper>
    )
}