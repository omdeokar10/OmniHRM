import axios from "axios";
import React from "react";
import api from "../BaseService";

const performanceURL = "/api/goals";

export const addPerformanceGoal = (goal) => api.post(performanceURL, goal);