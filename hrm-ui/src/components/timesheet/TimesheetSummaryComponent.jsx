import React from 'react'
import './summarypage.css';
import { fetchAttendance, logPunchInTime, logPunchOutTime } from '../../service/timesheet/TimesheetService';
import { useState, useEffect } from 'react';
import { calculateHours } from '../../service/timesheet/TimesheetService';

function TimesheetSummaryComponent() {

  const [attendanceRecord, setAttendanceRecord] = useState(0);
  const [hoursWorked, setHoursWorked] = useState(0);

  const months = ['', 'January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
  const [month, setMonth] = useState(months[new Date().getMonth() + 1]);
  const [year, setYear] = useState(new Date().getFullYear());
  function punchTimeIn() {
    logPunchInTime();
  }
  function fetchAttendanceRecord() {
    var date = new Date();
    var year = date.getFullYear();
    var month = date.getMonth();
    var startDate = getStartOfMonth(year, month).toISOString().slice(0, 10);
    var endDate = getEndOfMonth(year, month).toISOString().slice(0, 10);
    fetchAttendance(startDate, endDate)
      .then((res) => {
        var { count, hours } = calculateHours(res);
        setAttendanceRecord(count);
        setHoursWorked(hours);
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
          <span className="summary-link">*Hours worked this month: {hoursWorked} </span>
        </div>

      </div>

      <div className='footer'>
        *Attendance & hours calculated on basis on punch in time and punch out time.
      </div>
    </div >

  )
}

export default TimesheetSummaryComponent