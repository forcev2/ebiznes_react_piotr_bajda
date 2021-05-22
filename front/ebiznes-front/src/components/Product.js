import { getProduct, getSpecificProduct } from '../services/FetchApi';
import React, { useState } from 'react';
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Link,
  useLocation
} from "react-router-dom";
import ItemComments from './ItemComment';

function Product(props) {
  const location = useLocation()
  const par_id = props.match.params.id;
  let [responseData, setResponseData] = React.useState('');


  React.useEffect(() => {
    getSpecificProduct(par_id)
      .then((json) => {
        setResponseData(json)
      })
      .catch((error) => {
        console.log(error)
      })
  }, [setResponseData, responseData])

  return (
    <div className="Product">
      {responseData && responseData.map(obj => (
        <pre>
          <code>
            <h2> {obj.name} </h2>
          </code>
          <div>
            <div className="product-card">
              <div className="product-desc">{obj.description}</div>
              <div>category: {obj.category}</div>
            </div>
            <h3>Comments:</h3>
            <ItemComments product_id={obj.id} />
          </div>

        </pre>
      ))}
    </div>
  );
}

export default Product;
