import React, { useContext } from 'react'
import { AuthContext } from '../AuthStore'
import { signOut as signOutFetch } from '../services/FetchApi';

export default function SignOut() {
    const [state, setState] = useContext(AuthContext)

    const signOut = async () => {
        signOutFetch().then((response) => {
            localStorage.removeItem('email');
            localStorage.removeItem('userId');
            localStorage.removeItem("isLoggedIn");
            setState({ email: "", isLoggedIn: false, userId: null })

            console.log("from server logout ", response);
        })

    }

    return (
        <div className="menu-item lastItem" onClick={signOut}>
            SignOut
        </div>
    )
}
