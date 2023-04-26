import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';

function Register() {

  const [accountId, setAccountId] = useState('');
  const [username, setUsername] = useState('');
  const [email, setEmail] = useState('');
  const[password, setPassword] = useState('');
  const[confirmPassword, setConfirmPassword] = useState('');
  const[firstName, setFirstName] = useState('');
  const[lastName, setLastName] = useState('');
  const navigate = useNavigate(); // allows me to use the redirect
  

  async function handleSubmit(event)
    {
        event.preventDefault();
      try
        {
          await axios.post("http://localhost:8080/userAccount/register",
          {
          username: username,
          email: email,
          password: password,
          confirmPassword: confirmPassword,
          firstName, firstName,
          lastName, lastName
        });
          alert("User Registration Successful");
          setAccountId("");
          setUsername("");
          setEmail("");
          setPassword("");
          setConfirmPassword("");
          setFirstName("");
          setLastName("");
          navigate("/login"); // redirect to login
        }
        catch(err)
        {
          console.log(err);
          alert("User Registration Failed");
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
          <label>Email</label>
          <input type="email" class="form-control" placeholder="Enter Email Address"
          value={email}
          onChange={(event) =>
            {
              setEmail(event.target.value);
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
        <div class="form-group">
          <label>Confirm Password</label>
          <input type="password" class="form-control" placeholder="Confirm Password"
          value={confirmPassword}
          onChange={(event) =>
            {
              setConfirmPassword(event.target.value);
            }}
          />
        </div>
        <div class="form-group">
          <label>First Name</label>
          <input type="text" class="form-control" placeholder="Enter First Name"
          value={firstName}
          onChange={(event) =>
            {
              setFirstName(event.target.value);
            }}
          />
        </div>
        <div class="form-group">
          <label>Last Name</label>
          <input type="text" class="form-control" placeholder="Enter Last Name"
          value={lastName}
          onChange={(event) =>
            {
              setLastName(event.target.value);
            }}
          />
        </div>
        
        <button class="btn btn-primary mt-4">Register</button>
      </form>
    </div>
  );
}

export default Register;