import React from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import './App.css';

// Department components
import DepartmentList from './components/department/DepartmentList';
import DepartmentDetail from './components/department/DepartmentDetail';
import DepartmentForm from './components/department/DepartmentForm';

// Professor components
import ProfessorList from './components/professor/ProfessorList';
import ProfessorDetail from './components/professor/ProfessorDetail';
import ProfessorForm from './components/professor/ProfessorForm';

// Home component
import Home from './components/Home';

function App() {
  return (
    <Router>
      <div className="App">
        <nav className="navbar">
          <div className="nav-container">
            <Link to="/" className="nav-logo">University Department System</Link>
            <ul className="nav-menu">
              <li className="nav-item">
                <Link to="/departments" className="nav-link">Departments</Link>
              </li>
              <li className="nav-item">
                <Link to="/professors" className="nav-link">Professors</Link>
              </li>
            </ul>
          </div>
        </nav>

        <div className="container">
          <Routes>
            <Route path="/" element={<Home />} />

            {/* Department routes */}
            <Route path="/departments" element={<DepartmentList />} />
            <Route path="/departments/new" element={<DepartmentForm />} />
            <Route path="/departments/:id" element={<DepartmentDetail />} />
            <Route path="/departments/:id/edit" element={<DepartmentForm />} />

            {/* Professor routes */}
            <Route path="/professors" element={<ProfessorList />} />
            <Route path="/professors/new" element={<ProfessorForm />} />
            <Route path="/professors/:id" element={<ProfessorDetail />} />
            <Route path="/professors/:id/edit" element={<ProfessorForm />} />
          </Routes>
        </div>
      </div>
    </Router>
  );
}

export default App;
