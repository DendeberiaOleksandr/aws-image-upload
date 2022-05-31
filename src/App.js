import React, {useState, useEffect, useCallback} from 'react'
import {useDropzone} from 'react-dropzone'
import axios from 'axios'
import './App.css'

const UserProfiles = () => {
    const [userProfiles, setUserProfiles] = useState([])

    const fetchUserProfiles = () => { axios.get("http://localhost:8080/api/v1/user-profiles")
        .then(res => {
          console.log(res)
          setUserProfiles(res.data)
        })
  }

  useEffect(() => {
    fetchUserProfiles();
  }, [])

  return userProfiles.map((userProfile, index) => {
      return (
          <div style={{marginTop: "100px"}} key={userProfile.profileId}>
              { userProfile.profileImageLink ? <img alt={"No Image"} src={`http://localhost:8080/api/v1/user-profiles/${userProfile.profileId}/image/download`} /> : null}
              <br/>
              <br/>
              <h1>{userProfile.username}</h1>
              <p>{userProfile.profileId}</p>
              <Dropzone userProfileId={userProfile.profileId} />
          </div>
      );
  })

}

function Dropzone({ userProfileId }) {
    const onDrop = useCallback(acceptedFiles => {

        const file = acceptedFiles[0]

        const formData = new FormData()
        formData.append("file", file)

        axios.post(`http://localhost:8080/api/v1/user-profiles/${userProfileId}/image/upload`,
            formData,
            {
                headers: {
                    "Content-Type": "multipart/form-data"
                }
            }).then(() => {
                console.log("File uploaded successfully")
        }).catch(err => console.log(err))
        console.log(file)
    }, [])
    const {getRootProps, getInputProps, isDragActive} = useDropzone({onDrop})

    return (
        <div {...getRootProps()}>
            <input {...getInputProps()} />
            {
                isDragActive ?
                    <p>Drop the image here ...</p> :
                    <p>Drag 'n' drop profile image, or click to select profile image</p>
            }
        </div>
    )
}

function App() {

  return (
    <div className="App">
      <UserProfiles/>
    </div>
  );
}

export default App;
