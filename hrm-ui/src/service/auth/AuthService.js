import axios from "axios";
import React from "react";

const baseURL = "http://localhost:8081/api/auth";
//localhost:8081/api/auth/login
export const registerAPICall = (registerObj) => axios.post(baseURL + '/register', registerObj);

export const loginAPICall = (loginObj) => {
    return axios.post(baseURL + '/login', loginObj);
}

export const storeToken = (token) => localStorage.setItem("token", token);

export const getToken = () => localStorage.getItem("token");

export const saveLoggedInUser = (username, roles) => {
    sessionStorage.setItem("authenticatedUser", username);
    sessionStorage.setItem("roles", roles);
}

export const isUserLoggedIn = () => {
    const username = getLoggedInUser();
    if (username == null) {
        return false;
    }
    else {
        return true;
    }
}

export const getLoggedInUser = () => {
    return sessionStorage.getItem("authenticatedUser");
}

export const logout = () => {
    localStorage.clear();
    sessionStorage.clear();
}

export const isAdminUser = () => {

    let role = sessionStorage.getItem("role");

    if (role != null && role === 'ROLE_ADMIN') {
        return true;
    } else {
        return false;
    }

}