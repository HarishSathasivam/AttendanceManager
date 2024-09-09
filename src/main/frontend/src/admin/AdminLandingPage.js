import React, { useState } from "react";
import AllUserTable from './AllUserTable';
import AddUserForm from './AddUserForm';
import user_icon from '../assets/user_icon.png';
import user_report from '../assets/user_report.png';
import '../css/main.css'; 

const AdminLandingPage = ({ pageControl, fetchAllUsersActions, allUsers }) => {
    const [currentPage, setCurrentPage] = useState('landing');

    const handleLoadPage = (e) => {
        const targetPage = e.target.getAttribute('name');
        setCurrentPage(targetPage);
    }

    const handleGoBack = () => {
        setCurrentPage('landing');
    }

    let handleLogOut = () => {
        pageControl('logIn');
    };

    return (
        <div className="page-container">

            {currentPage === 'landing' && (
                <div>
                    <button onClick={handleLogOut} className='logout ' >Logout</button>

                    <div className="modules">

                        <div className="UserReportIcon" onClick={handleLoadPage} name="loadUserReport">
                            <img
                                name="loadUserReport"
                                src={user_report} 
                                alt="User Reports"
                                style={{ cursor: 'pointer', width: '100px', height: 'auto' }}
                            />
                            <h3>USER REPORTS</h3>
                        </div>
                        <div className="addUserIcon" onClick={handleLoadPage} name="addUser">
                            <img
                                name="addUser"
                                src={user_icon}
                                alt="Add User"
                                style={{ cursor: 'pointer', width: '100px', height: 'auto' }}
                            />
                            <h3>ADD USER</h3>
                        </div>
                    </div>
                </div>
            )}

            {currentPage === 'loadUserReport' && (
                <div className="centered-content">
                    <button className = "backBtn" onClick={handleGoBack} style={{ marginBottom: '20px', cursor: 'pointer' }}>Back</button>
                    <AllUserTable allUsers={allUsers} />
                </div>
            )}

            {currentPage === 'addUser' && (
                <div className="centered-content">
                    <button  className = "backBtn" onClick={handleGoBack} style={{ marginBottom: '20px', cursor: 'pointer' }}>Back</button>
                    <AddUserForm allUsers={allUsers} fetchAllUsersActions = {fetchAllUsersActions} />
                </div>
            )}


        </div>
    );
}

export default AdminLandingPage;
