<template>
    <div>
      <h1>Employee View</h1>
      <input v-model="empId" placeholder="Enter your Employee ID" />
      <button @click="fetchEmployee">Fetch My Data</button>
      <div v-if="employee">
        <p>{{ employee.firstName }} {{ employee.lastName }}</p>
        <p>Email: {{ employee.email }}</p>
      </div>
    </div>
  </template>

onMounted(async () => {
    const empId = localStorage.getItem('employeeId')
    const res = await axios.get(`/api/employees/${empId}`)
    employee.value = res.data
  })
  
  <script>
  import axios from 'axios'
  
  export default {
    data() {
      return {
        empId: '',
        employee: null
      }
    },
    methods: {
      async fetchEmployee() {
        const res = await axios.get(`/api/employee/${this.empId}`)
        this.employee = res.data
      }
    }
  }
  </script>