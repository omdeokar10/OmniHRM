import React from 'react'
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { createCompanyApi } from '../../service/hr/HrService';

function RegisterCompanyComponent() {

    const [companyName, setCompanyName] = useState('')
    const [companyDomain, setCompanyDomain] = useState('')
    const [companyEmail, setCompanyEmail] = useState('')
    const [firstName, setFirstName] = useState('')
    const [lastName, setLastName] = useState('')
    const [userName, setUserName] = useState('')

    const navigator = useNavigate();


    async function handleSubmit(e) {

        e.preventDefault();

        const userCompanyDto = { companyName, companyDomain, companyEmail, 
            firstName, lastName, userName};

        createCompanyApi(userCompanyDto).then((response) => {
            console.log('Company created successfully' + response);
            navigator(`/${companyName}`);
        }).catch(error => {
            console.error(error);
        })
    }

    return (
        <div className='container'>

            <div className='card-body'>
                <form>

                    <div className='card-header'>
                        <h3 className='text-center'> Create Company </h3>
                    </div>

                    <div className='row mb-3'>
                        <label className='col-md-3 control-label'> Company Name </label>
                        <div className='col-md-9'>
                            <input
                                type='text'
                                name='companyname'
                                className='form-control'
                                placeholder='Enter company name'
                                value={companyName}
                                onChange={(e) => setCompanyName(e.target.value)}
                            >
                            </input>
                        </div>
                    </div>

                    <div className='row mb-3'>
                        <label className='col-md-3 control-label'> Company Domain </label>
                        <div className='col-md-9'>
                            <input
                                type='text'
                                name='companydomain'
                                className='form-control'
                                placeholder='Enter Company Domain (Tech, Business, Service, etc.)'
                                value={companyDomain}
                                onChange={(e) => setCompanyDomain(e.target.value)}
                            >
                            </input>
                        </div>
                    </div>

                    <div className='row mb-3'>
                        <label className='col-md-3 control-label'> Company Email </label>
                        <div className='col-md-9'>
                            <input type='text' name='companyemail' className='form-control' placeholder='This will be used as the company admin email.'
                                value={companyEmail} onChange={(e) => setCompanyEmail(e.target.value)} required>
                            </input>
                        </div>
                    </div>

                    <div className='row mb-3'>
                        <label className='col-md-3 control-label'> Admin first name </label>
                        <div className='col-md-9'>
                            <input type='text' name='firstname' className='form-control' placeholder='Admin first name.'
                                value={firstName} onChange={(e) => setFirstName(e.target.value)} required>
                            </input>
                        </div>
                    </div>

                    <div className='row mb-3'>
                        <label className='col-md-3 control-label'> Admin last name </label>
                        <div className='col-md-9'>
                            <input type='text' name='lastname' className='form-control' placeholder='Admin last name.'
                                value={lastName} onChange={(e) => setLastName(e.target.value)} required>
                            </input>
                        </div>
                    </div>

                    <div className='row mb-3'>
                        <label className='col-md-3 control-label'> Admin user name </label>
                        <div className='col-md-9'>
                            <input type='text' name='lastname' className='form-control' placeholder='Admin last name.'
                                value={firstName+'.'+lastName+'.'+companyName} onChange={(e) => setUserName(e.target.value)} required>
                            </input>
                        </div>
                    </div>

                    <div className='form-group mb-3'>
                        <button className='btn btn-primary' onClick={(e) => handleSubmit(e)}>Submit</button>
                    </div>

                </form>

                * Password would be sent on the company email.

            </div>
        </div>
    )
}

export default RegisterCompanyComponent