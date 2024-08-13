import React from 'react'
import { useState } from 'react';
import { loginAPICall, saveLoggedInUser, storeToken } from '../../service/auth/AuthService';
import { useNavigate, useParams } from 'react-router-dom';
import { companyLogin } from '../../service/company/CompanyService';
import { storeInfo } from '../../service/auth/AuthService';

function LoginComponent() {

    const [userName, setUsername] = useState('')
    const [password, setPassword] = useState('')
    const { companyName } = useParams()

    const [title, setTitle] = useState('')

    const navigator = useNavigate();

    function pageTitle() {
        if (companyName) {
            return <h3 className='text-center'>Company Admin Login</h3>
        } else {
            return <h3 className='text-center'>Employee Login</h3>
        }
    }

    function loginLink(){
        if (companyName) {
            return  <a className='child-link' href='/'>Employee Login</a>
        } else {
            return <a className='child-link' href='/admin-login'>Company Admin Login</a>
        }
    }

    async function handleLoginForm(e) {

        e.preventDefault();

        if (companyName) {

            const loginDto = { userName, password };
            await loginAPICall(loginDto).then((response) => {
                console.log(response.data);
                const token = 'Bearer ' + response.data.accessToken;
                const refreshToken = response.data.refreshToken;
                const username = response.data.username;
                const roles = response.data.roles;
                storeToken(token, refreshToken);
                storeInfo("companyName", response.data.companyName);
                saveLoggedInUser(username, roles);
                navigator("/hr/summary");

                window.location.reload(false);
            }).catch(error => {
                console.error(error);
            })

        }
        else {

            const loginDto = { userName, password };
            storeToken(null);
            setTitle('');

            await loginAPICall(loginDto).then((response) => {
                console.log(response.data);

                const token = 'Bearer ' + response.data.accessToken;
                const refreshToken = response.data.refreshToken;
                const username = response.data.username;
                const roles = response.data.roles;
                storeInfo("companyName", response.data.companyName);
                storeToken(token, refreshToken);
                saveLoggedInUser(username, roles);
                navigator("/performance/summary")

                window.location.reload(false);
            }).catch(error => {
                console.error(error);
            })
        }

    }

    return (
        <div className='container'>
            <br /> <br />
            <div className='row'>
                <div className='col-md-6 offset-md-3'>
                    <div className='card'>
                        <div className='card-header'>
                            {pageTitle()}
                        </div>

                        <div className='card-body'>
                            <form>

                                <div className='row mb-3'>
                                    <label className='col-md-3 control-label'> Username</label>
                                    <div className='col-md-9'>
                                        <input
                                            type='text'
                                            name='username'
                                            className='form-control'
                                            placeholder='Enter username'
                                            value={userName}
                                            onChange={(e) => setUsername(e.target.value)}
                                        >
                                        </input>
                                    </div>
                                </div>

                                <div className='row mb-3'>
                                    <label className='col-md-3 control-label'> Password </label>
                                    <div className='col-md-9'>
                                        <input
                                            type='password'
                                            name='password'
                                            className='form-control'
                                            placeholder='Enter password'
                                            value={password}
                                            onChange={(e) => setPassword(e.target.value)}
                                        >
                                        </input>
                                    </div>
                                </div>

                                <div className='form-group mb-3'>
                                    <button className='btn btn-primary' onClick={(e) => handleLoginForm(e)}>Submit</button>
                                </div>
                            </form>
                            <div className='parent-links'>
                                <a className='child-link' href='/register-company'>New Company ? Register</a>
                                {loginLink()}
                            </div>
                        </div>


                    </div>
                </div>
            </div>


        </div>
    )
}

export default LoginComponent