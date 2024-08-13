import axios from "axios";
import { getLoggedInUser } from "../auth/AuthService";
import api from "../BaseService";

const empDetailUrl = "/api/employeesdetails";
const employeeUrl = "/api/employees";

export const fetchDetailsCurrentEmployee = () => {
    var user = getLoggedInUser();
    return api.get(empDetailUrl + '/');
}

export const fetchDetailsForHierarchy = () => {
    var user = getLoggedInUser();
    return api.get(employeeUrl + '/')
        .then(response => {
            return api.get(empDetailUrl + '/hierarchy');
        })
        .then((hierarchyResponse) => {
            return hierarchyResponse;
        })
        .catch(error => {
            console.error('Error fetching user email:', error);
        });

}

export const addEmployee = (employeeDetails) => {
    return api.post(empDetailUrl + '/', employeeDetails)
}

export const getAllEmployeesByCompany = (company) => {
    return api.get(empDetailUrl + '/all/' + company);
}

export const getEmployeeById = (id) => {
    return api.get(empDetailUrl + '/' + id);
}

export const updateEmployee = (id, employee) => {
    return api.put(empDetailUrl + '/' + id, employee);
}

export const deleteEmployeeById = (id) => {
    return api.delete(empDetailUrl + '/' + id);
}