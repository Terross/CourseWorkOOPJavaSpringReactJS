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
import PDF from '../../PdfReports/orders.pdf'
import { useHistory } from 'react-router-dom';
import HeaderComponent from '../HeaderComponent';
import IconButton from '@material-ui/core/IconButton';
import DeleteIcon from '@material-ui/icons/Delete';
import EditIcon from '@material-ui/icons/Edit';
import { Container } from '@material-ui/core';
const columns = [
  { id: 'id', label: 'Id', minWidth: 170 },
  { id: 'totalOrderPrice', label: 'Price', minWidth: 170},
];
const ORDERS_API_BASE_URL = "http://localhost:8080/api/orders";

const useStyles = makeStyles({
  root: {
    width: '100%',
  },
  container: {
    maxHeight: 440,
  },
});

export default function OrdersListComponent() {
  const classes = useStyles();
  const [ordersState, setOrdersState] = React.useState([])
  const [page, setPage] = React.useState(0);
  const [rowsPerPage, setRowsPerPage] = React.useState(5);
  let history = useHistory();
  

  const getOrders=()=>{
      axios.get(ORDERS_API_BASE_URL).then(data=>{
		setOrdersState(data.data);
		console.log(data.data);
      });
      
  }

  React.useEffect(() => {
    getOrders();
	}, []);

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(+event.target.value);
    setPage(0);
  };
  
  const deleteOrder = id => {
     axios.delete(ORDERS_API_BASE_URL +'/'+id).then(data => {
       getOrders();
     })
     
  }
 
  
  return (
    <Container fixed={false} maxWidth={2440}>
      <HeaderComponent title={"Orders"} PDF={PDF}></HeaderComponent>
      <Paper className={classes.root}>
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
                  
          {ordersState.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage).map((order) => {
            
            return (
              
              <TableRow hover  tabIndex={-1} key={order.id}>
                
                {columns.map((column) => {
                  const value = order[column.id];
                 
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
                        onClick={() =>deleteOrder(order.id)}
                        >
                        <DeleteIcon fontSize="inherit" />
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
      count={ordersState.length}
      rowsPerPage={rowsPerPage}
      page={page}
      onChangePage={handleChangePage}
      onChangeRowsPerPage={handleChangeRowsPerPage}
      />
  
      </TableContainer>

    </Paper>
    </Container>
    
  );
}