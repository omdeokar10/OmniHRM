import React from 'react'
import { useState, useEffect } from 'react';
import { getCurrentGlobalDate, getYear, fetchTasksForDate, addTask, deleteTask } from '../../service/timesheet/TimesheetService';

const TimesheetComponent = () => {
  const hours = Array.from({ length: 24 }, (_, i) => i);
  const days = ['Mon', 'Tues', 'Wed', 'Thurs', 'Fri'];
  const months = ['', 'January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
  const [tasks, setTasks] = useState({});
  const [showModal, setShowModal] = useState(false);
  const [weekDates, setWeekDates] = useState([]);
  const [globalDate, setGlobalDate] = useState(getCurrentGlobalDate());
  const [startDay, setStartDay] = useState(getCurrentGlobalDate().getDate());
  const [currentMonth, setCurrentMonth] = useState(getCurrentGlobalDate().getMonth());
  const [currentYear, setCurrentYear] = useState(getYear());
  const [description, setDescription] = useState('');
  const [durationLogged, setDurationLogged] = useState('');
  const [createdDate, setCreatedDate] = useState(getCurrentGlobalDate().toISOString().slice(0, 10));
  const [update, setUpdate] = useState(false);

  useEffect(() => {
    const dates = getWeekDates(globalDate.getDate(), globalDate.getMonth(), globalDate.getFullYear());
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


  }, [globalDate, startDay, update])

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
    addTask(description, durationLogged, createdDate)
      .then(() => {
        console.log('setting update');
        setUpdate(true);
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
    var dateString = new Date(currentYear + "-" + (currentMonth + 1) + "-" + startDay);
    var nextWeek = new Date(dateString.getTime() + 7 * 24 * 60 * 60 * 1000);
    setGlobalDate(nextWeek);
    setStartDay(nextWeek.getDate());
    setCurrentMonth(nextWeek.getMonth());
    setCurrentYear(nextWeek.getFullYear());

  }


  const pastweek = () => {
    var dateString = new Date(currentYear + "-" + (currentMonth + 1) + "-" + startDay);
    var nextWeek = new Date(dateString.getTime() - 7 * 24 * 60 * 60 * 1000);
    setGlobalDate(nextWeek);
    setStartDay(nextWeek.getDate());
    setCurrentMonth(nextWeek.getMonth());
    setCurrentYear(nextWeek.getFullYear());
    console.log('past week - current month' + months[nextWeek.getMonth() + 1]);
  }


  function handleDeleteTask(taskId, dateString) {
    console.log('delete task' + taskId + ':' + dateString);
    const confirmDelete = window.confirm("Are you sure you want to delete this task?");
    if (confirmDelete) {
      deleteTask(taskId)
        .then(() => {
          setTasks((prevTasks) => {
            const updatedTasks = { ...prevTasks };
            updatedTasks[dateString] = updatedTasks[dateString].filter(task => task.id !== taskId);
            return updatedTasks;
          });
          setUpdate(prev => !prev);
        })
        .catch(error => {
          console.error(`Error deleting task ${taskId}:`, error);
        });
    }
  }

  return (
    <div className="container">
      <div className="header">
        <button className="nav-button" onClick={pastweek}>{'<'}</button>
        <span className='m-2'>Week of {startDay} {months[currentMonth]} {currentYear}</span>
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
                      <div key={task.id} className="task" onDoubleClick={() => handleDeleteTask(task.id, dateString)}>
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
      <div>
        <span className='text-danger'><hr></hr>Double click deletes the task <hr></hr></span>
      </div>
    </div >

  );

};

export default TimesheetComponent