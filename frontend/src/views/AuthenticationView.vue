<!-- AuthenticationView.vue -->
<template>
    <div>
        <h2> Login </h2>
      <input v-model="login.username" placeholder="Username" />
      <input type="password" v-model="login.password" placeholder="Password" />
      <button @click="handleLogin">Login</button>
      <p v-if="error">{{ error }}</p>
    </div>
  </template>
  
  <script setup>
  import { ref } from 'vue'
  import axios from 'axios'
  import { useRouter } from 'vue-router'
  
  const router = useRouter() 
  const login = ref({ username: '', password: '' })
  const error = ref('')
  
  const handleLogin = async () => {
    try {
      const res = await axios.post('/api/auth/login', login.value)
      const role = res.data.role
  
      if (role === 'admin') {
        router.push('/admin')
      } else if (role === 'employee') {
        localStorage.setItem('employeeId', res.data.empid)
        router.push('/employee')
      } else {
        error.value = 'Unknown role'
      }
    } catch (err) {
      error.value = 'Invalid credentials'
    }
  }
  </script>