import { getClients } from '../services/FetchApi';
import React, { useState } from 'react';

function Clients() {
  let [responseData, setResponseData] = React.useState('');

  React.useEffect(() => {
    getClients()
      .then((json) => {
        setResponseData(json)
      })
      .catch((error) => {
        console.log(error)
      })
  }, [])

  return (
    <div className="Clients">
      <pre>
        <code>
          <h3>Clients</h3>
        </code>
        <ul>
          {responseData && responseData.map(obj => (
            <li>
              {obj.name}, {obj.surname} , {obj.user}
            </li>))}
        </ul>
      </pre>
    </div>
  );
}

export default Clients;
