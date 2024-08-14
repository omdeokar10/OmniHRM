import api from "../BaseService";

const authUrl = '/api/auth';

export const getAccessToken = () => localStorage.getItem("accesstoken");

export const storeToken = (token, refreshToken) => {
    localStorage.setItem("accesstoken", token);
    localStorage.getItem("accesstoken");
    if (refreshToken) {
        localStorage.setItem("refreshToken", refreshToken);
        localStorage.getItem("refreshToken");
    }
}

export const storeInfo = (key, value) => {
    localStorage.setItem(key, value);
    sessionStorage.setItem(key, value);
}

export const saveLoggedInUser = (username, roles) => {
    sessionStorage.setItem("authenticatedUser", username);
    sessionStorage.setItem("roles", roles);

    localStorage.setItem("authenticatedUser", username);
    localStorage.setItem("roles", roles);
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
    return localStorage.getItem("authenticatedUser");
}

export const getCompanyName = () => {
    return localStorage.getItem("companyName");
}

export const isAdminUser = () => {
    let role = sessionStorage.getItem("role");
    if (role != null && role === 'ROLE_ADMIN') {
        return true;
    } else {
        return false;
    }
}

export const registerAPICall = (registerObj) => api.post(authUrl + '/register', registerObj);

export const loginAPICall = (loginObj) => {
    return api.post(authUrl + '/login', loginObj);
}

export const forgotPasswordApiCall = (email) => api.post(authUrl + '/forgot-password/' + email);

export const refreshApiCall = () => {
    var refreshToken = localStorage.getItem("refreshToken");
    var username = getLoggedInUser();
    var refreshRequestObj = { refreshToken, username };
    return api.post(authUrl + '/refresh/token', refreshRequestObj);
}

export const logout = () => {
    var refreshToken = localStorage.getItem("refreshToken");
    var username = getLoggedInUser();
    const logoutObj = { refreshToken, username };
    api.post(authUrl + '/logout', logoutObj);
    localStorage.clear();
    sessionStorage.clear();
}

