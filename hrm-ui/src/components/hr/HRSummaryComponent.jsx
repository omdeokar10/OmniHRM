import React, { useState } from 'react'
import { useParams, useNavigate } from 'react-router-dom'
import { useEffect } from 'react';
import { getAllEmployeesByCompany, deleteEmployeeById } from '../../service/personal/employeedetails';

function HRSummaryComponent() {

    const { url } = useParams();
    const [fullUrl, setFullUrl] = useState(`${window.location.href}`);
    const [company, setCompany] = useState('company');

    const [employees, setEmployees] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        listEmployees();
    }, [])


    function addEmployee() {
        navigate('/hr/employee/add')
    }

    function listEmployees() {
        getAllEmployeesByCompany(localStorage.getItem("companyName")).then((response) => {
            setEmployees(response.data);
        })
    }

    function updateEmployee(id) {
        navigate(`/hr/employee/add/${id}`)
    }

    function deleteEmployee(id) {
        deleteEmployeeById(id).then((response) => {
            listEmployees();
            navigate('/hr/summary');
        }).catch(error => {
            console.log(error);
        })
    }

    function viewTimeSheet(id) {
        navigate(`/hr/employeesummary/${id}`);
    }

    function submit(id) {

    }

    return (
        <div className='container'>
            <h2 className='text-center'>List of Goals</h2>
            <button className='btn btn-primary mb-2' onClick={addEmployee}>Add Employee</button>
            <div>
                <table className="minima-table">
                    <thead>
                        <tr>
                            <th>Employees</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        {employees
                            .map(employee => (
                                <tr key={employee.employeeId}>
                                    <td>{employee.userName}</td>
                                    <td>
                                        <button className="btn minima-btn mb-2" onClick={() => updateEmployee(employee.employeeId)}>Update</button>
                                        <button className="btn minima-btn mb-2" onClick={() => viewTimeSheet(employee.employeeId, employee.userName)}>View Details</button>
                                        <button className="btn minima-btn mb-2" onClick={() => deleteEmployee(employee.employeeId)}>Delete</button>
                                    </td>
                                </tr>
                            ))
                        }
                    </tbody>
                </table>
            </div>

        </div>
    )
}
export default HRSummaryComponent;
