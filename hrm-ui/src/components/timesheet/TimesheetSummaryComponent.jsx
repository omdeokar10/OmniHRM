import React from 'react'
import './summarypage.css';
import { fetchAttendance, logPunchInTime, logPunchOutTime } from '../../service/timesheet/TimesheetService';
import { useState, useEffect } from 'react';
import { calculateHours } from '../../service/timesheet/TimesheetService';
import { fetchHoursWorked } from '../../service/timesheet/TimesheetService';
import { toastError, toastSuccess } from '../../service/ToastService';

function TimesheetSummaryComponent() {

  const [attendanceRecord, setAttendanceRecord] = useState(0);
  const [hoursRecorded, setHoursRecorded] = useState(0);
  const [minsRecorded, setMinsRecorded] = useState(0);
  const [hoursWorked, setHoursWorked] = useState(0);

  const months = ['', 'January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
  const [month, setMonth] = useState(months[new Date().getMonth() + 1]);
  const [year, setYear] = useState(new Date().getFullYear());

  function punchTimeIn() {
    logPunchInTime();
    toastSuccess('punched in for today');
  }

  function fetchAttendanceRecord() {
    var date = new Date();
    var year = date.getFullYear();
    var month = date.getMonth();
    var startDate = getStartOfMonth(year, month).toISOString().slice(0, 10);
    var endDate = getEndOfMonth(year, month).toISOString().slice(0, 10);
    fetchHoursWorked(startDate, endDate)
      .then((res) => {
        if (res && res.data) {
          setHoursWorked(res.data);
        }
      }).catch((error) => {
        console.error("Error:", error);
        toastError('error fetching hours');
      });

    fetchAttendance(startDate, endDate)
      .then((res) => {
        console.log(res.data);
        if (res && res.data.daysPresent) {
          setAttendanceRecord(res.data.daysPresent);
        }
        if (res && res.data.hoursRecorded) {
          setHoursRecorded(res.data.hoursRecorded);
        }
        if(res && res.data.minutesRecorded) {
          setMinsRecorded(res.data.minutesRecorded);
        }
      }).catch((error) => {
        toastError('error fetching data');
      });

  }

  useEffect(() => {
    fetchAttendanceRecord();
  }, []);

  const getStartOfMonth = (year, month) => {
    return new Date(year, month, 2);
  };

  const getEndOfMonth = (year, month) => {
    return new Date(year, month + 1, 1);
  };


  function punchTimeOut() {
    logPunchOutTime();
    toastSuccess('punched out for today');
  }

  return (
    <div className="parent">

      <div className="attendance">
        <div className="summary-item" onClick={punchTimeIn}>
          <a className="summary-link">Punch Time In</a>
        </div>
        <div className="summary-item" onClick={punchTimeOut}>
          <a className="summary-link">Punch Time Out</a>
        </div>
        <div className="summary-item">
          <a href="/time/attendance" className="summary-link">Attendance</a>
        </div>
        <div className="summary-item">
          <a href="/time/timesheet" className="summary-link">Timesheet</a>
        </div>
      </div>

      <div className="summary-container">
        <div className="summary-item">
          <a href="/time/timesheet" className="summary-link">{month}, {year}</a>
        </div>

        <div className="summary-item">
          <span className="summary-link">*Attendance for this month: {attendanceRecord} </span>
        </div>

        <div className="summary-item">
          <span className="summary-link">*Puched-in time recorded this month: {hoursRecorded} h: {minsRecorded} m </span>
        </div>
        <div className="summary-item">
          <span className="summary-link">*Tasks completed amounting to: {hoursWorked} hours </span>
        </div>

      </div>

      <div className='footer'>
        *Attendance & hours calculated on basis on punch in time and punch out time.
      </div>
    </div >

  )
}

export default TimesheetSummaryComponent