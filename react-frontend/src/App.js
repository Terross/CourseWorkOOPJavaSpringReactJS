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
//import customerHTML from './PdfReports/customers.html'
import OrdersListComponent from './components/OrderComponents/OrdersListComponent';
import Pi from './components/Pi';
import EditProduct from './components/ProductComponents/EditProduct';

function App() {
  return (
    <div>
      <Router>
        
           
          <div className="container">
            <Switch>
              <Route path="/" exact component = {ProductsList}/>
              <Route path="/main-page" component = {MainPageComponent}/>
              <Route path="/employees" component = {EmployeesListComponent}/>
              <Route path="/edit-employee/:id" component = {EditEmployee}/>
              <Route path="/customers" component = {CustomerListComponent}/>
              <Route path="/edit-customer/:id" component = {EditCustomer}/>
              <Route path="/catalog" component={ProductsList}/>
              <Route path="/products" component={ProductsListComponent}/>
			  <Route path="/orders" component={OrdersListComponent}/>
			  <Route path="/pi" component={Pi}/>
				<Route path="/edit-product/:id" component={EditProduct}/>
            </Switch>
          </div>

      </Router>
    </div>
  );
}

export default App;
