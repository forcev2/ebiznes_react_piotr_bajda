import { getVendorComment } from '../services/FetchApi';
import React, { useState } from 'react';

function VendorComment() {
  let [responseData, setResponseData] = React.useState('');

  React.useEffect(() => {
    getVendorComment()
      .then((json) => {
        setResponseData(json)
      })
      .catch((error) => {
        console.log(error)
      })
  }, [])

  return (
    <div className="VendorComment">
      <pre>
        <code>
          <h3>Vendor Comment</h3>
        </code>
        <ul>
          {responseData && responseData.map(obj => (
            <li>
              {obj.comment_body},{obj.vendor} , {obj.client}
            </li>))}
        </ul>
      </pre>
    </div>
  );
}

export default VendorComment;
