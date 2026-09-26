import {useEffect, useState} from "react";
import axios from "../axios";
import './Manage.css'


function Manage(){

    const [users,setUsers]=useState([]);
    useEffect(()=>{

        axios.get("/admin/users").then((response)=>{
            setUsers(response.data);
        })

    },[])

    const handleDelete =(id)=>{

        axios.delete(`/admin/users/${id}`).then((response)=>{
            setUsers(users.filter(user => user.id !== id));
            console.log(response.data)
        })

    }


    return (
      <div>
          <h1>Welcome to users List</h1>
          <h1>Below List Visible To Admin Only</h1>
          <div className="admin-container">
              <table className="admin-table" id="productsTable">
                  <thead>
                  <tr>
                      <th>USER ID</th>
                      <th>USERNAME</th>
                      <th>PASSWORD</th>
                      <th>ACTION</th>

                  </tr>
                  </thead>
                  <tbody>
                  {users.map((user) => (
                      <tr key={user.id}>
                          <td>{user.id}</td>
                          <td>{user.username}</td>
                          <td>{user.password}</td>
                          <td>
                              <button className="delete-btn" onClick={() => handleDelete(user.id)}>
                                  Delete
                              </button>
                          </td>
                      </tr>
                  ))}
                  </tbody>

              </table>
          </div>
      </div>
    );


}

export default Manage;