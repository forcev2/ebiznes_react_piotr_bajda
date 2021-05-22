import { getProduct } from '../services/FetchApi';
import React, { useState } from 'react';
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Link
} from "react-router-dom";

function Products() {
  let [responseData, setResponseData] = React.useState('');

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
              <div className="card-button">
                <Link to={'/product/' + obj.id} >Product Info</Link>
              </div>
            </div>
          ))}
        </div>
      </pre>
    </div >
  );
}

export default Products;
