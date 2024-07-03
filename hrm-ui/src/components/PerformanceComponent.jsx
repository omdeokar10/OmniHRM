import React from 'react'
import { useState } from 'react'
import { Button, Form } from 'react-bootstrap';
import { addPerformanceGoal } from '../service/PerformanceService';
import {Toaster,toast} from 'sonner';

function PerformanceComponent() {
    const [improvements, setImprovements] = useState([
        { goal: '', empInput: '', mgrFeedback: '' }]);

    const handleChange = (index, field, value) => {
        const newImprovements = [...improvements];
        newImprovements[index][field] = value;
        setImprovements(newImprovements);
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        console.log('Submitted Improvements:', improvements);
        improvements.forEach((improvement) => { addPerformanceGoal(improvement); });
        console.log('persisted');
        toast('Persisted');
    };

    const addImprovement = () => {
        setImprovements([...improvements, { goal: '', empInput: '', mgrFeedback: '' }]);
    };

    return (
        <div className="container mt-5">
            <h2 className="mb-4">Areas of Improvement</h2>
            <Form onSubmit={handleSubmit}>
                {improvements.map((improvement, index) => (
                    <div key={index} className="mb-3">
                        <Form.Group>
                            <Form.Label htmlFor={`goal${index}`}>Area {index + 1}:</Form.Label>
                            <Form.Control
                                type="text"
                                id={`goal${index}`}
                                value={improvement.goal}
                                onChange={(e) => handleChange(index, 'goal', e.target.value)}
                                required
                            />
                        </Form.Group>
                        <Form.Group>
                            <Form.Label htmlFor={`empInput${index}`}>Input:</Form.Label>
                            <Form.Control
                                type="text"
                                id={`empInput${index}`}
                                value={improvement.empInput}
                                onChange={(e) => handleChange(index, 'empInput', e.target.value)}
                                required
                            />
                        </Form.Group>
                        <Form.Group>
                            <Form.Label htmlFor={`mgrFeedback${index}`}>Feedback:</Form.Label>
                            <Form.Control
                                type="text"
                                id={`mgrFeedback${index}`}
                                value={improvement.mgrFeedback}
                                onChange={(e) => handleChange(index, 'mgrFeedback', e.target.value)}
                                required
                            />
                        </Form.Group>
                    </div>
                ))}
                <Button variant="primary" type="submit">Submit</Button>
                <Button variant="secondary" className="ml-2" onClick={addImprovement}>Add More</Button>
            </Form>
        </div>
    );
};

export default PerformanceComponent