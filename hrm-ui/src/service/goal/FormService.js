import axios from "axios";
import { getLoggedInUser } from "../auth/AuthService";
import { getCompanyName } from "../auth/AuthService";

import api from "../BaseService";

const baseURL = "/api/form";

export const submitForm = (formData, formName) => {
    var companyName = getCompanyName();
    const requestData = {
        companyName: companyName,
        formName: formName,
        formData: formData
    };
    return api.post(baseURL + '/', requestData);
}

export const getFormData = (id) => {
    return api.get(baseURL + '/id/' + id);
}

export const updateFormData = (id, data, formName) => {
    var companyName = getCompanyName();
    const requestData = {
        companyName: companyName,
        formName: formName,
        formData: data
    };
    return api.put(baseURL + '/' + id, requestData);
}

export const deleteFormById = (id) => {
    return api.delete(baseURL + '/' + id);
}

export const getFormDataByCompanyName = () => {
    var companyName = getCompanyName();
    console.log('companyName is'+companyName);
    return api.get(baseURL + '/company/' + companyName);
}

export const submitFormData = (data) => {
    var user = getLoggedInUser();
    api.post(baseURL + '/' + user, data);
}

export const getPendingFormData = () => {
    return api.get(baseURL + '/user');
}