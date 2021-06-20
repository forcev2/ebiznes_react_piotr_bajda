import React, { useContext } from 'react'
import { signIn as signInFetch } from '../services/FetchApi';
import { Redirect } from 'react-router-dom';
import { AuthContext } from '../AuthStore'
import { GoogleLogin } from 'react-google-login';
import GoogleSignIn from './GoogleSignIn';
import FacebookSignIn from './FacebookSignIn';
import Cookies from 'js-cookie';

export default function SignIn() {
    let [someEmpty, setSomeEmpty] = React.useState(false);
    let [wrongCreditials, setWrongCreditials] = React.useState(false);

    let [email, setEmail] = React.useState('');
    let [password, setPassword] = React.useState('');

    let [redirect, setRedirect] = React.useState(false);

    const [state, setState] = useContext(AuthContext)

    const changeEmail = event => setEmail(event.target.value);
    const changePassword = event => setPassword(event.target.value);

    console.log(email, password)

    const signIn = async () => {
        console.log(email, password)

        if (!email || !password) {
            setSomeEmpty(true);
            console.log("EMPTY FIELDS")
        }
        else {
            setSomeEmpty(false);

            if (!someEmpty) {
                console.log("GOT HERE ", someEmpty)
                signInFetch(email, password)
                    .then((response) => {
                        if (response.status == 403) {
                            console.log("wrong creditials")
                            console.log(response);
                            setWrongCreditials(true);
                        }
                        else {
                            return response.json()
                        }
                        //console.log("from server login ", response);
                    })
                    .then((data) => {
                        console.log("data ", data);
                        localStorage.setItem(data[0], data[1]);
                        console.log(Cookies.get())
                        setState({ email: email, isLoggedIn: true });
                        setRedirect(true);
                    });
            }

        }
        //await signUp(email, password);
    }




    return (
        <div>
            <h2>Login</h2>
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
                <button
                    type="button"
                    className="add-to-cart-button register-bttn"
                    onClick={signIn}
                >
                    Login
                </button>

            </form>
            <GoogleSignIn />
            <FacebookSignIn />

            {redirect ? (<Redirect push to="/" />) : null}
        </div>
    )
}
