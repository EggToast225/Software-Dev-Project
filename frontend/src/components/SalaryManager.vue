<template>
    <div>
        <input v-model="min" placeholder="Min Salary" />
        <input v-model="max" placeholder="Max Salary" />
        <input v-model="percent" placeholder="Increase %" />
        <button @click="search">Preview</button>
        <button @click="update">Apply</button>

        <table v-if="result.length" border="1">
            <thead>
                <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Salary</th>
                <th>Adjusted Salary</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="e in result" :key="e.empid">
                <td>{{ e.empid }}</td>
                <td>{{ e.firstName }} {{ e.lastName }}</td>
                <td>{{ e.salary }}</td>
                <td>{{ adjustedSalary(e.salary) }}</td>
                </tr>
            </tbody>
            </table>
    </div>
</template>

<script setup>
import { ref } from 'vue'
import axios from 'axios'

const min = ref('')
const max = ref('')
const percent = ref('')
const result = ref([])

const search = async () => {
    const res = await axios.get('/api/admin/search-salary', {
        params: { minSalary: min.value, maxSalary: max.value }
    })
    result.value = res.data
}

const update = async () => {
    await axios.patch('/api/admin/update-salary', null, {
        params:{
            minSalary: min.value,
            maxSalary: max.value,
            percentage: percent.value
        }
    })
    result.value = []
}

const adjustedSalary = (salary) => {
    return (salary * (1 + parseFloat(percent.value) / 100)).toFixed(2)
}
</script>

<style scoped>
input {
    margin: 0.5rem;
    padding: 0.5rem;
}
button {
    margin: 0.5rem;
    padding: 0.5rem 1rem;
    font-weight: bold;
}
fieldset {
    margin: 1rem 0;
    padding: 1rem;
    border: 1px solid #ccc;
    border-radius: 8px;
}
legend {
    font-weight: bold;
    padding: 0 0.5rem;
}
table {
    margin-top: 1rem;
    width: 100%;
    border-collapse: collapse;
}
th, td {
    padding: 0.5rem;
    border: 1px solid #ccc;
    text-align: left;
}
</style>