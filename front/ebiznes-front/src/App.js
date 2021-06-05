import logo from './logo.svg';
import getClients from './services/FetchApi';
import React, { useState } from 'react';
import './App.css';
import Products from './components/Products';
import Clients from './components/Clients';
import Vendors from './components/Vendors';
import Users from './components/Users';
import Category from './components/Category';
import VendorInfo from './components/VendorInfo';
import AllItemComment from './components/AllItemComment';
import VendorComment from './components/VendorComment';
import Product from './components/Product';
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Link
} from "react-router-dom";
import ShoppingCartComponent from './components/ShoppingCartComponent';

function App() {
  let [responseData, setResponseData] = React.useState('');

  const [cart, setCart] = useState([]);

  const addToCart = (product) => {
    console.log(cart);
    setCart([...cart, product]);
  };

  const removeFromCart = (product) => {
    let hardCopy = [...cart];
    hardCopy = hardCopy.filter((cartItem) => cartItem.id !== product.id);
    setCart(hardCopy);
  };

  let state = 2;


  return (
    <Router>
      <div className="App">
        <div className="left-panel">
          <div className="menu-top">
            MENU
          </div>
          <div className="menu-item">
            <Link to="/products">Products</Link>
          </div>
          <div className="menu-item">
            <Link to="/clients">Clients</Link>
          </div>
          <div className="menu-item">
            <Link to="/vendors">Vendors</Link>
          </div>
          <div className="menu-item">
            <Link to="/users">Users</Link>
          </div>
          <div className="menu-item">
            <Link to="/category">Category</Link>
          </div>
          <div className="menu-item">
            <Link to="/vendor_info">Vendor Info</Link>
          </div>
          <div className="menu-item">
            <Link to="/item_comment">Item Comments</Link>
          </div>
          <div className="menu-item">
            <Link to="/vendor_comment">Vendor Comments</Link>
          </div>
        </div>
        <header className="App-header">

          <Switch>
            <Route exact path="/products">
              <Products addToCart={addToCart} />
            </Route>
            <Route path="/clients">
              <Clients />
            </Route>
            <Route path="/vendors">
              <Vendors />
            </Route>
            <Route path="/users">
              <Users />
            </Route>
            <Route path="/category">
              <Category />
            </Route>
            <Route path="/vendor_info">
              <VendorInfo />
            </Route>
            <Route path="/item_comment">
              <AllItemComment />
            </Route>
            <Route path="/vendor_comment">
              <VendorComment />
            </Route>
            <Route path="/product/:id" component={Product} />

          </Switch>

        </header>
        <div className="Basket">
          <ShoppingCartComponent cart={cart} addToCart={addToCart}></ShoppingCartComponent>
        </div>
      </div>
    </Router >
  );
}

export default App;
