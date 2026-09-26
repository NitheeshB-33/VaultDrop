import React from "react";
import "./Account.css";
import {useState,useEffect} from "react";
import axios from "../axios";
import {useNavigate} from "react-router-dom";


function Account() {

    const navigate=useNavigate()
    const [userDetails,setUserDetails]=useState({});
    const [username,setUsername]=useState('');
    const [existPass,setExistPass]=useState('');
    const [newPass,setNewPass]=useState('');

    useEffect(()=>{

        axios.get("/account/my").then((response)=>{
            setUserDetails(response.data)
        })

    },[])


    const handleSubmit=(e)=>{
        e.preventDefault();
        axios.put("/account/update",{username}).then((response)=>{
            setUserDetails(response.data)
            localStorage.removeItem("token");
            navigate('/login')
        })
    }


    const handlePass=(e)=>{

        e.preventDefault();
        axios.put("/account/updatePass",{existPass,newPass}).then((response)=>{

            setUserDetails(response.data);
            localStorage.removeItem("token");
            navigate("/login")
        })

    }


    return (
        <div className="account-container">
            <h1>My Account</h1>

            {/* View Profile */}
            <section className="account-section">
                <h2>View Profile</h2>
                <div className="profile-info">
                    <p><strong>Username:</strong>{userDetails.username}</p>
                    {/*<p><strong>Email:</strong> {userDetails.email}</p>*/}
                    <p><strong>Role:</strong> {userDetails.role}</p>
                </div>
            </section>

            {/* Update Profile */}
            <section className="account-section">
                <h2>Update Profile</h2>
                <form className="account-form" onSubmit={handleSubmit}>
                    <label>
                        Username
                        <input type="text" placeholder="Enter new username"
                               value={username}
                               onChange={(e)=>setUsername(e.target.value)}
                               id="fname"
                               name="username"
                        />
                    </label>
                    {/*<label>*/}
                    {/*    Email*/}
                    {/*    <input type="email" placeholder="Enter new email" />*/}
                    {/*</label>*/}
                    <button type="submit" className="btn primary">Update</button>
                </form>
            </section>

            {/* Change Password */}
            <section className="account-section">
                <h2>Change Password</h2>
                <form className="account-form" onSubmit={handlePass}>
                    <label>
                        Current Password
                        <input type="password" placeholder="Enter current password"
                               value={existPass}
                               onChange={(e)=>setExistPass(e.target.value)}
                               id="cpass"
                               name="currentpassword"
                        />
                    </label>
                    <label>
                        New Password
                        <input type="password" placeholder="Enter new password"
                               value={newPass}
                               onChange={(e)=>setNewPass(e.target.value)}
                               id="npass"
                               name="newpassword"
                        />
                    </label>
                    <label>
                        Confirm New Password
                        <input type="password" placeholder="Confirm new password"
                               value={newPass}
                               onChange={(e)=>setNewPass(e.target.value)}
                               id="npass"
                               name="newpassword"
                        />
                    </label>
                    <button type="submit" className="btn danger">Change Password</button>
                </form>
            </section>
        </div>
    );
}

export default Account;
