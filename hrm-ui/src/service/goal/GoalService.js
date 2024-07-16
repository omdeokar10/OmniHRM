import axios from "axios";
import React from "react";
import { getLoggedInUser } from "../auth/AuthService";

const baseURL = "http://localhost:8081/api/goals";

export const createGoal = (formData) => {
    console.log('create goal.' + formData);
    return axios.post(baseURL, formData);
}

export const getAllGoals = () => {
    var formData = axios.get(baseURL);
    console.log(formData);
    return formData;
}


export const getAllGoalsByUser = () => {
    var username = getLoggedInUser();
    var formData = axios.get(baseURL + '/user/'+ username);
    console.log(formData);
    return formData;
}


export const deleteGoalById = (id) => {
    return axios.delete(baseURL + '/' + id);
}

export const getGoal = (id) => {
    return axios.get(baseURL + '/' + id);
}

export const updateGoal = (goalId, updatedGoal) => {
    console.log('update goal' + updatedGoal);
    return axios.put(`${baseURL}/${goalId}`, updatedGoal);
}