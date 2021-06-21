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
        const id = localStorage.getItem('userId');
        const isLoggedIn = localStorage.getItem('isLoggedIn');
        const checkSession = Cookies.get();
        //const authenticator = Cookies.get("authenticator");
        //console.log(checkSession)
        //console.log(authenticator)

        setState({ email: email, isLoggedIn: isLoggedIn, userId: id })
    }, [])

    return (
        <AuthContext.Provider value={[state, setState]}>{children}</AuthContext.Provider>
    );
}


export default Store;