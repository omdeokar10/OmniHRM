import React from 'react';
import { NavLink, useLocation } from 'react-router-dom';
import '../components/style.css';
import { isUserLoggedIn, logout } from '../service/auth/AuthService';

const HeaderComponent = () => {
  const isAuth = isUserLoggedIn();
  const location = useLocation();

  function callLogout() {
    logout();
  }

  const renderSubsections = () => {
    const path = location.pathname;

    if (path.startsWith('/performance')) {
      return (
        <ul>
          <li><NavLink to="/performance/listforms" className="nav-link">Forms</NavLink></li>
          <li><NavLink to="/performance/listgoal" className="nav-link">Goals</NavLink></li>
          <li><NavLink to="/performance/summary" className="nav-link">Summary</NavLink></li>
        </ul>
      )
    } else if (path.startsWith('/leave-management')) {
      return (
        <ul>
          <li><NavLink to="/leave-management/subsection1" className="nav-link">Leaves Subsection 1</NavLink></li>
          <li><NavLink to="/leave-management/subsection2" className="nav-link">Leaves Subsection 2</NavLink></li>
        </ul>
      );
    } else if (path.startsWith('/time-track')) {
      return (
        <ul>
          <li><NavLink to="/time-track/subsection1" className="nav-link">Timesheet Subsection 1</NavLink></li>
          <li><NavLink to="/time-track/subsection2" className="nav-link">Timesheet Subsection 2</NavLink></li>
        </ul>
      );
    } else if (path.startsWith('/salary')) {
      return (
        <ul>
          <li><NavLink to="/salary/subsection1" className="nav-link">Salary Subsection 1</NavLink></li>
          <li><NavLink to="/salary/subsection2" className="nav-link">Salary Subsection 2</NavLink></li>
        </ul>
      );
    }
    return null;
  };

  return (
    <div className="app-container">
      <header>
        <nav className='navbar'>
          <div className='navbar-brand'>
            HRM Tool
          </div>
          <div className='navbar-links'>
            <ul className='m-2'>
              {isAuth && <li>
                <NavLink to="/performance-management" className="nav-link">Tasks</NavLink>
              </li>}
              {isAuth && <li>
                <NavLink to="/leave-management" className="nav-link">Leaves</NavLink>
              </li>}
              {isAuth && <li>
                <NavLink to="/time-track" className="nav-link">Timesheet</NavLink>
              </li>}
              {isAuth && <li>
                <NavLink to="/finance" className="nav-link">Finance</NavLink>
              </li>}
              {isAuth && <li>
                <NavLink to="/" onClick={callLogout} className="nav-link">Logout</NavLink>
              </li>}
            </ul>
          </div>
        </nav>
      </header>
      <aside className="sidebar">
        {renderSubsections()}
      </aside>
      {/* <main className="content">
        
      </main> */}
    </div>
  );
}

export default HeaderComponent;
