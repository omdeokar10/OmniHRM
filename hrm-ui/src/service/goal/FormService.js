import axios from "axios";
import { getLoggedInUser } from "../auth/AuthService";

import api from "../BaseService";

const baseURL = "/api/form";

export const submitForm = (formData) => {
    return api.post(baseURL, formData);
}

export const getFormData = (id) => {
    var user = getLoggedInUser();
    return api.get(baseURL + '/' + id, user);
}

export const submitFormData = (data) => {
    var user = getLoggedInUser();
    api.post(baseURL + '/' + user, data);
}

export const getPendingFormData = () => {
    var user = getLoggedInUser();
    return api.get(baseURL + '/user/'+user);
}