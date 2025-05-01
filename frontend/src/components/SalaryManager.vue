<template>
    <div>
        <input v-model="min" placeholder="Min Salary" />
        <input v-model="max" placeholder="Max Salary" />
        <input v-model="percent" placeholder="Increase %" />
        <button @click="search">Preview</button>
        <button @click="update">Apply</button>

        <table v-if="result.length">
        <tr v-for="e in result" :key="e.empid">
            <td>{{ e.firstName }} {{ e.lastName }}</td>
            <td>{{ e.salary }}</td>
            <td>{{ adjustedSalary(e.salary) }}</td>
        </tr>
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
        params: { min: min.value, max: max.value }
    })
    result.value = res.data
}

const update = async () => {
    await axios.patch('/api/admin/update-salary', {
        min: min.value,
        max: max.value,
        percent: percent.value
    })
    result.value = []
}

const adjustedSalary = (salary) => {
    return (salary * (1 + parseFloat(percent.value) / 100)).toFixed(2)
}
</script>