import Navigation from "./components/Navigation";
import Register from "./components/Register";
import Home from "./components/Home";
import Login from "./components/Login";
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';

import "./styles.css";

function App() {

  const isLoggedIn = !!sessionStorage.getItem("user");

  return (
    <Router>
      <Navigation isLoggedIn={isLoggedIn} />
      <Routes>
        <Route exact path="/" component={Home} />
        <Route path="/register" element={<Register/>} />
        <Route path="/login" element={<Login/>} />
      </Routes>
    </Router>
  );
}

export default App;
