import React, { useEffect, useRef, useState } from "react";
import axios from 'axios';
import UsersTable from './UsersTable';
import { Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, TextField, Select, MenuItem } from '@mui/material';

const AddUserForm = (props) => {
    let { pageControl, allUsers, fetchAllUsersActions } = props;

    const [open, setOpen] = useState(false);
    const [userName, setUserName] = useState("");
    const [passWord, setPassWord] = useState("");
    const [error, setError] = useState("");
    const [role, setRole] = useState("user");
    const [frequencyInterval, setFrequencyInterval] = useState(1);
    const [newAllUser, setNewAllUser] = useState([]);
    const [selectedUser, setSelectedUser] = useState(null);



    useEffect(() => {
        if (allUsers.length > 0) {
            setNewAllUser(allUsers);
        }
    }, [allUsers]);

    const handleInput = (e) => {
        const { name, value } = e.target;
        if (name === "userName") {
            setUserName(value);
        } else if (name === "password") {
            setPassWord(value);
        } else if (name === "role") {
            setRole(value);
        } else if (name === "frequencyInterval") {
            setFrequencyInterval(value);
        }
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        
        if(selectedUser.userId !== '' && selectedUser.userId !== undefined){
                
            editUser(selectedUser);
        }else{
             
            if (userName !== "" && passWord !== "" && role !== "") {
                AddUser()
            }else{
                alert("Please fill the all details")
            }
        } 
    };

    let AddUser = () => {
        let url = `http://localhost:8080/AttendanceManager/addUser?userName=${userName}&passWord=${passWord}&role=${role}&frequencyInterval=${frequencyInterval}`;

        axios.get(url)
            .then((response) => {
                if (response.status === 200) {
                    fetchAllUsersActions();
                    setError("");
                    setOpen(false);
                    setUserName("")
                    setRole('')
                    setFrequencyInterval('')
                    setPassWord('')
                    setSelectedUser('')
                }
            })
            .catch((error) => {
                setError("UserId or password is incorrect " + error);
            });
    };

    const handleClickOpen = () => {
        setSelectedUser("");
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    const handleEditUser = (user) => {        
       
        if(user.userName.toLowerCase() == "administrator"){
            alert("administrator not editable")
        }else{
            setOpen(true);
            setUserName(user.userName)
            setRole(user.userRole)
            setFrequencyInterval(user.frequencyIntervalToLogOut)
            setPassWord(user.password)
            setSelectedUser(user)

        }
        
    }

    let editUser = (user) =>{
        let url = `http://localhost:8080/AttendanceManager/editUser?userId=${user.userId}&userName=${userName}&passWord=${passWord}&role=${role}&frequencyInterval=${frequencyInterval}`;
        
        axios.get(url)
        .then((response) => {
            if (response.status === 200) {
                fetchAllUsersActions()
                    setError("");
                    setOpen(false);
            }
        })
        .catch((error) => {
            console.log("error = ", error);
        });
    }

    const handleDeleteUser = (user) => {
        if(user.userName.toLowerCase() == "administrator"){
            alert("administrator undetectable")
        }else{
            const url = `http://localhost:8080/AttendanceManager/deleteUser?userId=${user.userId}`;

            axios.get(url)
            .then((response) => {
                if (response.status === 200) {
                    fetchAllUsersActions()
                }
            })
            .catch((error) => {
                console.log("error = ", error);
            });
        }
    }

    return (
        <div className="container mt-5">
            <div className="mb-3">
                <button onClick={handleClickOpen} style={{ cursor: 'pointer', padding: '10px 20px', marginBottom: '20px', backgroundColor: '#333b3c', color: 'white', border: 'none', borderRadius: '4px' }}>
                    Add User
                </button>
                <Dialog open={open} onClose={handleClose} aria-labelledby="form-dialog-title">
                    <DialogTitle id="form-dialog-title">Add User</DialogTitle>
                    <DialogContent>
                        <DialogContentText>
                            To add a new user, please enter the following details.
                        </DialogContentText>
                        <form  onSubmit={handleSubmit}>
                            <TextField
                                autoFocus
                                margin="dense"
                                id="userName"
                                name="userName"
                                label="Username"
                                type="text"
                                fullWidth
                                value={userName}
                                onChange={handleInput}
                                placeholder="Enter username"
                            />
                            <TextField
                                margin="dense"
                                id="password"
                                name="password"
                                label="Password"
                                type="password"
                                fullWidth
                                value={passWord}
                                onChange={handleInput}
                            />
                            <TextField
                                margin="dense"
                                id="frequencyInterval"
                                name="frequencyInterval"
                                label="Enter a log out Frequency Interval In Minutes"
                                type="number"
                                fullWidth
                                value={frequencyInterval}
                                onChange={handleInput}
                            />
                            <Select
                                labelId="role-label"
                                id="role"
                                name="role"
                                label="Select a Role For a User"
                                value={role}
                                onChange={handleInput}
                                fullWidth
                                style={{ marginTop: '16px' }}
                            >
                                <MenuItem value="admin">Administrator</MenuItem>
                                <MenuItem value="user">User</MenuItem>
                            </Select>
                            <span className='errorTxt text-danger'>{error}</span>
                        </form>
                    </DialogContent>
                    <DialogActions>
                        <button onClick={handleClose} style={{ cursor: 'pointer', padding: '10px 20px', backgroundColor: '#6c757d', color: 'white', border: 'none', borderRadius: '4px', marginRight: '10px' }}>
                            Cancel
                        </button>
                        <button onClick={handleSubmit} style={{ cursor: 'pointer', padding: '10px 20px', backgroundColor: '#767676', color: 'white', border: 'none', borderRadius: '4px' }}>
                            Submit
                        </button>
                    </DialogActions>
                </Dialog>
            </div>
            {allUsers.length > 0 && <UsersTable newAllUser={newAllUser} onEditUser={handleEditUser}  onDeleteUser = {handleDeleteUser}/>}
        </div>
    );
};

export default AddUserForm;
