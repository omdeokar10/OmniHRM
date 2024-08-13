import React from 'react'
import { calculateHours } from '../../service/timesheet/TimesheetService';
import { fetchAttendanceForUser } from '../../service/timesheet/TimesheetService';
import { useParams } from 'react-router-dom';
import { useState } from 'react';
import { useEffect } from 'react';
function HRDetailsSummaryComponent() {

    const [attendanceRecord, setAttendanceRecord] = useState(0);
    const [hoursWorked, setHoursWorked] = useState(0);
    const {id} = useParams();
    const months = ['', 'January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
    const [month, setMonth] = useState(months[new Date().getMonth() + 1]);
    const [year, setYear] = useState(new Date().getFullYear());

    function fetchAttendanceRecord() {
        var date = new Date();
        var year = date.getFullYear();
        var month = date.getMonth();
        var startDate = getStartOfMonth(year, month).toISOString().slice(0, 10);
        var endDate = getEndOfMonth(year, month).toISOString().slice(0, 10);

        fetchAttendanceForUser(startDate, endDate, id)
            .then((res) => {
                var { count, hours } = calculateHours(res);
                setAttendanceRecord(count);
                setHoursWorked(hours);
            });

    }

    const getStartOfMonth = (year, month) => {
        return new Date(year, month, 2);
    };

    const getEndOfMonth = (year, month) => {
        return new Date(year, month + 1, 1);
    };

    useEffect(() => {
        fetchAttendanceRecord();
    }, []);


    return (
        <div>
            <div className="summary-container">
                <div className="summary-item">
                    <a href="/time/timesheet" className="summary-link">{month}, {year}</a>
                </div>

                <div className="summary-item">
                    <span className="summary-link">*Attendance for this month: {attendanceRecord} </span>
                </div>

                <div className="summary-item">
                    <span className="summary-link">*Hours worked this month: {hoursWorked} </span>
                </div>

            </div>

        </div>
    )
}

export default HRDetailsSummaryComponent