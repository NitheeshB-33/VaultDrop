import { jwtDecode } from "jwt-decode";
import{BrowserRouter as Router,Routes,Route} from "react-router-dom";
import HomePage from './Pages/HomePage'
import AboutPage from './Pages/AboutPage'
import LoginPage from './Pages/LoginPage'
import RegisterPage from './Pages/RegisterPage'
import AdminDashboardPage from './Pages/AdminDashboardPage'
import AccountPage from './Pages/AccountPage'
import Manage from "./Components/Manage/Manage";
import FileDashboardPage from "./Pages/FileDashboardPage"

function App() {

    const token=localStorage.getItem("token")


    let role = null;

    if (token) {
        const decoded = jwtDecode(token);
        role = decoded.role;
    }

    console.log("TOKEN:", token);
    console.log("ROLE:", role);
    console.log(role);




  return (
    <div className="App">

        <Router>
            <Routes>

                {role === 'ADMIN' && (
                    <>
                    <Route path="/manage" element={<Manage/>}/>
                    <Route path="/dashboard" element={<AdminDashboardPage/>}/>
                    </>
                )}

                <Route path="/files" element={<FileDashboardPage/>}/>


                    <Route path="/account" element={<AccountPage/>}/>
                    <Route path="/" element={<HomePage/>}/>
                    <Route path="/about" element={<AboutPage/>}/>
                    <Route path="/login" element={<LoginPage/>}/>
                    <Route path="/register" element={<RegisterPage/>}/>

            </Routes>
        </Router>

    </div>
  );
}

export default App;
