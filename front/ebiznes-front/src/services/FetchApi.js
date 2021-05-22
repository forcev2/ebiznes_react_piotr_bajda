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
    console.log(route);
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

function fetchData(route) {
    const host = "http://localhost:12345/"

    return fetch(host + route).then((response) => response.json())
}
