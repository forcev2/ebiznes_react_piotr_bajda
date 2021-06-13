import { getProduct, getSpecificProduct } from '../services/FetchApi';
import React, { useState, useContext } from 'react';
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Link,
  useLocation
} from "react-router-dom";
import ItemComments from './ItemComment';
import { AuthContext } from '../AuthStore'
import { addComment } from '../services/FetchApi';
import { set } from 'js-cookie';

function Product(props) {
  const location = useLocation()
  const par_id = props.match.params.id;
  let [responseData, setResponseData] = React.useState('');
  const [state, setState] = useContext(AuthContext);
  const [comment, setComment] = useState('');

  React.useEffect(() => {
    getSpecificProduct(par_id)
      .then((json) => {
        setResponseData(json)
      })
      .catch((error) => {
        console.log(error)
      })
  }, [setResponseData, responseData])

  const postComment = (id) => {
    addComment(comment, id);
    console.log(comment, id);
  }

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
            {state.isLoggedIn &&
              <div className="comment-write-wrapper">
                <textarea className="comment-inp"
                  value={comment}
                  onChange={e => { setComment(e.target.value) }}>

                </textarea>
                <div>
                  <button
                    type="button"
                    className="add-to-cart-button register-bttn"
                    onClick={() => postComment(obj.id)}
                  >
                    Post Comment
                  </button>
                </div>
              </div>
            }
            <ItemComments product_id={obj.id} />
          </div>

        </pre>
      ))}
    </div>
  );
}

export default Product;
