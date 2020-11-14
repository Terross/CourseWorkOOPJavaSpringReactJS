import './App.css';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom'

import MainPageComponent from './components/MainPageComponent';
import EmployeesListComponent from './components/EmployeeComponents/EmployeesListComponent';
import AddEmployee from './components/EmployeeComponents/AddEmployee';
import EditEmployee from './components/EmployeeComponents/EditEmployee';

import AddCustomer from './components/CustomerComponents/AddCustomer';
import EditCustomer from './components/CustomerComponents/EditCustomer';
import CustomerListComponent from './components/CustomerComponents/CustomerListComponent';

import ProductsListComponent from './components/ProductComponents/ProductsListComponent';
import ProductsList from './components/Catalog/ProductsList';

function App() {
  return (
    <div>
      <Router>
        
           
          <div className="container">
            <Switch>
              <Route path="/" exact component = {MainPageComponent}/>
              <Route path="/main-page" component = {MainPageComponent}/>
              <Route path="/employees" component = {EmployeesListComponent}/>
              <Route path="/edit-employee/:id" component = {EditEmployee}/>
              <Route path="/customers" component = {CustomerListComponent}/>
              <Route path="/edit-customer/:id" component = {EditCustomer}/>
              <Route path="/catalog" component={ProductsList}/>
              <Route path="/products" component={ProductsListComponent}/>
            </Switch>
          </div>

      </Router>
    </div>
  );
}

export default App;
