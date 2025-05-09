<template>
  <div class="login">
    <h2>Login</h2>
    <form @submit.prevent="handleLogin">
      <input v-model="username" placeholder="Username" required />
      <input v-model="password" type="password" placeholder="Password" required />
      <button type="submit">Login</button>
    </form>
    <p v-if="errorMessage" style="color: red;">{{ errorMessage }}</p>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'

const username = ref('')
const password = ref('')
const errorMessage = ref('')
const router = useRouter()

// Configure to have axios send credentials every request
axios.defaults.withCredentials = true

const handleLogin = async () => {
  try {
    const response = await axios.post("api/auth/login", {
      username: username.value,
      password: password.value,
    }).then(response => {
      const role = response.data.role
      const email = username.value // Store the email (username is the email)

      localStorage.setItem('role', role)
      localStorage.setItem('email', email)
      
      if (role === 'ADMIN') {router.push('/admin')}
      else if (role === 'EMPLOYEE') {router.push('/employee')}
    })
  } catch (err) {
    errorMessage.value = err.response?.data || 'Login failed'
  }
}
</script>

<style>
input {
  margin: 0.5rem;
  padding: 0.5rem;
}
button {
  margin: 0.5rem;
  padding: 0.5rem 1rem;
  font-weight: bold;
}
</style>