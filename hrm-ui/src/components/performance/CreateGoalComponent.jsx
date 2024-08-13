import React, { useState, useEffect } from 'react';
import '../performance/goalstyle.css';
import '../../service/goal/GoalService';
import { createGoal, updateGoal, getGoal } from '../../service/goal/GoalService';
import { useNavigate, useParams } from 'react-router-dom'
import { getCompanyName, getLoggedInUser } from '../../service/auth/AuthService';

const CreateGoalComponent = () => {
    const [goal, setGoal] = useState({
        title: '',
        managerName: '',
        employeeName: '',
        companyName: '',
        description: '',
        category: '',
        startDate: '',
        endDate: '',
        kpis: [],
        milestones: '',
        feedbackNotes: '',
        selfAssessment: '',
        completed: false
    });
    const navigate = useNavigate()
    const { id } = useParams()

    const handleChange = (e) => {
        const { name, value } = e.target;
        setGoal((prevGoal) => ({
            ...prevGoal,
            [name]: value,
            employeeName: getLoggedInUser(),
            companyName: getCompanyName()
        }));
    };

    const handleSubmit = (e) => {

        e.preventDefault();

        if (id) {
            updateGoal(id, goal).then((res) => {
                performNavigateToGoalsPage();
            });
        }
        else {
            createGoal(goal).then((res) => {
                performNavigateToGoalsPage();
            });
        }

    };

    function performNavigateToGoalsPage() {
        navigate('/performance/listgoal');
    }

    function fetchAndUpdateTodo(id) {
        var old_goal = getGoal(id).then((res) => {
            setGoal(res.data);
        });
    }

    useEffect(() => {
        if (id) {
            fetchAndUpdateTodo(id);
        }
    }, [id])

    const handleKpiChange = (index, value) => {
        const newKpis = [...goal.kpis];
        newKpis[index] = value;
        setGoal((prevGoal) => ({
            ...prevGoal,
            kpis: newKpis
        }));
    };

    const removeKpi = (index) => {
        setGoal((prevGoal) => ({
            ...prevGoal,
            kpis: prevGoal.kpis.filter((_, i) => i !== index)
        }));
    }

    const addKpi = () => {
        setGoal((prevGoal) => ({
            ...prevGoal,
            kpis: [...prevGoal.kpis, '']
        }));
    };

    return (
        <div className="container">
            <form onSubmit={handleSubmit}>
                <div className="form-group mt-5">
                    <label htmlFor="title">Goal Title</label>
                    <input type="text" className="form-control" id="title" name="title" value={goal.title} onChange={handleChange} placeholder="Goal Title" required />
                </div>

                <div className="form-group">
                    <label htmlFor="title">Manager Name</label>
                    <input type="text" className="form-control" id="managerName" name="managerName" value={goal.managerName} onChange={handleChange} placeholder="Manager name" required />
                </div>

                <div className="form-group">
                    <label htmlFor="description">Goal Description</label>
                    <textarea className="form-control" id="description" name="description" value={goal.description} onChange={handleChange} placeholder="Goal Description" required />
                </div>

                <div className="form-group">
                    <label htmlFor="category">Category</label>
                    <input type="text" className="form-control" id="category" name="category" value={goal.category} onChange={handleChange} placeholder="Category" />
                </div>

                <div className="form-group">
                    <label htmlFor="startDate">Start Date</label>
                    <input type="date" className="form-control" id="startDate" name="startDate" value={goal.startDate} onChange={handleChange} required />
                </div>

                <div className="form-group">
                    <label htmlFor="endDate">End Date</label>
                    <input type="date" className="form-control" id="endDate" name="endDate" value={goal.endDate} onChange={handleChange} required />
                </div>

                <div className="form-group">
                    <label>Key Performance Indicators</label>
                    {
                        goal.kpis.map((kpi, index) => (
                            <div key={index} className="d-flex mb-2">
                                <input
                                    type="text"
                                    className="form-control"
                                    value={kpi}
                                    onChange={(e) => handleKpiChange(index, e.target.value)}
                                    placeholder="Key Performance Indicator"
                                    required
                                />
                                <button type="button" className="btn btn-success ml-2" onClick={() => removeKpi(index)}>Complete</button>
                            </div>
                        ))
                    }
                    <button type="button" className="btn btn-secondary mt-2" onClick={addKpi}>Add KPI</button>
                </div>

                <div className="form-group">
                    <label htmlFor="milestones">Milestones</label>
                    <textarea className="form-control" id="milestones" name="milestones" value={goal.milestones} onChange={handleChange} placeholder="Milestones" required />
                </div>

                <div className="form-group">
                    <label htmlFor="feedbackNotes">Feedback Notes</label>
                    <textarea className="form-control" id="feedbackNotes" name="feedbackNotes" value={goal.feedbackNotes} onChange={handleChange} placeholder="Feedback Notes" required />
                </div>

                <div className="form-group mb-3">
                    <label htmlFor="selfAssessment">Self-Assessment</label>
                    <textarea className="form-control" id="selfAssessment" name="selfAssessment" value={goal.selfAssessment} onChange={handleChange} placeholder="Self-Assessment" />
                </div>

                <button type="submit" className="btn btn-primary">Submit</button>
            </form>
        </div>
    );
};

export default CreateGoalComponent;
