import { useEffect, useState } from "react";
import axios from "../axios";
import { useNavigate } from "react-router-dom";
import "./Dashboard.css";

function Dashboard() {

    const [message, setMessage] = useState("");
    const [users, setUsers] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    const navigate = useNavigate();

    useEffect(() => {

        const loadDashboard = async () => {

            try {

                const [dashboardResponse, usersResponse] = await Promise.all([
                    axios.get("/admin/dashboard"),
                    axios.get("/admin/users")
                ]);

                setMessage(dashboardResponse.data);
                setUsers(usersResponse.data);

            } catch (err) {

                console.error("Dashboard loading error:", err);
                setError("Unable to load dashboard information.");

            } finally {

                setLoading(false);

            }
        };

        loadDashboard();

    }, []);

    return (
        <div className="admin-dashboard">

            {/* Header */}
            <div className="dashboard-header">

                <div>
                    <p className="dashboard-label">ADMIN PANEL</p>

                    <h1>VaultDrop Dashboard</h1>

                    <p className="dashboard-subtitle">
                        Welcome back, Administrator. Manage your VaultDrop
                        application from one place.
                    </p>
                </div>

                <div className="admin-badge">
                    <span>●</span>
                    Admin
                </div>

            </div>


            {/* Error */}
            {error && (
                <div className="dashboard-error">
                    {error}
                </div>
            )}


            {/* Statistics */}
            <div className="dashboard-stats">

                {/* Total Users */}
                <div className="dashboard-card">

                    <div className="card-icon users-icon">
                        👥
                    </div>

                    <div className="card-content">

                        <p>Total Users</p>

                        <h2>
                            {loading ? "..." : users.length}
                        </h2>

                        <span>
                            Registered VaultDrop users
                        </span>

                    </div>

                </div>


                {/* System Status */}
                <div className="dashboard-card">

                    <div className="card-icon status-icon">
                        ✓
                    </div>

                    <div className="card-content">

                        <p>System Status</p>

                        <h2 className="status-text">
                            {loading ? "..." : "Online"}
                        </h2>

                        <span>
                            {message || "Application status"}
                        </span>

                    </div>

                </div>

            </div>


            {/* Quick Actions */}
            <section className="dashboard-section">

                <div className="section-heading">

                    <div>
                        <p className="section-label">QUICK ACTIONS</p>

                        <h2>Administration</h2>
                    </div>

                </div>


                <div className="action-card">

                    <div className="action-icon">
                        👥
                    </div>

                    <div className="action-content">

                        <h3>User Management</h3>

                        <p>
                            View, manage and remove registered users
                            from the VaultDrop application.
                        </p>

                    </div>

                    <button
                        className="manage-button"
                        onClick={() => navigate("/manage")}
                    >
                        Manage Users
                        <span>→</span>
                    </button>

                </div>

            </section>


            {/* Information */}
            <section className="dashboard-info">

                <div className="info-icon">
                    🔐
                </div>

                <div>
                    <h3>VaultDrop Administration</h3>

                    <p>
                        This dashboard provides a central overview of the
                        VaultDrop system. Use User Management to manage
                        registered accounts.
                    </p>
                </div>

            </section>

        </div>
    );
}

export default Dashboard;