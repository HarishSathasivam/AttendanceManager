import React, { useEffect, useState } from 'react';
import axios from 'axios';
import '../css/main.css'

const LogIn = (props) => {
    const { loadSetLoggedInUser, pageControl, trackUserStatus, fetchAllUsersActions, allUsers } = props;
    const [userName, setUserName] = useState("");
    const [passWord, setPassword] = useState("");
    const [error, setError] = useState("");
    const [isSubmitting, setIsSubmitting] = useState(false);

    useEffect(() => {
        fetchAllUsersActions();
    }, []);

    const handleInput = (e) => {
        const { name, value } = e.target;
        if (name === "userName") {
            setUserName(value);
        } else if (name === "password") {
            setPassword(value);
        }
    }

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (userName !== "" && passWord !== "") {
            setIsSubmitting(true);
            if (allUsers.length === 0 && (userName.toLowerCase() === "administrator")) {
                pageControl("administratorPage");
                await addAdmin();
            } else {
                const userFound = allUsers.find(user => user.userName === userName && user.password === passWord);
                if (userFound) {
                    setError("");
                    loadSetLoggedInUser(userFound);

                    if (userFound.userRole === "admin") {
                        pageControl("administratorPage");
                    }else{
                        pageControl("userPage");
                    }
                } else {
                    setError("Incorrect username or password");
                }
            }
            setIsSubmitting(false);
        } else {
            setError("Username and password should not be empty");
        }
    }

    const addAdmin = async () => {
        const url = `http://localhost:8080/AttendanceManager/addUser?userName=${userName}&passWord=${passWord}&role=admin&frequencyInterval=1`;
        try {
            const response = await axios.get(url);
            if (response.status === 200) {
                loadSetLoggedInUser(response.data);
                setError("");
            }
        } catch (error) {
            setError(`UserId or password is incorrect ${error}`);
        }
    }

    return (
        <div className="logInformContainer">
            <form className='logInform'   onSubmit={handleSubmit}>
               
                <div className=" userInputs">
                    <input
                        name="userName"
                        type="text"
                        className="UserName"
                        placeholder='USERNAME'
                        value={userName}
                        onChange={handleInput}
                        style={{ fontSize: '1.2rem' }}
                    />
               
                    <input
                        name="password"
                        type="password"
                        className="password"
                        placeholder='PASSWORD'
                        value={passWord}
                        onChange={handleInput}
                        style={{ fontSize: '1.2rem' }}
                    />
                </div>
                <span className='errorTxt text-danger'>{error}</span>
                <button type="submit" className='logInBtn' disabled={isSubmitting}>
                    {isSubmitting ? "LOGGING IN..." : "LOG IN"}
                </button>

                
            </form>
        </div>
    );
}

export default LogIn;
