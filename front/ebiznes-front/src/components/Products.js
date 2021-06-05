import { getProduct } from '../services/FetchApi';
import React, { useState } from 'react';
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Link
} from "react-router-dom";

function Products(props) {
  let [responseData, setResponseData] = React.useState('');
  let { addToCart } = props;

  React.useEffect(() => {
    getProduct()
      .then((json) => {
        setResponseData(json)
      })
      .catch((error) => {
        console.log(error)
      })
  }, [setResponseData, responseData])

  return (
    <div className="Product">
      <pre>
        <code>
          <h2>Products</h2>
        </code>
        <div>
          {responseData && responseData.map(obj => (
            <div className="product-card">
              {obj.name}
              <div className="buttons-product">
                <div className="product-info-button">
                  <Link to={'/product/' + obj.id} >Product Info</Link>
                </div>
                <button onClick={() => addToCart(obj)} className="add-to-cart-button">Add To Cart</button>
              </div>

            </div>
          ))}
        </div>
      </pre>
    </div >
  );
}

export default Products;
