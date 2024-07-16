import React from 'react'
import { useState, useEffect } from 'react'
import { Container, Form, Button, Row, Col } from 'react-bootstrap';
import { getPendingFormData } from '../../service/goal/FormService';
import { useNavigate } from 'react-router-dom'

function ListAllPendingFormComponent() {

    const [formData, setFormData] = useState([])
    const navigate = useNavigate()

    useEffect(() => {
        console.log('in use effect: setup code');
        fetchFormData();
        return () => {
            console.log('in use effect, cleanup code');
        };
    }, [])

    function fetchFormData() {
        getPendingFormData().then((response) => {
            console.log('pending form data' + response.data);
            if(response.data.length > 0){
                setFormData(response.data);
            }
        });
    }

    function updateForm(id){
        navigate(`/performance/showforms/${id}`)
    }

    return (
        <div className='container'>
            <h2 className='text-center'>Pending Form Updates</h2>
            <div>
                <table className="minima-table">
                    {formData.length > 0 && <thead>
                        <tr>
                            <th>Form name</th>
                            <th>Description (Optional)</th>
                            <th>Actions</th>
                        </tr>
                    </thead> }
                    <tbody>
                        {formData.map(form => (
                            <tr key={form.id}>
                                <td>{form.formName}</td>
                                <td>{form.description}</td>
                                <td>
                                    <button className="btn minima-btn mb-2" onClick={() => updateForm(form.id)}>Update</button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
                {formData.length ==0 && <div className="minima">
                    <p>All Caught up.</p>
                    </div>
                    }
            </div>

        </div>
    )
}

export default ListAllPendingFormComponent