import axios from "axios";
import { getLoggedInUser } from "../auth/AuthService";

const baseURL = "http://localhost:8081/api/attendance";

const timeSheetURL = "http://localhost:8081/api/timesheet";

export const logPunchInTime = () => {

    var username = getLoggedInUser();
    var date = getCurrentDate();

    const attendanceDto = { username, date };

    axios.post(baseURL + "/login", attendanceDto);

}

function getCurrentDate() {
    var d = new Date();
    d = new Date(d.getTime() - d.getTimezoneOffset() * 60000);
    var date = d.toISOString().slice(0, 10);
    return date;
}

export const startDayOfWorkingWeek = () => {
    var curr = new Date();
    return curr.getDate() - curr.getDay() + 1;
}

export const endDayOfWorkingWeek = () => {
    var curr = new Date();
    return curr.getDate() - curr.getDay() + 5;
}

export const getCurrentMonth = () => {
    var date = new Date();
    return date.getMonth();
}

export const getYear = () => {
    var date = new Date();
    return date.getYear() + 1900;
}


export const logPunchOutTime = () => {
    var username = getLoggedInUser();
    var date = getCurrentDate();
    const attendanceDto = { username, date };
    axios.post(baseURL + "/logout", attendanceDto);
}

export const fetchTasksForDate = (date) => {
    var createdDate = `${date.year}-${date.month}-${date.date}`;
    var username = getLoggedInUser();
    var object = { username, createdDate };
    return axios.get(timeSheetURL + "/user/date/" + username + "/" + createdDate);

    // const formattedDate = `${date.year}-${date.month}-${date.date}`;
    // axios.get(`/api/tasks?date=${formattedDate}`)
    //   .then(response => {
    //     console.log(`Tasks for ${formattedDate}:`, response.data);
    //   })
    //   .catch(error => {
    //     // console.error(`Error fetching tasks for ${formattedDate}:`, error);
    //   });
}