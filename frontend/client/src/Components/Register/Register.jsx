import React, { useState} from 'react';
import { useNavigate } from 'react-router-dom';
import './Register.css';
import axios from '../axios'

export default function Register() {

    const navigate = useNavigate();
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const handleSubmit = (e) => {
        e.preventDefault();

        axios.post('/auth/register', { username, password })
            .then((response) => {
                console.log('User registered:', username);
                console.log(response);

                navigate('/');
            })
            .catch(err => {
                if (err.response && err.response.status === 409) {
                    alert(err.response.data.message);
                } else {
                    console.log(err);
                }
            });
    };

    return (
        <div className="signupParentDiv">
            <form onSubmit={handleSubmit}>
                <label htmlFor="username">Username</label>
                <br />
                <input
                    className="input"
                    type="text"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    id="username"
                    name="username"
                />
                <br />

                <label htmlFor="password">Password</label>
                <br />
                <input
                    className="input"
                    type="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    id="password"
                    name="password"
                />
                <br /><br />

                <button type="submit">Signup</button>
            </form>

            <button type="button" onClick={() => navigate('/login')}>Login</button>
        </div>
    );
}

