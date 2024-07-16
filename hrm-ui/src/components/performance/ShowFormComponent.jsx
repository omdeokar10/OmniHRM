import React from 'react'
import { getFormData, submitFormData } from '../../service/goal/FormService';
import { useState, useEffect } from 'react'
import { Container, Form, Button, Row, Col } from 'react-bootstrap';
import { useParams } from 'react-router-dom';
import { useNavigate } from 'react-router-dom'

function ShowFormComponent() {

    const [formData, setFormData] = useState([])
    const { id } = useParams()
    const navigate = useNavigate()

    useEffect(() => {
        console.log('in use effect: setup code');
        fetchFormData(id);
        return () => {
            console.log('in use effect, cleanup code');
        };
    }, [])

    function fetchFormData(id) {
        getFormData(id).then((response) => {
            console.log('show form component' + response.data);
            setFormData(response.data["fields"]);
        }).catch(error => {
            console.log(error);
        })
    }

    const handleSubmit = (e) => {
        e.preventDefault();
        submitFormData(formData);
        navigate('/performance/listforms')
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({
            ...formData,
            [name]: value,
        });
    };

    return (
        <Container>
            <Form onSubmit={handleSubmit}>
                <fieldset>
                    <legend>{formData['formName']}</legend>
                    {Object.entries(formData)
                        .filter(([key]) => key !== 'formName')
                        .map(([key, value]) => (
                            <Form.Group as={Row} controlId={key} key={key} className="mb-3">
                                <Form.Label column sm="2">
                                    {key}
                                </Form.Label>
                                <Col sm="10">
                                    {Array.isArray(value) ? (
                                        <div>
                                            {value.map(option => (
                                                <Form.Check
                                                    type="checkbox"
                                                    label={option.label}
                                                    name={key}
                                                    value={option.value}
                                                    checked={formData[key].includes(option.value)}
                                                    onChange={handleChange}
                                                    key={option.value}
                                                />
                                            ))}
                                        </div>
                                    ) : typeof value === 'object' && value.options ? (
                                        <Form.Control
                                            as="select"
                                            name={key}
                                            value={formData[key]}
                                            onChange={handleChange}
                                        >
                                            {value.options.map(option => (
                                                <option key={option.value} value={option.value}>
                                                    {option.label}
                                                </option>
                                            ))}
                                        </Form.Control>
                                    ) : (
                                        <Form.Control
                                            type={key === 'dateOfBirth' ? 'date' : 'text'}
                                            name={key}
                                            value={formData[key]}
                                            onChange={handleChange}
                                        />
                                    )}
                                </Col>
                            </Form.Group>
                        ))}
                </fieldset>
                <Button variant="primary" type="submit">
                    Submit
                </Button>
            </Form>
        </Container>


    )
}

export default ShowFormComponent