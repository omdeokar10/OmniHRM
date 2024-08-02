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
