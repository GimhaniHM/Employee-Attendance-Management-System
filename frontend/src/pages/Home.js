// src/pages/Home.js
import React from "react";
import { Link } from "react-router-dom";

const Home = () => (
  <div className="flex flex-col items-center justify-center h-screen bg-gray-200">
    <h1 className="text-3xl font-bold mb-6">Welcome to Attendance App</h1>
    <div className="flex gap-4">
      <Link to="/signup" className="bg-blue-500 px-4 py-2 text-white rounded hover:bg-blue-600">Sign Up</Link>
      <Link to="/login" className="bg-green-500 px-4 py-2 text-white rounded hover:bg-green-600">Login</Link>
    </div>
  </div>
);

export default Home;
