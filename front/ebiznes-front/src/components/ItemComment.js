import { getItemCommentsByProductId } from '../services/FetchApi';
import React from 'react';

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
  }, [])

  return (
    <div className="ItemComments">
      <pre>

        {responseData && responseData.map(obj => (
          <div className="product-card product-comment">
            {obj.commentBody}
          </div>
        ))}
      </pre>
    </div>
  );
}

export default ItemComments;
