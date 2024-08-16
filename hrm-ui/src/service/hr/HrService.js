import axios from "axios";

const baseURL = "http://localhost:8081";
const companyUrl = "/api/company";

export const createCompanyApi = (company) => {
    return axios.post(baseURL + companyUrl, company);
}