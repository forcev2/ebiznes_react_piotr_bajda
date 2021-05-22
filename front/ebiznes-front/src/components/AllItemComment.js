import { getItemComment } from '../services/FetchApi';
import React, { useState } from 'react';

function AllItemComments() {
  let [responseData, setResponseData] = React.useState('');

  React.useEffect(() => {
    getItemComment()
      .then((json) => {
        setResponseData(json)
      })
      .catch((error) => {
        console.log(error)
      })
  }, [setResponseData, responseData])

  return (
    <div className="ItemComment">
      <pre>
        <code>
          <h3>Item Comment</h3>
        </code>
        <ul>
          {responseData && responseData.map(obj => (
            <li>
              {obj.comment_body},{obj.product} , {obj.client}
            </li>))}
        </ul>
      </pre>
    </div>
  );
}

export default AllItemComments;
