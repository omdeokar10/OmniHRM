import React from 'react'
import { useState, useEffect } from 'react';
import { getCurrentGlobalDate, startDayOfWorkingWeek, endDayOfWorkingWeek, getCurrentMonth, getYear, fetchTasksForDate, getNextWeekStart } from '../../service/timesheet/TimesheetService';
import axios from "axios";

const TimesheetComponent = () => {
  const hours = Array.from({ length: 24 }, (_, i) => i);
  const days = ['Mon', 'Tues', 'Wed', 'Thurs', 'Fri'];
  const months = ['','January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
  const [tasks, setTasks] = useState({});
  // const [startVal, setStartVal] = useState(0);
  // const [endVal, setEndVal] = useState(0);
  const [year, setYear] = useState(0);
  const [weekDates, setWeekDates] = useState([]);
  const [globalDate, setGlobalDate] = useState(getCurrentGlobalDate());
  const [startDay, setStartDay] = useState(getCurrentGlobalDate().getDate());
  const [currentMonth, setCurrentMonth] = useState(getCurrentGlobalDate().getMonth());
  const [currentYear, setCurrentYear] = useState(getYear());

  useEffect(() => {

    console.log(globalDate.getMonth());
    setCurrentMonth(globalDate.getMonth()+1);
    setYear(globalDate.getFullYear());
    setCurrentYear(globalDate.getFullYear());

    const dates = getWeekDates(startDay, currentMonth, currentYear);
    setWeekDates(dates);

    dates.forEach(date =>
      fetchTasksForDate(date)
        .then(response => {
          setTasks((prevtasks) => {
            var dateString = date.year + "-" + date.month + "-" + date.date;
            return { ...prevtasks, [dateString]: response.data };
          })
        })

        .catch(error => {
          console.error(`Error fetching tasks for ${formattedDate}:`, error);
        }));


  }, [globalDate, startDay])

  const getWeekDates = (startDay, month, year) => {
    const dates = [];
    for (let i = 0; i < 5; i++) {
      const date = new Date(year, month, startDay + i);
      
      dates.push({
        day: days[i],
        date: String(date.getDate()).padStart(2, '0'),
        month: String(date.getMonth()+1).padStart(2, '0'), // 0 based in js , 1 based in db/backend
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

  const nextweek = () => {
    console.log('global dates' + globalDate);
    var dateString = new Date(year + "-" + currentMonth + "-" + startDay);
    var nextWeek = new Date(dateString.getTime() + 7 * 24 * 60 * 60 * 1000);
    setGlobalDate(nextWeek);
    setStartDay(nextWeek.getDate());
    setCurrentMonth(nextWeek.getMonth());
  }


  const pastweek = () => {
    console.log('global dates' + globalDate);
    var dateString = new Date(year + "-" + currentMonth + "-" + startDay);
    var nextWeek = new Date(dateString.getTime() - 7 * 24 * 60 * 60 * 1000);
    setGlobalDate(nextWeek);
    setStartDay(nextWeek.getDate());
    setCurrentMonth(nextWeek.getMonth());
  }


  return (
    <div className="container">
      <div className="header">
        <button className="nav-button" onClick={pastweek}>{'<'}</button>
        <span className='m-2'>Week of {startDay} {months[currentMonth]} {year}</span>
        <button className="nav-button" onClick={nextweek}>{'>'}</button>
      </div>
      <div className="week-container">
        {

          weekDates.map(({ day, date, month, year }) => {
            var dateString = year + "-" + month + "-" + date;
            const dayTasks = tasks[dateString] || [];

            return (
              <div key={day} className="day-column">
                <div className="day-header">
                  {day}
                </div>
                <div className="day-header">
                  {date} {month} {year}
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
                    <div className="no-tasks">Not present</div>
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