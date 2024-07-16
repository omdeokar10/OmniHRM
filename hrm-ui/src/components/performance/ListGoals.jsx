import React, { useEffect, useState } from 'react'
import '../../service/goal/GoalService';
import { getGoal, updateGoal, getAllGoals, deleteGoalById, getAllGoalsByUser } from '../../service/goal/GoalService';
import '../performance/listgoals.css';
import { useNavigate, useParams } from 'react-router-dom'

function ListGoals() {

    const [goals, setGoals] = useState([])
    const navigate = useNavigate()
    
    useEffect(() => {
        listGoals();
    }, [])

    function addGoal() {
        navigate('/performance/creategoal')
    }

    function listGoals() {
        getAllGoalsByUser().then((response) => {
            setGoals(response.data)
        })
    }

    function updateGoal(id) {
        navigate(`/performance/updategoal/${id}`)
    }

    function deleteGoal(id) {
        deleteGoalById(id).then((response)=>{
            listGoals();
            navigate('/performance/listgoal');
        }).catch(error=>{
            console.log(error);
        })
    }

    function submit(id) {

    }

    return (
        <div className='container'>
            <h2 className='text-center'>List of Goals</h2>
            <button className='btn btn-primary mb-2' onClick={addGoal}>Add Goal</button>
            <div>
                <table className="minima-table">
                    <thead>
                        <tr>
                            <th>Title</th>
                            <th>Description</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        {goals.map(goal => (
                            <tr key={goal.id}>
                                <td>{goal.title}</td>
                                <td>{goal.description}</td>
                                <td>
                                    <button className="btn minima-btn mb-2" onClick={() => updateGoal(goal.id)}>Update</button>
                                    <button className="btn minima-btn mb-2" onClick={() => deleteGoal(goal.id)}>Delete</button>
                                    <button className="btn minima-btn mb-2" onClick={() => submit(goal.id)}>Submit</button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>

        </div>
    )
}

export default ListGoals