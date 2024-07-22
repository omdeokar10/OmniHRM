import React from 'react'
import './summarypage.css';
import { logPunchInTime, logPunchOutTime } from '../../service/timesheet/TimesheetService';

function TimesheetSummaryComponent() {

  function punchTimeIn() {
    logPunchInTime();
  }

  function punchTimeOut() {
    logPunchOutTime();
  }

  return (
    <div className="summary-container">
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
  )
}

export default TimesheetSummaryComponent