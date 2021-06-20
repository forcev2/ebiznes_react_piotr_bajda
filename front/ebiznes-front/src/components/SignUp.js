import React from 'react'
import { signUp as signUpFetch } from '../services/FetchApi';
import { Redirect } from 'react-router-dom';

export default function SignUp() {
  let [emailExists, setEmailExists] = React.useState(false);
  let [someEmpty, setSomeEmpty] = React.useState(false);
  let [passwordsDiferent, setPasswordsDiferent] = React.useState(false);

  let [email, setEmail] = React.useState('');
  let [password, setPassword] = React.useState('');
  let [confirmPassword, setConfirmPassword] = React.useState('');

  let [redirect, setRedirect] = React.useState(false);

  const signUp = async () => {
    console.log(email, password, confirmPassword)

    if (!email || !password || !confirmPassword) {
      setSomeEmpty(true);
      console.log("EMPTY FIELDS")
    }
    else {
      setSomeEmpty(false);

      if (password != confirmPassword) {
        setPasswordsDiferent(true);
        console.log("DIFERENT")
      }
      else {
        setPasswordsDiferent(false);

        if (!someEmpty && !passwordsDiferent) {
          signUpFetch(email, password).then((response) => {
            console.log("from server ", response);
            if (response.status == 403) {
              console.log("already exists")
              setEmailExists(true)
            }
            else {
              setRedirect(true);
            }
            console.log("from server ", response);
          })
        }
      }
    }

    //await signUp(email, password);
  }

  const changeEmail = event => setEmail(event.target.value);
  const changePassword = event => setPassword(event.target.value);
  const changeConfirmPassword = event => setConfirmPassword(event.target.value);

  return (
    <div>
      <h2>Register</h2>
      <form>
        <div className="form-group text-left">
          <label htmlFor="exampleInputEmail1">Email address</label>
          <input type="email"
            className="form-control"
            id="email"
            aria-describedby="emailHelp"
            placeholder="Enter email"
            value={email}
            onChange={changeEmail}
          />
        </div>
        <div className="form-group text-left">
          <label htmlFor="exampleInputPassword1">Password</label>
          <input type="password"
            className="form-control"
            id="password"
            placeholder="Password"
            value={password}
            onChange={changePassword}
          />
        </div>
        <div className="form-group text-left">
          <label htmlFor="exampleInputPassword1">Confirm Password</label>
          <input type="password"
            className="form-control"
            id="confirmPassword"
            placeholder="Confirm Password"
            value={confirmPassword}
            onChange={changeConfirmPassword}
          />
        </div>
        <button
          type="button"
          className="add-to-cart-button register-bttn"
          onClick={signUp}
        >
          Register
        </button>
      </form>
      {emailExists ? (<strong className="errr">Email already exists</strong>) : null}
      {someEmpty ? (<strong className="errr">Fill in all empty fields</strong>) : null}
      {redirect ? (<Redirect push to="/login" />) : null}
    </div>
  )
}
