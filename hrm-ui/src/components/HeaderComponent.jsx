import { React, useState } from 'react';
import { NavLink, useLocation, useParams } from 'react-router-dom';
import '../components/style.css';
import { isUserLoggedIn, logout } from '../service/auth/AuthService';
import { Dropdown, DropdownToggle, DropdownMenu, DropdownItem } from 'reactstrap';

const HeaderComponent = () => {
  const [dropdownOpen, setDropdownOpen] = useState({});
  const isAuth = isUserLoggedIn();
  const location = useLocation();
  const [fullUrl, setFullUrl] = useState(`${window.location.href}`);

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
    if (section === 'performance' ) {
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
        </>
      );
    } else if (section === 'time') {
      return (
        <>
          <DropdownItem><NavLink to="/time/timesheet" className="nav-link">Worksheet</NavLink></DropdownItem>
          <DropdownItem><NavLink to="/time/summary" className="nav-link">Summary</NavLink></DropdownItem>
        </>
      );
    } else if (section === 'personal') {
      return (
        <>
          <DropdownItem><NavLink to="/personal/summary" className="nav-link">Summary</NavLink></DropdownItem>
        </>
      );
    }
    else if (section === 'hr-emp') {
      return (
        <>
          <DropdownItem><NavLink to="/hr/employee/add" className="nav-link">Add Employee</NavLink></DropdownItem>
          <DropdownItem><NavLink to="/hr/createform" className="nav-link">Create form</NavLink></DropdownItem>
          <DropdownItem><NavLink to="/hr/listforms" className="nav-link">List forms</NavLink></DropdownItem>
        </>
      );
    }
    else if (section === 'hr-details') {
      return (
        <>
          <DropdownItem><NavLink to="/hr/summary" className="nav-link">View All Employees</NavLink></DropdownItem>
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

              {isAuth && fullUrl.indexOf("hr") == -1 && <li className='nav-item dropdown'>
                <Dropdown isOpen={dropdownOpen['performance']} toggle={() => toggleDropdown('performance')}>
                  <DropdownToggle nav caret>
                    Tasks
                  </DropdownToggle>
                  <DropdownMenu>
                    {renderSubsections('performance')}
                  </DropdownMenu>
                </Dropdown>
              </li>}

              {isAuth && fullUrl.indexOf("hr") == -1 && <li className='nav-item dropdown'>
                <Dropdown isOpen={dropdownOpen['leave-management']} toggle={() => toggleDropdown('leave-management')}>
                  <DropdownToggle nav caret>
                    Leaves
                  </DropdownToggle>
                  <DropdownMenu>
                    {renderSubsections('leave-management')}
                  </DropdownMenu>
                </Dropdown>
              </li>}

              {isAuth && fullUrl.indexOf("hr") == -1 && <li className='nav-item dropdown'>
                <Dropdown isOpen={dropdownOpen['time']} toggle={() => toggleDropdown('time')}>
                  <DropdownToggle nav caret>
                    Timesheet
                  </DropdownToggle>
                  <DropdownMenu>
                    {renderSubsections('time')}
                  </DropdownMenu>
                </Dropdown>
              </li>}

              {isAuth && fullUrl.indexOf("hr") == -1 && <li className='nav-item dropdown'>
                <Dropdown isOpen={dropdownOpen['personal']} toggle={() => toggleDropdown('personal')}>
                  <DropdownToggle nav caret>
                    Personal
                  </DropdownToggle>
                  <DropdownMenu>
                    {renderSubsections('personal')}
                  </DropdownMenu>
                </Dropdown>
              </li>}

              {/* HR Admin  */}
              {isAuth && fullUrl.indexOf("hr") != -1 && <li className='nav-item dropdown'>
                <Dropdown isOpen={dropdownOpen['hr-emp']} toggle={() => toggleDropdown('hr-emp')}>
                  <DropdownToggle nav caret>
                    Services
                  </DropdownToggle>
                  <DropdownMenu>
                    {renderSubsections('hr-emp')}
                  </DropdownMenu>
                </Dropdown>
              </li>}

              {isAuth && fullUrl.indexOf("hr") != -1 && <li className='nav-item dropdown'>
                <Dropdown isOpen={dropdownOpen['hr-details']} toggle={() => toggleDropdown('hr-details')}>
                  <DropdownToggle nav caret>
                    Employee Details
                  </DropdownToggle>
                  <DropdownMenu>
                    {renderSubsections('hr-details')}
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
