import React, { useContext } from 'react'
import { AuthContext } from '../AuthStore'
import { signOut as signOutFetch } from '../services/FetchApi';
import Cookies from 'js-cookie';

export default function SignOut() {
    const [state, setState] = useContext(AuthContext)

    const signOut = async () => {
        signOutFetch().then((response) => {
            if (response.status != 200) {
                console.log("Already Logged In")

                const authenticator = Cookies.get("authenticator");
                if (authenticator) {
                    setState({ email: '', isLoggedIn: true });
                }
            }
            else {
                localStorage.removeItem('email');
                setState({ email: '', isLoggedIn: false });
            }
            console.log("from server logout ", response);
        })

    }

    return (
        <div className="menu-item lastItem" onClick={signOut}>
            SignOut
        </div>
    )
}
