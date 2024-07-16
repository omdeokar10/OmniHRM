import axios from "axios";
import { getLoggedInUser } from "../auth/AuthService";

const baseURL = "http://localhost:8081/api/form";

export const submitForm = (formData) => {
    return axios.post(baseURL, formData);
}

export const getFormData = (id) => {
    var user = getLoggedInUser();
    var data = axios.get(baseURL + '/' + id, user);
    return data;
}

export const submitFormData = (data) => {
    var user = getLoggedInUser();
    axios.post(baseURL + '/' + user, data);
}

export const getPendingFormData = () => {
    var user = getLoggedInUser();
    console.log(user);
    var data = axios.get(baseURL + '/user/'+user);
    return data;
}