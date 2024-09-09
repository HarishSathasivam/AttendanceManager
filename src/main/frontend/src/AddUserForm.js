import React, { useEffect, useRef, useState } from "react";
import axios from 'axios';
import AllUserTable from './AllUserTable';

const AddUserForm = (props) => {
    let { pageControl, allUsers, fetchAllUsersActions } = props;
    const [addUserForm, setAddUserForm] = useState(false);
    const [userName, setUserName] = useState("");
    const [passWord, setPassWord] = useState("");
    const [error, setError] = useState("");
    const [role, setRole] = useState("user");
    const [frequencyInterval , setFrequencyInterval]= useState(1);

    const formRef = useRef(null);
    const handleInput = (e) => {
        const { name, value } = e.target;
        if (name === "userName") {
            setUserName(value);
        } else if (name === "password") {
            setPassWord(value);
        } else if (name === "role") {
            setRole(value);
        } else if(name == "frequencyInterval"){
            setFrequencyInterval(value)
        }
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        if (userName !== "" && passWord !== "" && role !== "") {
            AddUser();
        }
    };

    let AddUser = () => {
        let url = `http://localhost:8080/AttendanceManager/addUser?userName=${userName}&passWord=${passWord}&role=${role}&frequencyInterval=${frequencyInterval}`;

        axios.get(url)
            .then((response) => {
                if (response.status === 200) {
                    fetchAllUsersActions();
                    setError("");
                    setAddUserForm(false); // Close the form after successful submission
                }
            })
            .catch((error) => {
                setError("UserId or password is incorrect " + error);
            });
    };

    let handleLogOut = () => {
        pageControl('logIn');
    };

    useEffect(() => {
        function handleClickOutside(event) {
            if (formRef.current && !formRef.current.contains(event.target)) {
                setAddUserForm(false);
            }
        }

        // Bind the event listener
        document.addEventListener('mousedown', handleClickOutside);
        return () => {
            // Unbind the event listener on clean up
            document.removeEventListener('mousedown', handleClickOutside);
        };
    }, [formRef]);

    return (
        <div className="container mt-5 , addUser">
            <div className="mb-3">
                <button onClick={handleLogOut} className='logout ' >Logout</button>

                <h3
                    className="text-primary cursor-pointer"
                    onClick={() => setAddUserForm(true)}
                >
                    Add User
                </h3>

                {addUserForm && (
                    <form ref={formRef} className='UserForm' onSubmit={handleSubmit}>
                        <div className="mb-3">
                            <input
                                name="userName"
                                type="text"
                                className="form-control"
                                placeholder='Username'
                                value={userName}
                                onChange={handleInput}
                            />
                        </div>
                        <div className="mb-3">
                            <input
                                name="password"
                                type="password"
                                className="form-control"
                                placeholder='Password'
                                value={passWord}
                                onChange={handleInput}
                            />
                        </div>
                        <div className="mb-3">
                            <input
                                name="frequencyInterval"
                                type="number"
                                className="form-control"
                                placeholder='Enter the frequency interval for automatic logout.'
                                value={frequencyInterval}
                                onChange={handleInput}
                            />
                        </div>
                        <div className="mb-3">
                            <select name="role" className="form-select form-select-lg" value={role} onChange={handleInput}>
                                <option value="administrator">Administrator</option>
                                <option value="user">User</option>
                            </select>
                        </div>
                        <span className='errorTxt text-danger'>{error}</span><br />
                        <input type="submit" className='btn btn-primary' value="Submit" />
                    </form>
                )}
            </div>
        </div>
    );
};

export default AddUserForm;
