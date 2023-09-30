import { Link } from "react-router-dom";
import Ticket from "./../images/omniHRM.jpeg";

const Home = () => {
  return (
    <>
      <div className="text-center">
        <h2>Welcome to your Office !!</h2>
        <hr />
        <Link to="/movies">
          <img src={Ticket} alt="movie tickets"></img>
        </Link>
      </div>
    </>
  );
};

export default Home;
