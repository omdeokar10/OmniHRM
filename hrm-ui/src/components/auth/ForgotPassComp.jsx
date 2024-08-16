import React from 'react'
import { forgotPasswordApiCall } from '../../service/auth/AuthService';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
const ForgotPassComp = () => {
    const [email, setEmail] = useState('');
    const [message, setMessage] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            forgotPasswordApiCall(email).then((res) => {
                setMessage('If an account with that email exists, a password reset link has been sent.');
                setError('');
                navigate('/');
            });
        } catch (error) {
            setError('An error occurred while trying to send the password reset email.');
            setMessage('');
        }
    };

    return (
        <div className="container">
            <h2>Forgot Password</h2>
            <form onSubmit={handleSubmit}>
                <div className="col-md-6 offset-md-3">
                    <label htmlFor="email">Email Address:</label>
                    <input
                        type="email"
                        id="email"
                        className="form-control"
                        placeholder="Enter your email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                    />
                </div>
                <button type="submit" className="btn btn-primary">Send Reset Link</button>
            </form>
            {message && <p className="message">{message}</p>}
            {error && <p className="error">{error}</p>}
        </div>
    );
};

export default ForgotPassComp