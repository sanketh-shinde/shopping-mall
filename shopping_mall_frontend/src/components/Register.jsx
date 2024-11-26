import React, { useState } from 'react';

const RegisterForm = () => {

  const [formData, setFormData] = useState({
    name: '',
    mobileNumber: '',
    role: 'employee',
    password: ''
  });

  const [errors, setErrors] = useState({});

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value
    }));
  };

  const validateForm = () => {
    let formErrors = {};
    if (!formData.name) formErrors.name = 'Name is required';
    if (!formData.mobileNumber || !/^\d{10}$/.test(formData.mobileNumber)) {
      formErrors.mobileNumber = 'Mobile number must be 10 digits';
    }
    if (!formData.password || formData.password.length < 6) {
      formErrors.password = 'Password must be at least 6 characters long';
    }
    return formErrors;
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    const formErrors = validateForm();

    if (Object.keys(formErrors).length === 0) {
      console.log('Form submitted:', formData);
      setFormData({
        name: '',
        mobileNumber: '',
        role: 'user',
        password: ''
      });
      setErrors({});
    } else {
      setErrors(formErrors);
    }
  };

  return (
    <div style={styles.container}>
      <div style={styles.formContainer}>
        <h2>Register</h2>
        <form onSubmit={handleSubmit}>
          <div>
            <label htmlFor="name">Name:</label>
            <input
              type="text"
              id="name"
              name="name"
              value={formData.name}
              onChange={handleChange}
              placeholder="Enter your name"
              style={styles.input}
            />
            {errors.name && <p style={styles.error}>{errors.name}</p>}
          </div>

          <div>
            <label htmlFor="mobileNumber">Mobile Number:</label>
            <input
              type="text"
              id="mobileNumber"
              name="mobileNumber"
              value={formData.mobileNumber}
              onChange={handleChange}
              placeholder="Enter your mobile number"
              style={styles.input}
            />
            {errors.mobileNumber && <p style={styles.error}>{errors.mobileNumber}</p>}
          </div>

          <div>
            <label htmlFor="role">Role:</label>
            <select
              id="role"
              name="role"
              value={formData.role}
              onChange={handleChange}
              style={styles.input}
            >
              <option value="employee">Employee</option>
              <option value="admin">Admin</option>
              <option value="manager">Manager</option>
              <option value="accountant">Accountant</option>
              <option value="floor_supervisor">FloorSupervisor</option>
              <option value="sale_supervisor">SaleSupervisor</option>
            </select>
          </div>

          <div>
            <label htmlFor="password">Password:</label>
            <input
              type="password"
              id="password"
              name="password"
              value={formData.password}
              onChange={handleChange}
              placeholder="Enter your password"
              style={styles.input}
            />
            {errors.password && <p style={styles.error}>{errors.password}</p>}
          </div>

          <button type="submit" style={styles.submitButton}>
            Register
          </button>
        </form>
      </div>
    </div>
  );
};
const styles = {
  container: {
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    height: '100vh', 
    backgroundColor: '#f4f4f9',
  },
  formContainer: {
    width: '100%',
    maxWidth: '400px',
    padding: '20px',
    border: '1px solid #ccc',
    borderRadius: '10px',
    backgroundColor: 'white',
  },
  input: {
    width: '100%',
    padding: '10px',
    marginBottom: '10px',
    borderRadius: '5px',
    border: '1px solid #ccc',
    fontSize: '16px',
  },
  submitButton: {
    width: '100%',
    padding: '10px',
    backgroundColor: '#4CAF50',
    color: 'white',
    border: 'none',
    borderRadius: '5px',
    fontSize: '16px',
    cursor: 'pointer',
  },
  error: {
    color: 'red',
    fontSize: '12px',
    marginBottom: '10px',
  }
};
export default RegisterForm;
