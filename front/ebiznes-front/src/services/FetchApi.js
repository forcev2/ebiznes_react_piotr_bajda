import Cookies from 'js-cookie';

export function getClients() {
    const route = "clientJSON";

    return fetchData(route);
}

export function getVendor() {
    const route = "vendorJSON";

    return fetchData(route);
}

export function getProduct() {
    const route = "productJSON";

    return fetchData(route);
}

export function getSpecificProduct(id) {
    const route = "productJSON/" + id;

    return fetchData(route);
}

export function getVendorComment() {
    const route = "vendorCommentJSON";
    return fetchData(route);
}

export function getCategory() {
    const route = "categoryJSON";
    return fetchData(route);
}

export function getVendorInfo() {
    const route = "vendorInfoJSON";
    return fetchData(route);
}

export function getItemComment() {
    const route = "itemCommentJSON";
    return fetchData(route);
}

export function getItemCommentsByProductId(product) {
    const route = "itemCommentJSON/" + product;
    return fetchData(route);
}

export function getTransactionInfo() {
    const route = "transactionInfoJSON";
    return fetchData(route);
}

export function getBuyInfo() {
    const route = "buyInfoJSON";
    return fetchData(route);
}

export function getUser() {
    const route = "userJSON";
    return fetchData(route);
}


export function signUp(email, password) {
    const host = "https://ebiznesdckrpb.azurewebsites.net/"
    const route = "signUp";
    const requestOptions = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json', 'Accept': 'application/json' },
        body: JSON.stringify({ email: email, password: password })
    };
    return fetch(host + route, requestOptions)
}

export function signIn(email, password) {
    const host = "https://ebiznesdckrpb.azurewebsites.net/"
    const route = "signIn";
    const requestOptions = {
        method: 'POST',
        crossDomain: true,
        xhrFields: { withCredentials: true },
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email: email, password: password }),
        credentials: 'include',
    };
    return fetch(host + route, requestOptions)
}

//"csrfToken"
export function signOut() {
    const host = "https://ebiznesdckrpb.azurewebsites.net/"
    const route = "signOut";
    const token = localStorage.getItem("csrfToken");
    console.log("Using token csrfToken " + token)
    Cookies.set("csrfToken", token);
    const requestOptions = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        credentials: 'include',
    };
    return fetch(host + route, requestOptions)
}

//G_AUTHUSER_H
export function signInGoogle() {
    const host = "https://ebiznesdckrpb.azurewebsites.net/"
    const route = "authenticate/google";
    const requestOptions = {
        method: 'GET',
        headers: { 'Content-Type': 'application/json' },
        credentials: 'include',
        mode: 'cors',
    };
    return fetch(host + route, requestOptions)
}


export function addComment(commentBody, productId, userId) {
    const host = "https://ebiznesdckrpb.azurewebsites.net/"
    const route = "addJSON/" + commentBody + "/" + productId + "/" + userId;
    console.log(route);
    const requestOptions = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        mode: 'cors',
    };
    return fetch(host + route, requestOptions)
}

function fetchData(route) {
    const host = "https://ebiznesdckrpb.azurewebsites.net/"

    return fetch(host + route).then((response) => response.json())
}
