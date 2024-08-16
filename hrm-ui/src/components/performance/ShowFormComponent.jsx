import React from 'react'
import { getFormData, submitFormData, updateFormData } from '../../service/goal/FormService';
import { useState, useEffect } from 'react'
import { Container, Form, Button, Row, Col } from 'react-bootstrap';
import { useParams } from 'react-router-dom';
import { useNavigate } from 'react-router-dom'

function ShowFormComponent() {

    const [formData, setFormData] = useState([]);
    const [formTemplate, setFormTemplate] = useState(null);
    const { id } = useParams()
    const navigate = useNavigate()

    useEffect(() => {
        getFormData(id)
            .then(response => {
                console.log(response.data);
                setFormTemplate(response.data)
            });
    }, [id]);

    if (!formTemplate) {
        return <div>Loading...</div>;
    }

    const handleSubmit = (e) => {
        e.preventDefault();
        console.log(formData);
        updateFormData(id, formData, formTemplate.formName);
        navigate('/performance/listforms')
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        console.log(name, value);
        setFormData({
            ...formData,
            [name]: value,
        });
    };

    return (
        <div className="container mt-5">

            <form name={formTemplate.formName} className="mb-4" onSubmit={handleSubmit}>
                {formTemplate.fields.map((field, index) => (
                    <div key={index} className="form-group mb-2">
                        <label htmlFor={field.name}>{field.label}</label>
                        {field.type === 'select' || field.type === 'checkbox' ? (
                            <select className="form-control mb-2 w-25"
                                name={field.name} required={true}>
                                {field.options.map((option, idx) => (
                                    <option key={idx} value={option}>
                                        {option.label}
                                    </option>
                                ))}
                            </select>
                        ) : (
                            <input
                                className="form-control mb-2 w-25"
                                type={field.type}
                                name={field.name}
                                placeholder={field.placeholder}
                                required={true}
                                onChange={handleChange}
                            />
                        )}
                    </div>
                ))}
            </form>
            <button type="submit" onClick={handleSubmit} className="btn btn-success mb-4">Submit Form</button>
        </div>
    );
};

export default ShowFormComponent