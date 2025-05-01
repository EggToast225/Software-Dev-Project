<template>
    <div>
      <h2>Pay History</h2>
  
      <div v-if="isAdmin">
        <label>Employee ID:</label>
        <input v-model="empidInput" placeholder="Enter Employee ID" />
        <button @click="loadHistory">Load History</button>
      </div>
  
      <table v-if="history.length">
        <thead>
          <tr>
            <th>Pay Date</th>
            <th>Gross Pay</th>
            <th>Net Pay</th>
            <th>Deductions</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="entry in history" :key="entry.id">
            <td>{{ entry.payDate }}</td>
            <td>{{ entry.grossPay }}</td>
            <td>{{ entry.netPay }}</td>
            <td>{{ entry.deductions }}</td>
          </tr>
        </tbody>
      </table>
  
      <p v-if="!history.length">No records found.</p>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue'
  import axios from 'axios'
  
  const history = ref([])
  const empidInput = ref('')
  const isAdmin = localStorage.getItem('role') === 'ADMIN'
  
  const loadHistory = async () => {
    try {
      const empid = isAdmin ? empidInput.value : localStorage.getItem('empid')
      const res = await axios.get(`/api/admin/division/monthly-net-pay/${empid}`)
      history.value = res.data
    } catch (err) {
      console.error('Failed to load history:', err)
    }
  }
  
  onMounted(() => {
    if (!isAdmin) {
      loadHistory()
    }
  })
  </script>