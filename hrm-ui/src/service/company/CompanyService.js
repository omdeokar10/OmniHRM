import api from "../BaseService";

const authUrl = '/api/company';

export const companyLogin = (companyDto) => {
    return api.post(authUrl + '/login', companyDto);
}