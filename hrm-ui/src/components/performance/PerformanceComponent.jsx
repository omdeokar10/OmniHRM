import React from 'react'
import { useState } from 'react'
import { Button, Form } from 'react-bootstrap';
import { addPerformanceGoal } from '../../service/goal/PerformanceService';
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
        <div>
            
        </div>
    );
};

export default PerformanceComponent