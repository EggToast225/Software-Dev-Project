<template>
  <div class="employee-view">
    <h1>Employee Dashboard</h1>
    
    <!-- Personal Information Section -->
    <div v-if="employee" class="info-section">
      <h2>Personal Information</h2>
      <div class="info-grid">
        <div class="info-item">
          <label>Name:</label>
          <span>{{ employee.firstName }} {{ employee.lastName }}</span>
        </div>
        <div class="info-item">
          <label>Email:</label>
          <span>{{ employee.email }}</span>
        </div>
        <div class="info-item">
          <label>Phone:</label>
          <span>{{ employee.phone }}</span>
        </div>
        <div class="info-item">
          <label>Date of Birth:</label>
          <span>{{ formatDate(employee.dob) }}</span>
        </div>
        <div class="info-item">
          <label>Gender:</label>
          <span>{{ employee.gender }}</span>
        </div>
        <div class="info-item">
          <label>Race:</label>
          <span>{{ employee.identifiedRace }}</span>
        </div>
      </div>

      <!-- Address Information -->
      <h2>Address</h2>
      <div class="info-grid">
        <div class="info-item">
          <label>Street:</label>
          <span>{{ employee.address?.street }}</span>
        </div>
        <div class="info-item">
          <label>City:</label>
          <span>{{ employee.address?.city?.cityName }}</span>
        </div>
        <div class="info-item">
          <label>State:</label>
          <span>{{ employee.address?.state?.stateName }}</span>
        </div>
        <div class="info-item">
          <label>ZIP:</label>
          <span>{{ employee.address?.zip }}</span>
        </div>
      </div>

      <!-- Employment Information -->
      <h2>Employment Details</h2>
      <div class="info-grid">
        <div class="info-item">
          <label>Hire Date:</label>
          <span>{{ formatDate(employee.hireDate) }}</span>
        </div>
        <div class="info-item">
          <label>Salary:</label>
          <span>${{ formatSalary(employee.salary) }}</span>
        </div>
        <div class="info-item">
          <label>Job Title:</label>
          <span>{{ employee.jobTitle }}</span>
        </div>
      </div>

      <!-- Payroll Link -->
      <div class="payroll-link">
        <router-link to="/pay-history" class="payroll-button">View Payroll History</router-link>
      </div>
    </div>
    <div v-else class="loading">Loading employee information...</div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

const employee = ref(null)

// Configure axios to send credentials
axios.defaults.withCredentials = true

const fetchEmployeeData = async () => {
  try {
    const email = localStorage.getItem('email')
    if (!email) {
      console.error('No email found in localStorage')
      return
    }

    // Fetch employee details
    const employeeResponse = await axios.get(`/api/employee/details?email=${email}`)
    employee.value = employeeResponse.data

    // Fetch job title
    const jobTitleResponse = await axios.get(`/api/employee/job-title?email=${email}`)
    if (jobTitleResponse.data) {
      employee.value.jobTitle = jobTitleResponse.data.title
    }
  } catch (error) {
    console.error('Error fetching employee data:', error)
  }
}

const formatDate = (dateString) => {
  if (!dateString) return 'N/A'
  return new Date(dateString).toLocaleDateString()
}

const formatSalary = (amount) => {
  if (!amount) return '0.00'
  return amount.toFixed(2)
}

onMounted(fetchEmployeeData)
</script>

<style scoped>
.employee-view {
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
}

.info-section {
  margin-top: 20px;
}

.info-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 20px;
  margin-bottom: 30px;
}

.info-item {
  display: flex;
  flex-direction: column;
  gap: 5px;
}

.info-item label {
  font-weight: bold;
  color: #666;
}

.payroll-link {
  margin-top: 30px;
  text-align: center;
}

.payroll-button {
  display: inline-block;
  padding: 12px 24px;
  background-color: #4CAF50;
  color: white;
  text-decoration: none;
  border-radius: 4px;
  font-weight: bold;
  transition: background-color 0.3s;
}

.payroll-button:hover {
  background-color: #45a049;
}

.loading {
  text-align: center;
  padding: 20px;
  color: #666;
}
</style>