import React from 'react'
import { getPendingFormData } from '../../service/goal/FormService';
import { useState, useEffect } from 'react'
import { getGoal, updateGoal, getAllGoals, deleteGoalById, getAllGoalsByUser } from '../../service/goal/GoalService';

function SummaryComponent() {


    const [pendingform, setPendingForm] = useState(0);
    const [pendingGoal, setPendingGoals] = useState(0);
    const [pendingKpi, setPendingKpi] = useState(0);
    useEffect(() => {
        console.log('in use effect: setup code');
        fetchFormData();
        fetchGoalData();
        return () => {
            console.log('in use effect, cleanup code');
        };
    }, [])

    function fetchFormData() {
        getPendingFormData().then((response) => {
            setPendingForm(response.data.length);
        });
    }

    function fetchGoalData() {
        getAllGoalsByUser().then((response) => {
            setPendingGoals(response.data.length);
            var kpis = 0;
            response.data.forEach((goal) => kpis += (goal.kpis.length));
            setPendingKpi(kpis);
        });
    }

    return (
        <div className="container mt-5">
            <h1 className="text-center">Statistics Summary</h1>
            <div className="row">
                <div className="col-md-4 mb-4">
                    <div className="card">
                        <div className="card-body">
                            <h5 className="card-title">Pending Forms</h5>
                            <p className="card-text">{pendingform}</p>
                        </div>
                    </div>
                    <div className="card">
                        <div className="card-body">
                            <h5 className="card-title">Pending Goals</h5>
                            <p className="card-text">{pendingGoal}</p>
                        </div>
                    </div>
                    <div className="card">
                        <div className="card-body">
                            <h5 className="card-title">Pending Kpis</h5>
                            <p className="card-text">{pendingKpi}</p>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    );
}

export default SummaryComponent