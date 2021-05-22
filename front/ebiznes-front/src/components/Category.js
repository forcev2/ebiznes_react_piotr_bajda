import { getCategory } from '../services/FetchApi';
import React, { useState } from 'react';

function Category() {
  let [responseData, setResponseData] = React.useState('');

  React.useEffect(() => {
    getCategory()
      .then((json) => {
        setResponseData(json)
      })
      .catch((error) => {
        console.log(error)
      })
  }, [setResponseData, responseData])

  return (
    <div className="Category">
      <pre>
        <code>
          <h3>Category</h3>
        </code>
        <ul>
          {responseData && responseData.map(obj => (
            <li>
              {obj.name}
            </li>))}
        </ul>
      </pre>
    </div>
  );
}

export default Category;
