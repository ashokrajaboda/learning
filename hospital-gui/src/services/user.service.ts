import authHeader from "./common.service";


const API_URL = process.env.REACT_APP_API_URL;

export const getPublicContent = async () => {
    const requestOptions = {
        method: 'GET',
        headers: { 
            'Content-Type': 'application/json',
            'Accept': 'application/json' 
        }
    };
    return await fetch(API_URL + "/dashboard/test2", requestOptions);
    //return axios.get(API_URL + "all");
};

export const getExceptionContent = async () => {
    const requestOptions = {
        method: 'GET',
        headers: { 
            'Content-Type': 'application/json',
            'Accept': 'application/json' 
        }
    };
    return await fetch(API_URL + "/dashboard/testException", requestOptions);
    //return axios.get(API_URL + "all");
};

export const getApiExceptionContent = async () => {
    const requestOptions = {
        method: 'GET',
        headers: { 
            'Content-Type': 'application/json',
            'Accept': 'application/json' 
        }
    };
    return await fetch(API_URL + "/dashboard/testApiException", requestOptions);
    //return axios.get(API_URL + "all");
};

export const getBindingContent = async () => {
    const requestOptions = {
        method: 'GET',
        headers: { 
            'Content-Type': 'application/json',
            'Accept': 'application/json' 
        }
    };
    return await fetch(API_URL + "/dashboard/testBinding", requestOptions);
    //return axios.get(API_URL + "all");
};

export const getUserBoard = async () => {
    const requestOptions = {
        method: 'GET',
        headers: { 'Content-Type': 'application/json', ...authHeader }
    };
    return await fetch(API_URL + "/user", requestOptions);
    //return axios.get(API_URL + "user", { headers: authHeader() });
};

export const getModeratorBoard = async () => {
    const requestOptions = {
        method: 'GET',
        headers: { 'Content-Type': 'application/json', ...authHeader }
    };
    return await fetch(API_URL + "/mod", requestOptions);
    //return axios.get(API_URL + "mod", { headers: authHeader() });
};

export const getAdminBoard = async () => {
    const requestOptions = {
        method: 'GET',
        headers: { 'Content-Type': 'application/json', ...authHeader }
    };
    return await fetch(API_URL + "/admin", requestOptions);
    //return axios.get(API_URL + "admin", { headers: authHeader() });
};