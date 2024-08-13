import React, { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom';
import { useParams } from 'react-router-dom';
import { } from '../../service/company/CompanyService';
import { getEmployeeById, updateEmployee, addEmployee } from '../../service/personal/employeedetails';

const AddEmployeeComponent = () => {

    const [employee, setEmployee] = useState({
        fullName: '',
        managerEmail: '',
        userName: '',
        hireDate: '',
        email: '',
        baseSalary: 0,
        bonusAllotted: 0,
        jobProfile: '',
        companyName: '',
        businessTitle: '',

        telephone: '',
        gender: '',
        dateOfBirth: '',
        age: '',
        countryOfBirth: '',
        maritalStatus: '',
        dependantName: '',
        relationToDependant: '',

        team: ''
    });
    const navigate = useNavigate();
    const { id } = useParams();

    const handleChange = (e) => {
        const { name, value } = e.target;
        setEmployee((prev) => ({
            ...prev,
            [name]: value
        }));
    };

    const handleSubmit = (e) => {

        e.preventDefault();
        try {
            if (id) {
                updateEmployee(id, employee).then((res) => {
                    navigate('/hr/summary');
                });
            }
            else {
                employee.companyName = localStorage.getItem("companyName");
                addEmployee(employee).then((res) => {
                    navigate('/hr/summary');
                });
            }
        }
        catch (error) {
            console.log('response is:' + error);
        }
    };

    function fetchAndUpdate(id) {
        getEmployeeById(id).then((res) => {
            setEmployee(res.data);
        });
    }

    useEffect(() => {
        if (id) {
            fetchAndUpdate(id);
        }
    }, [id])

    return (
        <div className="container">
            <form onSubmit={handleSubmit}>
                <div className="form-group mt-5">
                    <label htmlFor="title">Full Name</label>
                    <input type="text" className="form-control" id="fullName" name="fullName" value={employee.fullName} onChange={handleChange} placeholder="Full name" required />
                </div>

                <div className="form-group">
                    <label htmlFor="managerEmail">Manager Email</label>
                    <input type="text" className="form-control" id="managerEmail" name="managerEmail" value={employee.managerEmail} onChange={handleChange} placeholder="Manager name" />
                </div>

                <div className="form-group">
                    <label htmlFor="userName">User name</label>
                    <input type='text' className="form-control" id="userName" name="userName" value={employee.userName} onChange={handleChange} placeholder="User name" required />
                </div>

                <div className="form-group">
                    <label htmlFor="hireDate">Hire date</label>
                    <input type="date" className="form-control" id="hireDate" name="hireDate" value={employee.hireDate} onChange={handleChange} placeholder="Hire date" />
                </div>

                <div className="form-group">
                    <label htmlFor="email">Email</label>
                    <input type="text" className="form-control" id="email" name="email" value={employee.email} onChange={handleChange} required />
                </div>

                <div className="form-group">
                    <label htmlFor="baseSalary">Base salary</label>
                    <input type="number" className="form-control" id="baseSalary" name="baseSalary" value={employee.baseSalary} onChange={handleChange} required placeholder="Base salary" />
                </div>

                <div className="form-group">
                    <label htmlFor="bonusAllotted">Bonus alloted</label>
                    <input type="number" className="form-control" id="bonusAllotted" name="bonusAllotted" value={employee.bonusAllotted} onChange={handleChange} placeholder="Bonus alloted" />
                </div>

                <div className="form-group">
                    <label htmlFor="jobProfile">Job Profile</label>
                    <input type="text" className="form-control" id="jobProfile" name="jobProfile" value={employee.jobProfile} placeholder="Business title" onChange={handleChange} required />
                </div>

                <div className="form-group">
                    <label htmlFor="businessTitle">Business Title</label>
                    <input type="text" className='form-control' id="businessTitle" name="businessTitle" value={employee.businessTitle} placeholder="Business title" onChange={handleChange} required />
                </div>

                <div className="form-group">
                    <label htmlFor="team">Team</label>
                    <input type="text" className='form-control' id="team" name="team" value={employee.team} placeholder="Assigned Team" onChange={handleChange} />
                </div>



                <div className="form-group">
                    <label htmlFor="telephone">Telephone</label>
                    <input type="text" className='form-control' id="telephone" name="telephone" value={employee.telephone} placeholder="Mobile/Telephone" onChange={handleChange} />
                </div>

                <div className="form-group">
                    <label htmlFor="gender">Gender</label>
                    <select className='form-control' id="gender" name="gender" value={employee.gender} placeholder="Gender" onChange={handleChange}>
                        <option value="all">All</option>
                        <option value="male">Male</option>
                        <option value="female">Female</option>
                    </select>
                </div>

                <div className="form-group">
                    <label htmlFor="dateOfBirth">Date of birth</label>
                    <input type="date" className='form-control' id="dateOfBirth" name="dateOfBirth" value={employee.dateOfBirth} placeholder="Date of Birth" onChange={handleChange} />
                </div>

                <div className="form-group">
                    <label htmlFor="age">Age</label>
                    <input type="number" className='form-control' id="age" name="age" value={employee.age} placeholder="Age" onChange={handleChange} />
                </div>

                <div className="form-group">
                    <label htmlFor="countryOfBirth">Country of birth</label>
                    <input type="text" className='form-control' id="countryOfBirth" name="countryOfBirth" value={employee.countryOfBirth} placeholder="Country of Birth" onChange={handleChange} />
                </div>

                <div className="form-group">
                    <label htmlFor="maritalStatus">Maritial Status</label>
                    <input type="text" className='form-control' id="maritalStatus" name="maritalStatus" value={employee.maritalStatus} placeholder="Maritial Status" onChange={handleChange} />
                </div>

                <div className="form-group">
                    <label htmlFor="dependantName">Dependant Name</label>
                    <input type="text" className='form-control' id="dependantName" name="dependantName" value={employee.dependantName} placeholder="Dependant Name" onChange={handleChange} />
                </div>

                <div className="form-group">
                    <label htmlFor="relationToDependant">Relation To Dependant</label>
                    <input type="text" className='form-control' id="relationToDependant" name="relationToDependant" value={employee.relationToDependant} placeholder="Relation To Dependant" onChange={handleChange} />
                </div>

                <br />
                <button type="submit" className="btn btn-primary">Submit</button>
            </form>
        </div>
    );

}

export default AddEmployeeComponent