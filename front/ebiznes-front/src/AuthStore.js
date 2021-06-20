import React, { useState, useEffect } from 'react';
import Cookies from 'js-cookie';


const initialState = {
    email: '',
    isLoggedIn: false
};


export const AuthContext = React.createContext();



//G_AUTHUSER_H PLAY_SESSION
const Store = ({ children }) => {
    const [state, setState] = useState(initialState);

    useEffect(() => {
        const email = localStorage.getItem('email');
        const checkSession = Cookies.get();
        const authenticator = Cookies.get("authenticator");
        console.log(checkSession)
        console.log(authenticator)
        if (authenticator) {
            setState({ email: email, isLoggedIn: true })
        }
        else {
            setState({ email: email, isLoggedIn: false })
        }

        console.log(email);
    }, [])

    return (
        <AuthContext.Provider value={[state, setState]}>{children}</AuthContext.Provider>
    );
}


export default Store;