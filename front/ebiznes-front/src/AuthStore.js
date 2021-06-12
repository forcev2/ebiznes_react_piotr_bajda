import React, { useState, useEffect } from 'react';
import { signIn } from './services/FetchApi';
import Cookies from 'js-cookie';


const initialState = {
    email: '',
    isLoggedIn: false
};


export const AuthContext = React.createContext();

/*
G_AUTHUSER_H: "0"
authenticator: "1-3cb7cbdbc6a31b2abe715101ae63ab2015e0cb4b-1-Vs8Ss0nH8V+wMatSaDoZE3aGhar1n5OhWE718Ptu9B+7RZ9l5501SY0HKmP6PWlnSYKhM2rmGl7DaxxVnRJ3Wf/s5LRlxjYQO7qJqnm64UgBe1m7IksMlIV1nDxUVqJWQlxNX3YcWLpAr/aepoTgQfNNYCvwQKMVt7kvBtv5ySnkittWXP4/sC63c7PoDY6xxcMRmngStFV5ohcVaKGjg3qDmyoqHnDxioCsyjKgydBM7/9ODetZaSdk+2sG+yB86B0aSXbNi8q84RZ9MaVh0GY8JeJe0+M82hGaLpQcHwYyEaqq5wh/Ju80rF747HxSUdR79r9bWx2/FBM32JyZfgy5Y7Y2lBC/8lXuu/yRGsTFhsXBigq+PiZ8kpQTBlIr7/g//7uDgPfBmHzWI5atVdyIV02NGizIWdvUjbqupNlkzEt8vanQ8ezKw+9SE0hYDOMUG5sqU2lbEGtdtvsTcyhrGnMGdn4rqivFvJJW3o1TE9lwlDfJfwM0RU6HcL6uZSwSSX3SdpRtVBTXtstYEhFMQORiq3wZVTbXfEEdphL7Y3BApX8cdfpAUK4pCoQz+YVCmAJoZBPTpb4EMZoOGfAbI1DV3k0JUwQeCh7AWBArIzG8v2mhwAdoIgL/GwNymNh0gfJOdiYbLxxroO62yZY9Pu/Au/oVMljKOeVsvdAxpw=="
csrfToken: "623d15a175f9167e9881b55d86f0c84003ca519f-1623531242616-78478ebc26619ff4f7b5a563"
csrftoken: "zrCD6soa7usiVjgFWFy4KDvxeSAnbUtg3Qqoxo5yzLvLFbIfTjmUCkjd3jGV0TRy"
*/


/*
G_AUTHUSER_H: "0"
csrftoken: "zrCD6soa7usiVjgFWFy4KDvxeSAnbUtg3Qqoxo5yzLvLFbIfTjmUCkjd3jGV0TRy"
*/



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
    }, []) // <-- empty dependency array

    return (
        <AuthContext.Provider value={[state, setState]}>{children}</AuthContext.Provider>
    );
}


export default Store;