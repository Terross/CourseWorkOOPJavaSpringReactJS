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
import Order from './Order';
const columns = [
  { id: 'name', label: 'Name', minWidth: 170 },
  { id: 'price', label: 'Price', minWidth: 100 },
];
const PRODUCTS_API_BASE_URL = "http://localhost:8080/api/products";

const useStyles = makeStyles({
  root: {
    width: '100%',
  },
  container: {
    maxHeight: 440,
  },
});

export default function ProductsList(props) {
    const classes = useStyles();
    const [stateProducts, setProductsState] = React.useState([]);
    const [orderState, setOrderState] = React.useState([]);
    const [page, setPage] = React.useState(0);
    const [rowsPerPage, setRowsPerPage] = React.useState(5);
    let history = useHistory();
    

    const getProducts=()=>{
        axios.get(PRODUCTS_API_BASE_URL).then(data=>{
            setProductsState(data.data);

        })
        
    }
    const test = (product)=>{
        console.log("before add:" + orderState);
        setOrderState([...orderState,product]);
        
    }
    React.useEffect(() => {
        getProducts();
    }, [])
    return(
        <Container maxWidth={2440}>
            <HeaderComponent title="Catalog"></HeaderComponent>
            <Grid container>
                <Grid item  xs={9}>
                    <Grid container>
                    {
                        stateProducts.map((product)=>{
                        
                            
                            return(
                            <Grid item>
                                <ProductCard product = {product}
                                orderState={orderState}
                                setOrderState={setOrderState}></ProductCard>
                            </Grid>)
                            
                        })
                    }
                    </Grid>  
                </Grid>
                <Grid item xs={3}>
                    <Order orderState={orderState}>

                    </Order>
                </Grid>
            </Grid>
            {console.log(orderState)}
            
            
            
        </Container>
    )
}