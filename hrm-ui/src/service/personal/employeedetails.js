import axios from "axios";
import { getLoggedInUser } from "../auth/AuthService";

const baseURL = "http://localhost:8081/api/employeesdetails";

const employeeUrl = "http://localhost:8081/api/employees";

export const fetch_details_for_current_employee = () => {
    var user = getLoggedInUser();
    var company = 'dundermifflin';
    return axios.get(baseURL + '/' + user + '@' + company + '.com');
}

export const fetchDetailsForHierarchy = () => {
    var user = getLoggedInUser();
    return axios.get(employeeUrl + '/username/' + user)
        .then(response => {
            const email = response.data.email; 
            return axios.get(baseURL + '/hierarchy/' + email)
        })
        .then((hierarchyResponse)=>{
            return hierarchyResponse;
        })
        .catch(error => {
            console.error('Error fetching user email:', error);
        });

}

export const fetch_employee_details = () => {

    var user = getLoggedInUser();
    return axios.get(baseURL + '/' + user + '@' + company + '.com');

}
