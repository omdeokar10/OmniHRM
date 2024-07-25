import { React, useState } from 'react';
import { NavLink, useLocation } from 'react-router-dom';
import '../components/style.css';
import { isUserLoggedIn, logout } from '../service/auth/AuthService';
import { Dropdown, DropdownToggle, DropdownMenu, DropdownItem } from 'reactstrap';

const HeaderComponent = () => {
  const [dropdownOpen, setDropdownOpen] = useState({});
  const isAuth = isUserLoggedIn();
  const location = useLocation();

  function callLogout() {
    logout();
  }

  const toggleDropdown = (section) => {
    setDropdownOpen(prevState => ({
      ...prevState,
      [section]: !prevState[section]
    }));
  };

  const renderSubsections = (section) => {
    if (section === 'performance') {
      return (
        <>
          <DropdownItem><NavLink to="/performance/listforms" className="nav-link">Forms</NavLink></DropdownItem>
          <DropdownItem><NavLink to="/performance/listgoal" className="nav-link">Goals</NavLink></DropdownItem>
          <DropdownItem><NavLink to="/performance/summary" className="nav-link">Summary</NavLink></DropdownItem>
        </>
      );
    } else if (section === 'leave-management') {
      return (
        <>
          <DropdownItem><NavLink to="/leave-management/subsection1" className="nav-link">Leaves Subsection 1</NavLink></DropdownItem>
          <DropdownItem><NavLink to="/leave-management/subsection2" className="nav-link">Leaves Subsection 2</NavLink></DropdownItem>
        </>
      );
    } else if (section === 'time') {
      return (
        <>
          <DropdownItem><NavLink to="/time/timesheet" className="nav-link">Worksheet</NavLink></DropdownItem>
          <DropdownItem><NavLink to="/time/summary" className="nav-link">Summary</NavLink></DropdownItem>
        </>
      );
    } else if (section === 'salary') {
      return (
        <>
          <DropdownItem><NavLink to="/salary/subsection1" className="nav-link">Salary Subsection 1</NavLink></DropdownItem>
          <DropdownItem><NavLink to="/salary/subsection2" className="nav-link">Salary Subsection 2</NavLink></DropdownItem>
        </>
      );
    }
    return null;
  };

  return (
    <div className="app-container">
       <header>
        <nav className='navbar navbar-expand-lg navbar-light bg-light'>
          <div className='navbar-brand'>
            HRM Tool
          </div>
          <div className='collapse navbar-collapse'>
            <ul className='navbar-nav mr-auto'>
              {isAuth && <li className='nav-item dropdown'>
                <Dropdown isOpen={dropdownOpen['performance']} toggle={() => toggleDropdown('performance')}>
                  <DropdownToggle nav caret>
                    Tasks
                  </DropdownToggle>
                  <DropdownMenu>
                    {renderSubsections('performance')}
                  </DropdownMenu>
                </Dropdown>
              </li>}
              {isAuth && <li className='nav-item dropdown'>
                <Dropdown isOpen={dropdownOpen['leave-management']} toggle={() => toggleDropdown('leave-management')}>
                  <DropdownToggle nav caret>
                    Leaves
                  </DropdownToggle>
                  <DropdownMenu>
                    {renderSubsections('leave-management')}
                  </DropdownMenu>
                </Dropdown>
              </li>}
              {isAuth && <li className='nav-item dropdown'>
                <Dropdown isOpen={dropdownOpen['time']} toggle={() => toggleDropdown('time')}>
                  <DropdownToggle nav caret>
                    Timesheet
                  </DropdownToggle>
                  <DropdownMenu>
                    {renderSubsections('time')}
                  </DropdownMenu>
                </Dropdown>
              </li>}
              {isAuth && <li className='nav-item dropdown'>
                <Dropdown isOpen={dropdownOpen['salary']} toggle={() => toggleDropdown('salary')}>
                  <DropdownToggle nav caret>
                    Finance
                  </DropdownToggle>
                  <DropdownMenu>
                    {renderSubsections('salary')}
                  </DropdownMenu>
                </Dropdown>
              </li>}
              {isAuth && <li className='nav-item'>
                <NavLink to="/" onClick={callLogout} className="nav-link">Logout</NavLink>
              </li>}
            </ul>
          </div>
        </nav>
      </header>
      {/* <main className="content">
        
      </main> */}
    </div>
  );
}

export default HeaderComponent;
