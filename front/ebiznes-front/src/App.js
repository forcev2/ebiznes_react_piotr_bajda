import React, { useState, useContext, useEffect } from 'react';
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
import SignUp from './components/SignUp';
import SignIn from './components/SignIn';
import SignOut from './components/SignOut';
import { AuthContext } from './AuthStore'
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Link
} from "react-router-dom";
import ShoppingCartComponent from './components/ShoppingCartComponent';


function App() {
  const [state, setState] = useContext(AuthContext);

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

  const readUrlParamsFromAuthentication = () => {
    var url_string = window.location.href; //window.location.href
    var url = new URL(url_string);
    var userStr = url.searchParams.get("user-id");

    var userId = null;
    var userEmail = null;
    if (userStr && userStr.startsWith("User(")) {
      var ind1 = userStr.lastIndexOf("User(") + 5;
      var ind2 = userStr.indexOf(",");
      userId = userStr.substring(ind1, ind2);

      ind1 = userStr.lastIndexOf("),") + 2;
      ind2 = userStr.lastIndexOf(")");

      userEmail = userStr.substring(ind1, ind2);

      localStorage.setItem("email", userEmail);
      localStorage.setItem("isLoggedIn", true);
      localStorage.setItem("userId", userId);
      setState({ email: userEmail, isLoggedIn: true, userId: userId })
    }

    console.log(" user id =", userId, "email =", userEmail);

  }

  useEffect(() => {
    readUrlParamsFromAuthentication();
  }, [])


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
          <div className="padding-menu">

          </div>
          {!state.isLoggedIn &&
            <div className="menu-item">
              <Link to="/register">Sign Up</Link>
            </div>
          }
          {!state.isLoggedIn &&
            <div className="menu-item lastItem">
              <Link to="/login">Sign In</Link>
            </div>
          }
          {state.isLoggedIn &&
            <SignOut />
          }
          {state.isLoggedIn &&
            <div className="menu-item email-size">
              {state.email}
            </div>
          }

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
            <Route path="/register">
              <SignUp />
            </Route>
            <Route path="/login">
              <SignIn />
            </Route>

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
