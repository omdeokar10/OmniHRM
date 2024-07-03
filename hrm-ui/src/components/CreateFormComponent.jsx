import React, { useEffect } from 'react'
import { useState } from 'react'
import { Button, Form } from 'react-bootstrap';
import { Toaster, toast } from 'sonner';
import { submitForm } from '../service/FormService';

function CreateFormComponent() {

    const [jsonInput, setJsonInput] = useState('');
    const [sampleJsonInput, setSampleJsonInput] = useState('');
    const [formConfig, setFormConfig] = useState([]);
    const [formData, setFormData] = useState({});
    const [showModal, setShowModal] = useState(false);
    const [formName, setFormName] = useState('')

    useEffect(() => {
        defineSampleInput();
        return () => {
        };
    }, [])


    const handleJsonChange = (e) => {
        setJsonInput(e.target.value);
    };

    const handleJsonSubmit = (e) => {
        e.preventDefault();
        try {
            const parsedConfig = JSON.parse(jsonInput);
            setFormConfig(parsedConfig);
            setFormData(parsedConfig.reduce((acc, field) => {
                acc[field.name] = '';
                return acc;
            }, {}));
            console.log(formData);
        } catch (error) {
            console.log(error);
        }
    };

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setFormData({
            ...formData,
            [name]: value,
        });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        
        submitForm(formData);
    };

    const handleShowJson = () => {
        setShowModal(true);
    };

    const handleCloseModal = () => {
        setShowModal(false);
    };

    const defineSampleInput = () => {
        setSampleJsonInput(JSON.stringify([
            { "name": "fullName", "label": "Full Name", "type": "text" },
            { "name": "dateOfBirth", "label": "Date of Birth", "type": "date" },
            { "name": "department", "label": "Department", "type": "select", "options": [{ "value": "", "label": "" }, { "value": "HR", "label": "HR" }, { "value": "Eng", "label": "Eng" }] }
        ]));
        return;
    }

    const addFormNameToFormData = (e) => {
        const { name, value } = e.target;
        setFormName(value);
        setFormData({
            ...formData,
            [name]: value,
        });
    }

    return (

        <div className="container mt-5">

            <form onSubmit={handleJsonSubmit} className="mb-4">
                <div className="form-group mb-2">
                    <label htmlFor="jsonInput">Define Form Configuration (JSON):</label>
                    <textarea
                        id="jsonInput"
                        className="form-control m-1"
                        rows="10"
                        onChange={handleJsonChange}
                        placeholder="Please check sample configuration below"
                    required />
                </div>
                <button type="submit" className="btn btn-primary">Generate Form</button>
                <button type="button" className="btn btn-secondary m-4" onClick={handleShowJson}>
                    Show Sample JSON
                </button>

            </form>

            {formConfig.length > 0 && (

                <form onSubmit={handleSubmit}>
                    <label>Please enter a unique form name</label>
                    <input
                        type="text"
                        className="form-control mb-2 w-25" required
                        name="formName"
                        value={formName}
                        onChange={addFormNameToFormData}
                    />
                    {formConfig.map((field) => (
                        <div className="form-group mb-3" key={field.name}>
                            <label>{field.label}</label>
                            {field.type === 'text' && (
                                <input
                                    type="text"
                                    className="form-control mb-2 w-25"
                                    name={field.name}
                                    value={formData[field.name]}
                                    datatype="text"
                                    onChange={handleInputChange}
                                    required />
                            )}
                            {field.type === 'date' && (
                                <input
                                    type="date"
                                    className="form-control mb-2 w-25"
                                    name={field.name}
                                    value={formData[field.name]}
                                    onChange={handleInputChange}
                                    datatype="date"
                                    required />
                            )}
                            {field.type === 'select' && (
                                <select
                                    className="form-control mb-2 w-25"
                                    name={field.name}
                                    value={formData[field.name]}
                                    datatype="text"
                                    onChange={handleInputChange}
                                >
                                    {field.options.map((option) => (
                                        <option key={option.value} value={option.value}>
                                            {option.label}
                                        </option>
                                    ))}
                                </select>
                            )}
                        </div>
                    ))}

                    <button type="submit" className="btn btn-success mb-4">Submit Form</button>
                </form>
            )}

            {/* Modal */}
            {showModal && (
                <div className="modal show d-block" tabIndex="-1">
                    <div className="modal-dialog">
                        <div className="modal-content">
                            <div className="modal-header">
                                <h5 className="modal-title">JSON Configuration</h5>

                            </div>
                            <div className="modal-body" >
                                <pre>{sampleJsonInput}</pre>
                            </div>
                            <div className="modal-footer">
                                <button type="button" className="btn btn-secondary" onClick={handleCloseModal}>Close</button>
                            </div>
                        </div>
                    </div>
                </div>
            )}

        </div>
    );
}

export default CreateFormComponent