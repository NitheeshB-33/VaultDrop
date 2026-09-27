import React,{useState} from 'react';
import './Login.css';
import { useNavigate } from 'react-router-dom'
import axios from '../axios';

function Login() {
    const [username,setUsername] = useState('')
    const [password,setPassword] = useState('')
    const navigate=useNavigate()
    const handleSubmit =(e)=>{
        e.preventDefault()
        axios.post('/auth/login',{username,password}).then((response)=> {
            console.log("succesfully loged" +response.data.username);
            localStorage.setItem(
                        "token",
                        response.data
                    );
            console.log(response);
            window.location.href = "/";
        }).catch(err => {

            if (err.response && err.response.status === 401) {
                alert(err.response.data.message);
            } else {
                console.log(err);
            }

        });
    }

    return (
        <div>
            <div className="loginParentDiv">
                <form onSubmit={handleSubmit}>
                    <label htmlFor="fname">Username</label>
                    <br />
                    <input
                        className="input"
                        type="text"
                        value={username}
                        onChange={(e)=>setUsername(e.target.value)}
                        id="fname"
                        name="username"

                    />
                    <br />
                    <label htmlFor="lname">Password</label>
                    <br />
                    <input
                        className="input"
                        type="password"
                        value={password}
                        onChange={(e)=>setPassword(e.target.value)}
                        id="lname"
                        name="password"

                    />
                    <br />
                    <br />
                    <button>Login</button>
                </form>
                <a onClick={()=>navigate("/register")}>Signup</a>
            </div>
        </div>
    );
}

export default Login;
