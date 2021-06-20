import { getItemComment } from '../services/FetchApi';
import React from 'react';

function AllItemComments() {
  let [responseData, setResponseData] = React.useState('');

  React.useEffect(() => {
    getItemComment()
      .then((json) => {
        setResponseData(json)
        console.log(json)
      })
      .catch((error) => {
        console.log(error)
      })
  }, [])

  return (
    <div className="ItemComment">
      <pre>
        <code>
          <h3>Item Comment</h3>
        </code>
        <ul>
          {responseData && responseData.map(obj => (
            <li>
              {obj.commentBody},{obj.product} , {obj.client}
            </li>))}
        </ul>
      </pre>
    </div>
  );
}

export default AllItemComments;
