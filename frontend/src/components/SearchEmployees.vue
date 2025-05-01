<template>
<!-- Search Section -->
    <h2>Search Employees</h2>
    <div>
    <input v-model="search.firstName" placeholder="Search by First Name" />
    <input v-model="search.lastName" placeholder="Search by Last Name" />
    <input v-model="search.ssn" placeholder="Search by SSN" />
    <input v-model="search.empid" placeholder="Search by Employee ID" />
    <button @click="searchEmployees">Search</button>
    <button @click="clearSearch">Clear</button>
    </div>
</template>

<script setup>

import { ref } from 'vue'
import axios from 'axios'

const emit = definteEmits(['searched'])
const search = ref({
    firstName: '',
    lastName: '',
    ssn: '',
    empid: ''
})

// Search
const searchEmployees = async () => {
try {
    const res = await axios.get('/api/admin/search', {
        params: {
        firstName: search.value.firstName,
        lastName: search.value.lastName,
        ssn: search.value.ssn,
        empid: search.value.empid
        }
    })
    emit('searched', res.data)
    } catch (err) {
    console.error('Search error:', err)
    emit('searched',[])
    }
}

// Clear search
const clearSearch = () => {
    search.value = {
    firstName: '',
    lastName: '',
    ssn: '',
    empid: ''
    }
    emit('searched',[])
}
</script>