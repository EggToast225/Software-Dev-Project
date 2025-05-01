<template>
  <div>
  <!-- Add New Employee -->
  <h2>Add New Employee</h2>
  <fieldset>
    <legend>Personal Info</legend>
    <input v-model="form.firstName" placeholder="First Name" />
    <input v-model="form.lastName" placeholder="Last Name" />
    <input v-model="form.dob" placeholder="Date of Birth (YYYY-MM-DD)" />
    <input v-model="form.gender" placeholder="Gender" />
    <input v-model="form.identifiedRace" placeholder="Identified Race" />
    <input v-model="form.ssn" placeholder="SSN" />
  </fieldset>

  <fieldset>
    <legend>Contact Info</legend>
    <input v-model="form.email" placeholder="Email" />
    <input v-model="form.phone" placeholder="Phone" />
  </fieldset>

  <fieldset>
    <legend>Job Info</legend>
    <input v-model="form.hireDate" placeholder="Hire Date (YYYY-MM-DD)" />
    <input v-model="form.salary" placeholder="Salary" />
  </fieldset>

  <button @click="addEmployee">Add Employee</button>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import axios from 'axios'

// Employee form
const form = ref({
  firstName: '',
  lastName: '',
  dob: '',
  gender: '',
  identifiedRace: '',
  ssn: '',
  email: '',
  phone: '',
  hireDate: '',
  salary: ''
})

// Add new employee
const addEmployee = async () => {
  try {
    await axios.post('/api/admin', form.value)
    defineEmits('added')
    
    // Clears form
    form.value = {
      firstName: '',
      lastName: '',
      dob: '',
      gender: '',
      identifiedRace: '',
      ssn: '',
      email: '',
      phone: '',
      hireDate: '',
      salary: ''
    }
  } catch (err) {
    console.error('Add error:', err)
  }
}
</script>
