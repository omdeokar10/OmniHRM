import { useState } from 'react'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import './App.css'
import {Toaster} from 'sonner'

import HeaderComponent from './components/HeaderComponent'
import FooterComponent from './components/FooterComponent'
import PerformanceComponent from './components/PerformanceComponent'
import LoginComponent from './components/LoginComponent'
import CreateFormComponent from './components/CreateFormComponent'
import ShowFormComponent from './components/ShowFormComponent'

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
          <Route path='/addform' element={< CreateFormComponent />}></Route>
          <Route path='/showform' element={< ShowFormComponent />}></Route>
        </Routes>


        <FooterComponent />
      </BrowserRouter>
    </>
  )
}

export default App
