import { useState } from 'react'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import './App.css'
import {Toaster} from 'sonner'

import HeaderComponent from './components/HeaderComponent'
import FooterComponent from './components/FooterComponent'
import PerformanceComponent from './components/performance/PerformanceComponent'
import LoginComponent from './components/auth/LoginComponent'
import CreateFormComponent from './components/performance/CreateFormComponent'
import ShowFormComponent from './components/performance/ShowFormComponent'
import AreasOfImprovement from './components/performance/AreasOfImprovement'
import CreateGoalComponent from './components/performance/CreateGoalComponent'
import ListGoals from './components/performance/ListGoals'
import ListAllPendingFormComponent from './components/performance/ListAllPendingFormComponent'
import SummaryComponent from './components/performance/SummaryComponent'
import AttendanceComponent from './components/timesheet/AttendanceComponent'
import TimesheetSummaryComponent from './components/timesheet/TimesheetSummaryComponent'
import TimesheetComponent from './components/timesheet/TimesheetComponent'


function App() {
  const [count, setCount] = useState(0)

  return (
    <>
      <BrowserRouter>
        <HeaderComponent />
        <Toaster />
        <Routes>
          <Route path='/' element={<LoginComponent />}></Route>
          <Route path='/performance' element={< PerformanceComponent />}></Route>

          <Route path='/performance/creategoal' element={< CreateGoalComponent />}></Route>
          <Route path='/performance/updategoal/:id' element={< CreateGoalComponent />}></Route>
          <Route path='/performance/listgoal' element={< ListGoals />}></Route>

          <Route path='/performance/createform' element={< CreateFormComponent />}></Route>
          <Route path='/performance/listforms' element={< ListAllPendingFormComponent />}></Route>
          <Route path='/performance/showforms/:id' element={< ShowFormComponent />}></Route>

          <Route path='/performance/areasofimprovement' element={< AreasOfImprovement />}></Route>
          <Route path='/performance/summary' element={< SummaryComponent />}></Route>
          
          <Route path='/time/attendance' element={< AttendanceComponent />}></Route>
          <Route path='/time/timesheet' element={< TimesheetComponent />}></Route>
          <Route path='/time/summary' element={< TimesheetSummaryComponent />}></Route>

        </Routes>


        <FooterComponent />
      </BrowserRouter>
    </>
  )
}

export default App
