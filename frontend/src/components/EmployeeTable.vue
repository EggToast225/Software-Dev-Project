<template>
  <div>
    <h2>Add New Employee</h2>
    <input v-model="form.firstName" placeholder="First Name" />
    <input v-model="form.lastName" placeholder="Last Name" />
    <input v-model="form.email" placeholder="Email" />
    <button @click="addEmployee">Add</button>

    <h2>All Employees</h2>
    <table>
      <thead>
        <tr>
          <th>ID</th><th>First</th><th>Last</th><th>Email</th><th>Action</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="e in employees" :key="e.empid">
          <td>{{ e.empid }}</td>
          <td>{{ e.firstName }}</td>
          <td>{{ e.lastName }}</td>
          <td>{{ e.email }}</td>
          <td><button @click="deleteEmployee(e.empid)">Delete</button></td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

const employees = ref([])
const form = ref({ 
  firstName: '',
  lastName: '', 
  email: '' })

const fetchEmployees = async () => {
  const res = await axios.get('/api/employees')
  employees.value = res.data
}

const addEmployee = async () => {
  try {
    const response = await axios.post('/api/employees', form.value)
    console.log('Employee added:', response.data)
    fetchEmployees()  // Fetch updated list of employees
    form.value = { firstName: '', lastName: '', email: '' }  // Clear form
  } catch (error) {
    console.error('Error adding employee:', error)
  }
}

const deleteEmployee = async (id) => {
  await axios.delete(`/api/employees/${id}`)
  fetchEmployees()
}

onMounted(fetchEmployees)
</script>

<style>
input {
  margin: 0.5rem;
  padding: 0.5rem;
}
table {
  margin-top: 1rem;
  width: 100%;
  border-collapse: collapse;
}
th, td {
  padding: 0.5rem;
  border: 1px solid #ccc;
}
</style>