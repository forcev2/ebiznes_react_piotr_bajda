import { getItemCommentsByProductId } from '../services/FetchApi';
import React, { useState } from 'react';
import { Link } from 'react-router-dom';

function ItemComments(props) {
  let [responseData, setResponseData] = React.useState('');
  const product_id = props.product_id;


  React.useEffect(() => {
    getItemCommentsByProductId(product_id)
      .then((json) => {
        setResponseData(json)
      })
      .catch((error) => {
        console.log(error)
      })
  }, [setResponseData, responseData])

  return (
    <div className="ItemComments">
      <pre>

        {responseData && responseData.map(obj => (
          <div className="product-card product-comment">
            {obj.comment_body}
          </div>
        ))}
      </pre>
    </div>
  );
}

export default ItemComments;
