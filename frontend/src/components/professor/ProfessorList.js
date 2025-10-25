import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { professorApi } from '../../services/api';

function ProfessorList() {
  const [professors, setProfessors] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    fetchProfessors();
  }, []);

  const fetchProfessors = async () => {
    try {
      setLoading(true);
      const response = await professorApi.getAll();
      setProfessors(response.data);
      setError(null);
    } catch (err) {
      setError('Failed to fetch professors. Please try again.');
      console.error('Error fetching professors:', err);
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (id) => {
    if (window.confirm('Are you sure you want to delete this professor?')) {
      try {
        await professorApi.delete(id);
        fetchProfessors();
      } catch (err) {
        alert('Failed to delete professor. Please try again.');
        console.error('Error deleting professor:', err);
      }
    }
  };

  if (loading) return <div className="loading">Loading professors...</div>;
  if (error) return <div className="error">{error}</div>;

  return (
    <div>
      <div className="page-header">
        <h1>Professors</h1>
        <Link to="/professors/new" className="btn btn-primary">Add New Professor</Link>
      </div>

      <div className="card">
        <table className="table">
          <thead>
            <tr>
              <th>Name</th>
              <th>Email</th>
              <th>Title</th>
              <th>Department</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {professors.map((prof) => (
              <tr key={prof.id}>
                <td>{prof.firstName} {prof.lastName}</td>
                <td>{prof.email}</td>
                <td>{prof.title || 'N/A'}</td>
                <td>{prof.department ? prof.department.code : 'N/A'}</td>
                <td>
                  <Link to={`/professors/${prof.id}`} className="btn btn-primary">View</Link>
                  <Link to={`/professors/${prof.id}/edit`} className="btn btn-secondary">Edit</Link>
                  <button onClick={() => handleDelete(prof.id)} className="btn btn-danger">Delete</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}

export default ProfessorList;
