import axios from "axios";
import React from "react";
import { getLoggedInUser } from "../auth/AuthService";
import api from "../BaseService";

const baseURL = "/api/goals";

export const createGoal = (formData) => {
    return api.post(baseURL, formData);
}

export const getAllGoals = () => {
    return api.get(baseURL);
}


export const getAllGoalsByUser = () => {
    var username = getLoggedInUser();
    return api.get(baseURL + '/user/'+ username);
}


export const deleteGoalById = (id) => {
    return api.delete(baseURL + '/' + id);
}

export const getGoal = (id) => {
    return api.get(baseURL + '/' + id);
}

export const updateGoal = (goalId, updatedGoal) => {
    return api.put(`${baseURL}/${goalId}`, updatedGoal);
}