import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { departmentApi } from '../../services/api';

function DepartmentList() {
  const [departments, setDepartments] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    fetchDepartments();
  }, []);

  const fetchDepartments = async () => {
    try {
      setLoading(true);
      const response = await departmentApi.getAll();
      setDepartments(response.data);
      setError(null);
    } catch (err) {
      setError('Failed to fetch departments. Please try again.');
      console.error('Error fetching departments:', err);
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (id) => {
    if (window.confirm('Are you sure you want to delete this department?')) {
      try {
        await departmentApi.delete(id);
        fetchDepartments();
      } catch (err) {
        alert('Failed to delete department. It may have professors assigned to it.');
        console.error('Error deleting department:', err);
      }
    }
  };

  if (loading) return <div className="loading">Loading departments...</div>;
  if (error) return <div className="error">{error}</div>;

  return (
    <div>
      <div className="page-header">
        <h1>Departments</h1>
        <Link to="/departments/new" className="btn btn-primary">Add New Department</Link>
      </div>

      <div className="card">
        <table className="table">
          <thead>
            <tr>
              <th>Code</th>
              <th>Name</th>
              <th>Year Established</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {departments.map((dept) => (
              <tr key={dept.id}>
                <td>{dept.code}</td>
                <td>{dept.name}</td>
                <td>{dept.yearEstablished || 'N/A'}</td>
                <td>
                  <Link to={`/departments/${dept.id}`} className="btn btn-primary">View</Link>
                  <Link to={`/departments/${dept.id}/edit`} className="btn btn-secondary">Edit</Link>
                  <button onClick={() => handleDelete(dept.id)} className="btn btn-danger">Delete</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}

export default DepartmentList;
