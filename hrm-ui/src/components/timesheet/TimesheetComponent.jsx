import React from 'react'
import { useState, useEffect } from 'react';
import { startDayOfWorkingWeek, endDayOfWorkingWeek, getCurrentMonth, getYear, fetchTasksForDate } from '../../service/timesheet/TimesheetService';
import axios from "axios";

const TimesheetComponent = () => {
  const hours = Array.from({ length: 24 }, (_, i) => i);
  const days = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday'];
  const months = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
  const [tasks, setTasks] = useState({});
  const [startVal, setStartVal] = useState(0);
  const [endVal, setEndVal] = useState(0);
  const [month, setMonth] = useState('');
  const [year, setYear] = useState(0);
  const [weekDates, setWeekDates] = useState([]);

  useEffect(() => {

    const startDay = startDayOfWorkingWeek();
    const endDay = endDayOfWorkingWeek();
    const currentMonth = getCurrentMonth();
    const currentYear = getYear();

    setStartVal(startDay);
    setEndVal(endDay);
    setMonth(currentMonth);
    setYear(currentYear);

    const dates = getWeekDates(startDay, currentMonth, currentYear);
    setWeekDates(dates);

    dates.forEach(date => 
      fetchTasksForDate(date)
      .then(response => {
        setTasks((prevtasks) => {
          var dateString = date.year + "-" + date.month + "-" + date.date;
          return {...prevtasks, [dateString]: response.data};
        })
      })
      
      .catch(error => {
        console.error(`Error fetching tasks for ${formattedDate}:`, error);
      }));


}, [])

const getWeekDates = (startDay, month, year) => {
  const dates = [];
  for (let i = 0; i < 5; i++) {
    const date = new Date(year, month, startDay + i);
    const monthStr = date.getMonth() < 10 ? "0" + (date.getMonth() + 1) : date.getMonth();
    dates.push({
      day: days[i],
      date: date.getDate(),
      month: monthStr,
      year: date.getFullYear(),
    });
  }
  return dates;
};

const handleTaskSubmit = (event) => {
  event.preventDefault();
  const formData = new FormData(event.target);
  const task = formData.get('task');
};

return (
  <div className="container">
    <div className="header">
      <button className="nav-button">{'<'}</button>
      <span className='m-2'>Week ({startVal} to {endVal} {months[month]} {year})</span>
      <button className="nav-button">{'>'}</button>
    </div>
    <div className="week-container">
      {
      
      weekDates.map(({ day, date, month, year }) => {
        console.log(tasks);
        var dateString = year + "-" + month + "-" + date;
        const dayTasks = tasks[dateString] || [];

        return (
          <div key={day} className="day-column">
            <div className="day-header">
              {day}
            </div>
            <div className="tasks">
              {dayTasks.length > 0 ? (
                dayTasks.map(task => (
                  <div key={task.id} className="task">
                    <div className="task-description">{task.description}</div>
                    <div className="task-duration">{task.durationLogged}</div>
                  </div>
                ))
              ) : (
                <div className="no-tasks">No tasks</div>
              )}
            </div>
          </div>
        );
      })}
    </div>

  </div>

);

};

export default TimesheetComponent