import React, { useContext } from 'react'
import { AuthContext } from '../AuthStore'
import { signOut as signOutFetch } from '../services/FetchApi';

export default function SignOut() {
    const [state, setState] = useContext(AuthContext)

    const signOut = async () => {
        signOutFetch().then((response) => {
            if (response.status != 200) {
                console.log("Already Logged In")
            }
            else {
                localStorage.removeItem('email');
                setState({ email: '', isLoggedIn: false });
            }
            console.log("from server logout ", response);
        })

    }

    return (
        <div className="menu-item push-down lastItem" onClick={signOut}>
            SignOut
        </div>
    )
}
