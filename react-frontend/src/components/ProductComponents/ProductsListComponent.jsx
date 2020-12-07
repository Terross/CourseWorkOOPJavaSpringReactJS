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
import {Grid } from '@material-ui/core';
import PDF from '../../PdfReports/products.pdf'
import { useHistory } from 'react-router-dom';
import AddProducts from './AddProduct';
import HeaderComponent from '../HeaderComponent';
import IconButton from '@material-ui/core/IconButton';
import DeleteIcon from '@material-ui/icons/Delete';
import EditIcon from '@material-ui/icons/Edit';
import { Container } from '@material-ui/core';
const columns = [
  { id: 'name', label: 'Name', minWidth: 170 },
  { id: 'price', label: 'Price', minWidth: 100 },
  {id : 'count', label: 'Count', minWidth:100},
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

export default function ProductsListComponent() {
  const classes = useStyles();
  const [stateProducts, setProductsState] = React.useState([])
  const [page, setPage] = React.useState(0);
  const [rowsPerPage, setRowsPerPage] = React.useState(5);
  let history = useHistory();
  

  const getProducts=()=>{
      axios.get(PRODUCTS_API_BASE_URL).then(data=>{
          setProductsState(data.data);
          
      })
      
  }
  React.useEffect(() => {
    getProducts();
}, [])
  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(+event.target.value);
    setPage(0);
  };
  
  

  const editProduct = id => {
    
    history.push({
      pathname: `/edit-product/${id}`,
      state:{stateProducts}
      }
    )
    getProducts();
    
  }
  const deleteProducts = id => {
    
     axios.delete(PRODUCTS_API_BASE_URL +'/'+id).then(data => {
       getProducts();
     })
     
  }
 
  
  return (
    <Container fixed={false} maxWidth={2440}>
      <HeaderComponent title={"Products"} PDF={PDF}></HeaderComponent>
      <Paper className={classes.root}>
      
      <Grid container algin="center" alignItems="center" direction="row" spacing={16}>
        <Grid item xs={3}>
          <AddProducts getProducts={() => getProducts()} ></AddProducts>
        </Grid>
        <Grid item xs={9}>
        <TableContainer className={classes.container} >
      
      <Table stickyHeader aria-label="caption table">
          <TableRow>
            
          </TableRow>
        <TableHead>

          <TableRow>
    
            {columns.map((column) => (
              <TableCell
                key={column.id}
                align={column.align}
                style={{ minWidth: column.minWidth }}
              >
                {column.label}
              </TableCell>
            ))}
            <TableCell align = "center">
              <span>Actions</span>
            </TableCell>
          </TableRow>

        </TableHead>
        <TableBody>
                  
          {stateProducts.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage).map((prod) => {
            
            return (
              
              <TableRow hover  tabIndex={-1} key={prod.id}>
                
                {columns.map((column) => {
                  const value = prod[column.id];
                 
                  return (
                  <>
                    <TableCell key={column.id} align={column.align}>
                      {value}
                    </TableCell>
                    
                      
                    </>
                    
                  );
                })}
                <TableCell align="center">
                
                      <IconButton aria-label="delete"
                       className={classes.margin}
                        size="small"
                        onClick={() =>deleteProducts(prod.id)}
                        >
                        <DeleteIcon fontSize="inherit" />
                      </IconButton>
                      <IconButton aria-label="edit"
                       className={classes.margin}
                        size="small"
                        onClick={() => editProduct(prod.id)}>
                        <EditIcon  fontSize="inherit" />
                      </IconButton>
                    </TableCell>
              </TableRow>
            );
          })}

        </TableBody>
        
      </Table>
      
      <TablePagination
      rowsPerPageOptions={[5, 25, 100]}
      component="div"
      count={stateProducts.length}
      rowsPerPage={rowsPerPage}
      page={page}
      onChangePage={handleChangePage}
      onChangeRowsPerPage={handleChangeRowsPerPage}
      />
  
      </TableContainer>
  
        </Grid>
      </Grid>
      
        
    </Paper>
    </Container>
    
  );
}