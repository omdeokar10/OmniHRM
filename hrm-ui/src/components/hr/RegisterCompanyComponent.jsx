import React from 'react'
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { createCompanyApi } from '../../service/hr/HrService';
import { toastError, toastSuccess } from '../../service/ToastService';

function RegisterCompanyComponent() {

    const [companyName, setCompanyName] = useState('')
    const [companyDomain, setCompanyDomain] = useState('')
    const [companyEmail, setCompanyEmail] = useState('')
    const [userName, setUserName] = useState('')

    const navigator = useNavigate();


    async function handleSubmit(e) {

        e.preventDefault();

        const userCompanyDto = {
            companyName, companyDomain, companyEmail,
            userName
        };

        createCompanyApi(userCompanyDto).then((response) => {
            console.log('Company created successfully' + response);
            toastSuccess('Company created successfully');
            navigator(`/${companyName}`);
        }).catch(error => {
            console.log('error creating company');
            toastError(error.message);
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
                        <label className='col-md-3 control-label'> Admin user name </label>
                        <div className='col-md-9'>
                            <input type='text' name='lastname' className='form-control' placeholder='Admin user name. Please append companyname to maintain uniqueness.'
                                value={userName} onChange={(e) => setUserName(e.target.value)} required>
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