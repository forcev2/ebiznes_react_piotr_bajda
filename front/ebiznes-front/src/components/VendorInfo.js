import { getVendorInfo } from '../services/FetchApi';
import React, { useState } from 'react';

function VendorInfo() {
  let [responseData, setResponseData] = React.useState('');

  React.useEffect(() => {
    getVendorInfo()
      .then((json) => {
        setResponseData(json)
      })
      .catch((error) => {
        console.log(error)
      })
  }, [])

  return (
    <div className="VendorInfo">
      <pre>
        <code>
          <h3>VendorInfo</h3>
        </code>
        <ul>
          {responseData && responseData.map(obj => (
            <li>
              {obj.description}
            </li>))}
        </ul>
      </pre>
    </div>
  );
}

export default VendorInfo;
