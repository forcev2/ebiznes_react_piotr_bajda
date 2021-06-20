import { getVendor } from '../services/FetchApi';
import React, { useState } from 'react';

function Vendors() {
  let [responseData, setResponseData] = React.useState('');

  React.useEffect(() => {
    getVendor()
      .then((json) => {
        setResponseData(json)
      })
      .catch((error) => {
        console.log(error)
      })
  }, [])

  return (
    <div className="Vendors">
      <pre>
        <code>
          <h3>Vendors</h3>
        </code>
        <ul>
          {responseData && responseData.map(obj => (
            <li>
              {obj.companyName}
            </li>))}
        </ul>
      </pre>
    </div>
  );
}

export default Vendors;
