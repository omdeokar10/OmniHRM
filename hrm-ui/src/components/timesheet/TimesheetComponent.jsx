import React from 'react'
import { useState, useEffect } from 'react';
import { getCurrentGlobalDate, startDayOfWorkingWeek, endDayOfWorkingWeek, getCurrentMonth, getYear, fetchTasksForDate, getNextWeekStart, addTask } from '../../service/timesheet/TimesheetService';
import axios from "axios";
import { getLoggedInUser } from '../../service/auth/AuthService';
import { useNavigate, useParams } from 'react-router-dom'

const TimesheetComponent = () => {
  const hours = Array.from({ length: 24 }, (_, i) => i);
  const days = ['Mon', 'Tues', 'Wed', 'Thurs', 'Fri'];
  const months = ['', 'January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
  const [tasks, setTasks] = useState({});
  const [showModal, setShowModal] = useState(false);
  const [year, setYear] = useState(0);
  const [weekDates, setWeekDates] = useState([]);
  const [globalDate, setGlobalDate] = useState(getCurrentGlobalDate());
  const [startDay, setStartDay] = useState(getCurrentGlobalDate().getDate());
  const [currentMonth, setCurrentMonth] = useState(getCurrentGlobalDate().getMonth());
  const [currentYear, setCurrentYear] = useState(getYear());
  const [description, setDescription] = useState('');
  const [durationLogged, setDurationLogged] = useState('');
  const [createdDate, setCreatedDate] = useState(getCurrentGlobalDate().toISOString().slice(0, 10));

  useEffect(() => {

    console.log(globalDate.getMonth());
    setCurrentMonth(getCurrentGlobalDate().getMonth());
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

  const handleCloseModal = () => {
    setShowModal(false);
  };

  const handleSubmitModal = () => {
    setShowModal(false);
    addTimeSheetTask();
  };

  const handleShowModal = () => {
    setShowModal(true);
  };

  function addTimeSheetTask() {
    var loggedInUser = getLoggedInUser();
    addTask(description, durationLogged, createdDate)
      .then(() => {
        that.setState({ update: true })
      })
      .catch(function (err) {
        console.log(err)
      })
  }

  const getWeekDates = (startDay, month, year) => {
    const dates = [];
    for (let i = 0; i < 5; i++) {
      const date = new Date(year, month, startDay + i);

      dates.push({
        day: days[i],
        date: String(date.getDate()).padStart(2, '0'),
        month: String(date.getMonth() + 1).padStart(2, '0'), // 0 based in js , 1 based in db/backend
        year: date.getFullYear(),
      });
    }
    return dates;
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
      <button type="button" className="btn btn-secondary m-4" onClick={handleShowModal}>
        Add task
      </button>
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


      {showModal && (
        <div className="modal show d-block" tabIndex="-1">
          <div className="modal-dialog">
            <div className="modal-content">
              <div className="modal-header">
                <h5 className="modal-title">Add task</h5>
              </div>

              <div className='form-group mb-2'>

                <label className='form-label'>Todo Description:</label>
                <input
                  type='text'
                  className='form-control'
                  placeholder='Enter Task Description'
                  name='description'
                  value={description}
                  onChange={(e) => setDescription(e.target.value)}
                >
                </input>
                <label className='form-label'>Task Duration (hh:mm:ss) </label>
                <input
                  type='text'
                  className='form-control'
                  placeholder='Enter Duration'
                  name='durationLogged'
                  value={durationLogged}
                  onChange={(e) => setDurationLogged(e.target.value)}
                ></input>


                <label className='form-label'>Task Date (yyyy-mm-dd) </label>
                <input
                  type='text'
                  className='form-control'
                  placeholder='Enter Created Date (yyyy-mm-dd)'
                  name='createdDate'
                  value={createdDate}
                  onChange={(e) => setCreatedDate(e.target.value)}
                ></input>

              </div>

              <div className="modal-footer">
                <button type="button" className="btn btn-primary" onClick={handleSubmitModal}>Create</button>
                <button type="button" className="btn btn-secondary" onClick={handleCloseModal}>Close</button>
              </div>
            </div>
          </div>
        </div>
      )
      }

    </div >

  );

};

export default TimesheetComponent