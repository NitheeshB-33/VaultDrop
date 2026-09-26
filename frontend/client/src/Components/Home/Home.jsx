import React from "react";
import "./Home.css";
import { useNavigate } from 'react-router-dom'


export default function Home() {
    const navigate=useNavigate()
    const token=localStorage.getItem("token");

    const Logout=(e)=>{
        localStorage.removeItem("token")
        navigate("/login")
    }



    return (
        <div className="home-container">
            <header className="home-header">
                <h1>VaultDrop</h1>
                <p className="tagline">Securely store, share, and manage your files with ease.</p>
            </header>

            <section className="home-hero">
                <div className="hero-text">
                    <h2>Welcome to VaultDrop</h2>
                    <p>
                        VaultDrop is your trusted platform for secure file management, powered by
                        Spring Boot and JWT authentication. Upload, protect, and access your files
                        anytime, anywhere.
                    </p>
                    <div className="hero-buttons">

                        <button className="btn secondary" onClick={() => navigate('/about')}>Learn More</button>

                        {token ? (
                            <>
                                <button className="btn primary" onClick={()=> navigate('/files')}>Get Started</button>
                                <button className="btn secondary" onClick={Logout} >Logout</button>
                                <button className="btn secondary" onClick={() => navigate('/account')}>Account</button>
                                </>
                            ):(
                                <button className="btn primary" onClick={(e)=>navigate("/login")}>Login</button>
                        )}

                    </div>
                </div>
            </section>

            <footer className="home-footer">
                <p>© {new Date().getFullYear()} VaultDrop. All rights reserved.</p>
            </footer>
        </div>
    );
}
