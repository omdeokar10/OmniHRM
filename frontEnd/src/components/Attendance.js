import React, { useState } from "react";
import { useParams } from "react-router-dom";

const Attendance = () => {
  const { user_id } = useParams();
  const [isSignedIn, setIsSignedIn] = useState(false);

  const handleSignInOut = async () => {
    try {
      // Make a POST request to the backend based on the current state
      const route = isSignedIn ? `/admin/attendance/signout/${user_id}` : `/admin/attendance/signin/${user_id}`;
      const response = await fetch(route, {
        method: "POST",
        // You can include headers and body data if required
      });

      if (response.ok) {
        setIsSignedIn(!isSignedIn); // Toggle the state
      } else {
        // Handle error, maybe display an error message
      }
    } catch (error) {
      // Handle network error
      console.error("Network error:", error);
    }
  };

  return (
    <div className="text-center">
      <h2>Attendance Page</h2>
      <p>User ID: {user_id}</p>
      <button
        onClick={handleSignInOut}
        style={{ backgroundColor: isSignedIn ? "red" : "green" }}
      >
        {isSignedIn ? "Check Out" : "Check In"}
      </button>
    </div>
  );
};

export default Attendance;
