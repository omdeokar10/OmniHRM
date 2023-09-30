import { useEffect, useState } from "react";
import { Link } from "react-router-dom";

const PersonalInfo = () => {
    const [PersonalInfo, setPersonalInfo] = useState([]);

    useEffect( () => {
        const headers = new Headers();
        headers.append("Content-Type", "application/json");

        const requestOptions = {
            method: "GET",
            headers: headers,
        }

        fetch(`http://localhost:8080/PersonalInfo`, requestOptions)
            .then((response) => response.json())
            .then((data) => {
                setPersonalInfo(data);
            })
            .catch(err => {
                console.log(err);
            })

    }, []);

    return(
        <div>
            <h2>PersonalInfo</h2>
            <hr />
            <table className="table table-striped table-hover">
                <thead>
                    <tr>
                        <th>Movie</th>
                        <th>Release Date</th>
                        <th>Rating</th>
                    </tr>
                </thead>
                <tbody>
                    {PersonalInfo.map((m) => (
                        <tr key={m.id}>
                            <td>
                                <Link to={`/personalinfo/${m.id}`}>
                                    {m.title}
                                </Link>
                            </td>
                            <td>{m.release_date}</td>
                            <td>{m.mpaa_rating}</td>
                        </tr>    
                    ))}
                </tbody>
            </table>
        </div>
    )
}

export default PersonalInfo;