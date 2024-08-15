import React, { useEffect, useState } from 'react';
import { fetchDetailsCurrentEmployee, fetchDetailsForHierarchy } from '../../service/personal/employeedetails';
import '../personal/style.css';

function EmployeeDetails() {
    const [activeTab, setActiveTab] = useState('job');
    const [details, setDetails] = useState({});
    const [hierarchy, setHierarchy] = useState({});

    useEffect(() => {

        fetchDetailsCurrentEmployee().then((response) => {
            setDetails(response.data);
        });

        fetchDetailsForHierarchy().then((res) => {
            setHierarchy(res.data);
        });

    }, []);

    const renderContent = () => {
        switch (activeTab) {
            case 'job':
                return <JobDetails details={details} />;
            case 'personal':
                return <PersonalDetails details={details} />;
            case 'salary':
                return <SalaryDetails details={details} />;
            case 'hierarchy':
                return <HierarchyDetails hierarchy={hierarchy} />;
            default:
                return null;
        }
    };

    return (
        <div className="employee-details">
            <h2>Employee Details</h2>
            <div className="tabs">
                <button
                    className={`tab-button ${activeTab === 'job' ? 'active' : ''}`}
                    onClick={() => setActiveTab('job')}
                >
                    Job Details
                </button>
                <button
                    className={`tab-button ${activeTab === 'personal' ? 'active' : ''}`}
                    onClick={() => setActiveTab('personal')}
                >
                    Personal Details
                </button>
                <button
                    className={`tab-button ${activeTab === 'salary' ? 'active' : ''}`}
                    onClick={() => setActiveTab('salary')}
                >
                    Salary Details
                </button>
                <button
                    className={`tab-button ${activeTab === 'hierarchy' ? 'active' : ''}`}
                    onClick={() => setActiveTab('hierarchy')}
                >
                    Hierarchy Details
                </button>

            </div>
            <div className="tab-content">
                {renderContent()}
            </div>
        </div>
    );
};

const JobDetails = ({ details }) => (
    <div>
        <h3>Job Details</h3>
        <p><strong>Team:</strong> {details.team}</p>
        <p><strong>Business Title:</strong> {details.businessTitle}</p>
        <p><strong>Job Profile:</strong> {details.jobProfile}</p>
        <p><strong>Location:</strong> {details.location}</p>
        <p><strong>Hire Date:</strong> {details.hireDate}</p>
        <p><strong>Length of Service:</strong> {details.lengthOfService}</p>
        <p><strong>Telephone:</strong> {details.telephone}</p>
        <p><strong>Email:</strong> {details.email}</p>
        <p><strong>Work Address:</strong> {details.workAddress}</p>
    </div>
);

const HierarchyDetails = ({ hierarchy }) => {
    console.log('Hierarchy data received:', hierarchy);
    return (
        <div className="hierarchy-container">
            {hierarchy.length === 0 ? (
                <p>No hierarchy data available.</p>
            ) : (
                hierarchy
                    
                    .map((employee, index) => (
                        <div key={index} className="hierarchy-box">
                            <p><strong>Username:</strong> {employee.username}</p>
                            <p><strong>Email:</strong> {employee.email}</p>
                            <p><strong>Manager:</strong> {employee.managerUsername}</p>
                        </div>
                    ))
            )}
        </div>
    );
};

const PersonalDetails = ({ details }) => (
    <div>
        <h3>Personal Details</h3>
        <p><strong>Gender:</strong> {details.gender}</p>
        <p><strong>Date of Birth:</strong> {details.dateOfBirth}</p>
        <p><strong>Age:</strong> {details.age}</p>
        <p><strong>Country of Birth:</strong> {details.countryOfBirth}</p>
        <p><strong>City of Birth:</strong> {details.cityOfBirth}</p>
        <p><strong>Marital Status:</strong> {details.maritalStatus}</p>
        <p><strong>Nationality:</strong> {details.nationality}</p>
        <p><strong>Dependant Name:</strong> {details.dependantName}</p>
        <p><strong>Relation to Dependant:</strong> {details.relationToDependant}</p>
    </div>
);

const SalaryDetails = ({ details }) => (
    <div>
        <h3>Salary Details</h3>
        <p><strong>Base Salary:</strong> {details.baseSalary}</p>
        <p><strong>Bonus Allotted:</strong> {details.bonusAllotted}</p>
        <p><strong>Stocks Offered:</strong> {details.stocksOffered}</p>
        <p><strong>Total Compensation:</strong> {details.totalComp}</p>
    </div>
);



export default EmployeeDetails;
