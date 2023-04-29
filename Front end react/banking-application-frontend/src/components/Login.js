import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';

function Login() {

  const [username, setUsername] = useState('');
  const[password, setPassword] = useState('');
  const navigate = useNavigate(); // allows me to use the redirect
  

  async function handleSubmit(event)
    {
        event.preventDefault();
      try
        {
          await axios.post("http://localhost:8080/userAccount/login",
          {
          username: username,
          password: password,
        });
          alert("Login Successful");
          setUsername("");
          setPassword("");
          navigate("/home"); // redirect to login
        }
        catch(err)
        {
          console.log(err);
          alert("Login Failed");
        }
    }

  return (
    <div className="container">
      <form onSubmit={handleSubmit}>
        <div class="form-group">
          <label>Username</label>
          <input type="text" class="form-control" placeholder="Enter Username"
          value={username}
          onChange={(event) =>
            {
              setUsername(event.target.value);
            }}
          />
        </div>
        <div class="form-group">
          <label>Password</label>
          <input type="password" class="form-control" placeholder="Enter Password"
          value={password}
          onChange={(event) =>
            {
              setPassword(event.target.value);
            }}
          />
        </div>
        
        <button class="btn btn-primary mt-4">Login</button>
      </form>
    </div>
  );
}

export default Login;