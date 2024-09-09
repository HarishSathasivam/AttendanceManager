import React, { useEffect, useState } from 'react';
import { BrowserRouter } from 'react-router-dom';
import axios from 'axios';
import LogIn from './admin/Login';
import LandingPage from './admin/LandingPage';
import './css/main.css'
import './css/App.css'
import AdminLandingPage from './admin/AdminLandingPage'

function App() {
  const [currentUser, setCurrentUser] = useState("");
  const [loginPage, setLogInPage] = useState(true);
  const [landingPage, setLandingPage] = useState(false);
  const [administratorPage, setAdministratorPage] = useState(false);
  const [allUsers, setAllUsers] = useState([]);
  

  useEffect(() => {
   fetchAllUsersActions();
  }, []);


  const loadSetLoggedInUser = (user) => {
    if (user && user !== "") {
      setCurrentUser(user);
    }
  };

 

  const pageControl = (page) => {
    if (page === "logIn") {
      setLandingPage(false);
      setAdministratorPage(false);
      setLogInPage(true);
    } else if (page === "userPage" ) {
      
      setLandingPage(true);
      setLogInPage(false);
      setAdministratorPage(false);
    
    } else if (page === "administratorPage" ) {
      setLogInPage(false);
      setLandingPage(false);
      setAdministratorPage(true);
    }
   
  };

  const fetchAllUsersActions = () => {
    const url = "http://localhost:8080/AttendanceManager/allUsers";
    axios.get(url)
      .then((response) => {
        if (response.status === 200) {
          setAllUsers(response.data);
        }
      })
      .catch((error) => {
        console.log("error = ", error);
      });
  };

  return (
    <BrowserRouter>
      <div className="App">
        {loginPage && <LogIn
          loadSetLoggedInUser={loadSetLoggedInUser}
          pageControl={pageControl}
          fetchAllUsersActions={fetchAllUsersActions}
          allUsers={allUsers}
        />}
        {landingPage && <LandingPage pageControl={pageControl} currentUser = {currentUser} />}
        {administratorPage&&<AdminLandingPage pageControl={pageControl} fetchAllUsersActions={fetchAllUsersActions} allUsers={allUsers} />}
      </div>
    </BrowserRouter>
  );
}

export default App;
