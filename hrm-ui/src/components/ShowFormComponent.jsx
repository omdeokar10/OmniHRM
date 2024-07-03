import React from 'react'
import { getFormData } from '../service/FormService';
import { useState, useEffect } from 'react'
import { Container, Form, Button, Row, Col } from 'react-bootstrap';

function ShowFormComponent() {

    const [formData, setFormData] = useState([])

    useEffect(() => {
        console.log('in use effect: setup code');
        fetchFormData();
        return () => {
            console.log('in use effect, cleanup code');
        };
    }, [])

    function fetchFormData() {
        getFormData().then((response) => {
            setFormData(response.data[0]["keyValuePairs"]);//we can pass id of form to be filled.
        }).catch(error => {
            console.log(error);
        })
    }

    const handleSubmit = (e) => {
        e.preventDefault();
        console.log('Form Data Submitted:', formData);
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
                        .filter(([key]) => key != 'formName')
                        .map(([key, value]) => (

                            <Form.Group as={Row} controlId={key} key={key} className="mb-3">
                                <Form.Label column sm="2">
                                    {key}
                                </Form.Label>
                                <Col sm="10">
                                    <Form.Control
                                        type={key === 'dateOfBirth' ? 'date' : 'text'}
                                        name={key}
                                        value={formData[key]}
                                        onChange={handleChange}
                                    />
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