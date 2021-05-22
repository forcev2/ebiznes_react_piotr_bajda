import { getUser } from '../services/FetchApi';
import React, { useState } from 'react';

function Users() {
  let [responseData, setResponseData] = React.useState('');

  React.useEffect(() => {
    getUser()
      .then((json) => {
        setResponseData(json)
      })
      .catch((error) => {
        console.log(error)
      })
  }, [setResponseData, responseData])

  return (
    <div className="Vendors">
      <pre>
        <code>
          <h3>Vendors</h3>
        </code>
        <ul>
          {responseData && responseData.map(obj => (
            <li>
              {obj.username} , {obj.email}
            </li>))}
        </ul>
      </pre>
    </div>
  );
}

export default Users;
