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

import { useHistory } from 'react-router-dom';
import AddEmployee from './AddEmployee';
import HeaderComponent from '../HeaderComponent';
import IconButton from '@material-ui/core/IconButton';
import DeleteIcon from '@material-ui/icons/Delete';
import EditIcon from '@material-ui/icons/Edit';
import { Container } from '@material-ui/core';
const columns = [
  { id: 'firstName', label: 'First Name', minWidth: 170 },
  { id: 'secondName', label: 'Second Name', minWidth: 100 },
  { id: 'salary', label: 'Salary',minWidth: 170},
  
  
];
const EMPLOYEE_API_BASE_URL = "http://localhost:8080/api/v1/employee";

const useStyles = makeStyles({
  root: {
    width: '100%',
  },
  container: {
    maxHeight: 440,
  },
});

export default function EmployeeListComponent() {
  const classes = useStyles();
  const [stateEmployee, setEmployeeState] = React.useState([])
  const [page, setPage] = React.useState(0);
  const [rowsPerPage, setRowsPerPage] = React.useState(5);
  let history = useHistory();
  

  const getEmployee=()=>{
      axios.get(EMPLOYEE_API_BASE_URL).then(data=>{
          setEmployeeState(data.data);
          console.log(stateEmployee)
      })
      
  }
  React.useEffect(() => {
    getEmployee();
}, [])
  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(+event.target.value);
    setPage(0);
  };
  
  

  const editEmployee = id => {
    
    history.push({
      pathname: `/edit-employee/${id}`,
      state:{stateEmployee}
      }
    )
    getEmployee();
    
  }
  const deleteEmployee = id => {
    
     axios.delete(EMPLOYEE_API_BASE_URL +'/'+id).then(data => {
       getEmployee();
     })
     
  }
 
  
  return (
    <Container fixed={false} maxWidth={2440}>
      <HeaderComponent title={"Employees"}></HeaderComponent>
      <Paper className={classes.root}>
      
      <Grid container algin="center" alignItems="center" direction="row" spacing={16}>
        <Grid item xs={3}>
          <AddEmployee getEmployee={() => getEmployee()} ></AddEmployee>
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
                  
          {stateEmployee.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage).map((emp) => {
            
            return (
              
              <TableRow hover  tabIndex={-1} key={emp.id}>
                
                {columns.map((column) => {
                  const value = emp[column.id];
                 
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
                        onClick={() =>deleteEmployee(emp.id)}
                        >
                        <DeleteIcon fontSize="inherit" />
                      </IconButton>
                      <IconButton aria-label="edit"
                       className={classes.margin}
                        size="small"
                        onClick={() => editEmployee(emp.id)}>
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
      count={stateEmployee.length}
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