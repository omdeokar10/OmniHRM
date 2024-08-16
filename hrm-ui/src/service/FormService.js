import axios from "axios";
import React from "react";

const baseURL = "http://localhost:8081/api/form";

export const submitForm = (formData) => {
    return axios.post(baseURL, formData);
}

export const getFormData = () => {
    return axios.get(baseURL);
}