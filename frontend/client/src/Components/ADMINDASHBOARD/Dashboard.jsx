import {useEffect, useState} from "react";
import axios from "../axios";


function Dashboard(){

    const [res,setRes]=useState('');

    useEffect(()=>{
        axios.get("/admin/dashboard").then((response)=>{
            setRes(response.data);
        })
    },[])

    return (
        <div>
            <h1>{res}</h1>
        </div>
    );
}

export default Dashboard;