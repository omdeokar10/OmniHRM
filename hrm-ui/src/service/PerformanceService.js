import axios from "axios";
import React from "react";

const baseURL = "http://localhost:8081/api/goals";

export const addPerformanceGoal = (goal) => axios.post(baseURL, goal);