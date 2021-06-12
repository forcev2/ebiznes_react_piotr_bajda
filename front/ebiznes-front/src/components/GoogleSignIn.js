import React from 'react'
import { GoogleLogin } from 'react-google-login';
import { signIn as signInFetch, signInGoogle } from '../services/FetchApi';

export default function GoogleSignIn() {

    const responseGoogle = () => {
        window.location.href = "http://localhost:12345/authenticate/google";
    }

    return (
        <div>
            <button onClick={responseGoogle}
                className="add-to-cart-button register-bttn ingsoc ggl-bttn"> Login With Google </button>
        </div>
    )
}
