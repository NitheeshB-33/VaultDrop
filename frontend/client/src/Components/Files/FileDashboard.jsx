import { useEffect, useState } from "react";
import axios from "../axios";
import "./FileDashboard.css";


function FileDashboard() {

    const [files, setFiles] = useState([]);
    const [selectedFile, setSelectedFile] = useState(null);

    const [showShareBox, setShowShareBox] = useState(false);
    const [shareFile, setShareFile] = useState(null);
    const [recipientUsername, setRecipientUsername] = useState("");
    const [expiresAt, setExpiresAt] = useState("");

    const [sharedFiles, setSharedFiles] = useState([]);
    const [mySharedFiles, setMySharedFiles] = useState([]);


    useEffect(() => {

        axios.get('/files/getmy').then((response)=>{
            setFiles(response.data);
        })



        axios.get('/shares/received').then((response) => {
            setSharedFiles(response.data);
        });


        axios.get('/shares/my').then((response)=>{
            setMySharedFiles(response.data);
        })



    }, []);


    const handleFileChange = (e) => {
        setSelectedFile(e.target.files[0])
    };

    const handleUpload = () => {

        if (!selectedFile) {
            alert("Please select a file");
            return;
        }

        const formData = new FormData();
        formData.append("file", selectedFile);

        axios.post('/files/upload',formData).then((response)=>{

            console.log(response.data)
            alert("File uploaded successfully")
            setSelectedFile(null);
            //navigate('/files')

            axios.get('/files/getmy').then((response) => {
                setFiles(response.data);
            });

        })


    };

    const handleDownload = (id, fileName) => {

        axios.get(`/files/${id}/download`, {
            responseType: "blob"
        })
            .then((response) => {

                const url = window.URL.createObjectURL(
                    new Blob([response.data])
                );

                const link = document.createElement("a");

                link.href = url;
                link.setAttribute("download", fileName);

                document.body.appendChild(link);

                link.click();

                link.remove();

                window.URL.revokeObjectURL(url);

            })
            .catch((error) => {

                console.log(error);

                if (error.response) {
                    alert("Download failed: Access denied or file sharing has expired.");
                } else {
                    alert("Something went wrong while downloading the file.");
                }

            });

    };

    const handleDelete = (id) => {

        axios.delete(`/files/${id}`).then((response)=>{
            console.log(response.data)
            setFiles(files.filter(file => file.id !== id));

            alert("File deleted successfully");
        })


    };






    // Open Share box
    const openShareBox = (file) => {
        setShareFile(file);
        setRecipientUsername("");
        setExpiresAt("");
        setShowShareBox(true);
    };

    // Close Share box
    const closeShareBox = () => {
        setShowShareBox(false);
        setShareFile(null);
        setRecipientUsername("");
        setExpiresAt("");
    };



    const handleShare = () => {

        if (!recipientUsername.trim()) {
            alert("Please enter username");
            return;
        }

        axios.post(`/shares/${shareFile.id}`, {
            username: recipientUsername,
            expiresAt: expiresAt
        })
            .then((response) => {

                console.log(response.data);

                alert("File shared successfully");

                closeShareBox();

            })
            .catch((error) => {

                console.log(error);

                if (error.response) {
                    alert(error.response.data.message);
                } else {
                    alert("Something went wrong");
                }

            });

    };






    const handleRevoke = (fileId, userId) => {

        if (!window.confirm("Are you sure you want to revoke access?")) {
            return;
        }

        axios.delete(`/shares/${fileId}/${userId}`)
            .then(() => {

                alert("Access revoked successfully");

                setMySharedFiles(
                    mySharedFiles.filter(
                        share =>
                            !(share.fileId === fileId &&
                                share.userId === userId)
                    )
                );

            })
            .catch((error) => {

                console.log(error);

                if (error.response) {
                    alert(error.response.data.message);
                } else {
                    alert("Something went wrong");
                }

            });
    };







    return (

        <div className="file-dashboard">

            <h1>VaultDrop</h1>

            <h2>My Files</h2>

            {/* Upload Section */}

            <div className="upload-container">

                <input
                    type="file"
                    onChange={handleFileChange}
                />

                <button onClick={handleUpload}>
                    Upload File
                </button>

            </div>

            {/* File List */}

            <div className="file-container">

                <table className="file-table">

                    <thead>

                    <tr>
                        <th>FILE ID</th>
                        <th>FILE NAME</th>
                        <th>FILE TYPE</th>
                        <th>FILE SIZE</th>
                        <th>ACTION</th>
                    </tr>

                    </thead>

                    <tbody>

                    {files.map((file) => (

                        <tr key={file.id}>

                            <td>{file.id}</td>

                            <td>{file.fileName}</td>

                            <td>{file.fileType}</td>

                            <td>{file.fileSize} bytes</td>

                            <td>

                                <button
                                    className="download-btn"
                                    onClick={() =>
                                        handleDownload(
                                            file.id,
                                            file.fileName
                                        )
                                    }
                                >
                                    Download
                                </button>

                                <button
                                    className="delete-btn"
                                    onClick={() =>
                                        handleDelete(file.id)
                                    }
                                >
                                    Delete
                                </button>


                                <button className="share-btn" onClick={() => openShareBox(file) } > Share </button>

                            </td>

                        </tr>

                    ))}

                    </tbody>

                </table>

            </div>


            {/* ================= SHARE BOX ================= */}
            {showShareBox && (
                <div className="share-overlay">
                    <div className="share-box">
                        <h2>Share File</h2>
                        <p>
                            File: <strong> {" "}{shareFile?.fileName} </strong>
                        </p>
                        <label> Username </label>
                        <input
                            type="text"
                            placeholder="Enter username"
                            value={recipientUsername}
                            onChange={(e) => setRecipientUsername(e.target.value)}
                        />
                        <label>Expires At</label>

                        <input
                            type="datetime-local"
                            value={expiresAt}
                            onChange={(e) => setExpiresAt(e.target.value)}
                        />

                        <div className="share-actions">
                            <button className="cancel-btn" onClick={closeShareBox}>
                                Cancel
                            </button>
                            <button
                                className="share-confirm-btn"
                                onClick={handleShare}
                            >
                                Share
                            </button>
                        </div>
                    </div>
                </div>
            )}



            <h2 className="shared-heading">
                My Shared Files
            </h2>

            <div className="file-container">

                <table className="file-table">

                    <thead>
                    <tr>
                        <th>FILE NAME</th>
                        <th>SHARED WITH</th>
                        <th>EXPIRES AT</th>
                        <th>ACTION</th>
                    </tr>
                    </thead>

                    <tbody>

                    {mySharedFiles.length === 0 ? (

                        <tr>
                            <td colSpan="4">
                                You have not shared any files.
                            </td>
                        </tr>

                    ) : (

                        mySharedFiles.map((share) => (

                            <tr key={share.id}>

                                <td>{share.fileName}</td>

                                <td>{share.username}</td>

                                <td>{share.expiresAt}</td>

                                <td>

                                    <button
                                        className="delete-btn"
                                        onClick={() =>
                                            handleRevoke(
                                                share.fileId,
                                                share.userId
                                            )
                                        }
                                    >
                                        Revoke
                                    </button>

                                </td>

                            </tr>

                        ))

                    )}

                    </tbody>

                </table>

            </div>




            {/* ================= SHARED WITH ME ================= */}
            <h2 className="shared-heading"> Shared With Me </h2>
            <div className="file-container">
                <table className="file-table">
                    <thead>
                    <tr>
                        <th>FILE ID</th>
                        <th>FILE NAME</th>
                        <th>FILE TYPE</th>
                        <th>FILE SIZE</th>
                        <th>ACTION</th>
                    </tr>
                    </thead>
                    <tbody>
                    {sharedFiles.length === 0 ? (
                        <tr>
                            <td colSpan="5"> No files have been shared with you. </td>
                        </tr>
                    ) : (
                        sharedFiles.map((file) => (
                            <tr key={file.id}>
                                <td>{file.id}</td>
                                <td>{file.fileName}</td>
                                <td>{file.fileType}</td>
                                <td>{file.fileSize} bytes</td>
                                <td>
                                    <button
                                        className="download-btn"
                                        onClick={() => handleDownload(file.id, file.fileName)}
                                    >
                                        Download
                                    </button>
                                </td>
                            </tr>
                        ))
                    )}
                    </tbody>
                </table>
            </div>



        </div>

    );
}

export default FileDashboard;