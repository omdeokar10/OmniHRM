import React from 'react'
import { useState, useEffect } from 'react'
import { Container, Form, Button, Row, Col } from 'react-bootstrap';
import { deleteFormById, getFormDataByCompanyName } from '../../service/goal/FormService';
import { useNavigate } from 'react-router-dom'
import { toastSuccess, toastError } from '../../service/ToastService';

function ListAllPendingFormComponent() {

    const [formData, setFormData] = useState([])
    const [dataChanged, setDataChanged] = useState(true);
    const navigate = useNavigate()

    useEffect(() => {
        retrieveFormsByCompany();
    }, [dataChanged]);

    function retrieveFormsByCompany() {
        getFormDataByCompanyName().then((response) => {
            setFormData(response.data);
        });
    }

    function deleteForm(id) {
        deleteFormById(id).then((response) => {
            retrieveFormsByCompany();
        })
        .catch((error) => {
            console.error("Error deleting form:", error.response.data.message);
            toastError(error.response.data.message);
        });
        setDataChanged(!dataChanged);
    }

    function updateForm(id) {
        navigate(`/performance/showforms/${id}`);
    }

    return (
        <div className='container'>
            <h2 className='text-center'>Pending Form Updates</h2>
            <div>
                <table className="minima-table">
                    {formData.length > 0 && <thead>
                        <tr>
                            <th>Form name</th>
                            <th>Description(Optional)</th>
                            <th>Actions</th>
                        </tr>
                    </thead>}
                    <tbody>
                        {formData.map(form => (
                            <tr key={form.id}>
                                <td>{form.formName}</td>
                                <td>{form.description}</td>
                                <td>
                                    <button className="btn minima-btn mb-2" onClick={() => updateForm(form.id)}>Update</button>
                                    <button className="btn minima-btn mb-2" onClick={() => deleteForm(form.id)}>Delete</button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
                {formData.length == 0 && <div className="minima">
                    <p>All Caught up.</p>
                </div>
                }
            </div>

        </div>
    )
}

export default ListAllPendingFormComponent