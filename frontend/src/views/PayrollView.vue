<template>
    <div>
        <h1>Payroll History</h1>
        <div v-if="payroll.length === 0">
        <p>No payroll records found.</p>
    </div>
    
    <div v-else>
        <table>
            <thead>
            <tr>
            <th>Employee ID</th>
            <th>Pay Date</th>
            <th>Earnings</th>
            <th>Federal Tax</th>
            <th>Medicare</th>
            <th>Social Security</th>
            <th>State Tax</th>
            <th>401k</th>
            <th>Healthcare</th>
            <th>Net Pay</th>
            </tr>
            </thead>
        <tbody>
            <tr v-for="p in payroll" :key="p.id">
            <td>{{ p.employee.empid }}</td>
            <td>{{ formatDate(p.payDate) }}</td>
            <td>{{ toCurrency(p.earnings) }}</td>
            <td>{{ toCurrency(p.fed_tax) }}</td>
            <td>{{ toCurrency(p.fed_med) }}</td>
            <td>{{ toCurrency(p.fed_SS) }}</td>
            <td>{{ toCurrency(p.state_tax) }}</td>
            <td>{{ toCurrency(p.retire_401k) }}</td>
            <td>{{ toCurrency(p.healthCare) }}</td>
            <td>{{ toCurrency(getNetPay(p)) }}</td>
            </tr>
        </tbody>
        </table>
    </div>
    </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import axios from 'axios'
import { useRouter } from 'vue-router'

const router = useRouter()
const payroll = ref([])

const fetchPayroll = async () => {
    try {
    const role = localStorage.getItem('role')
    if (role === 'ADMIN') {
        const res = await axios.get('/api/payroll/all')
        payroll.value = res.data
    } else if (role === 'EMPLOYEE') {
        const empid = localStorage.getItem('empid')
        const res = await axios.get(`/api/payroll/${empid}`)
        payroll.value = res.data
    } else {
        router.push('/')
    }
    } catch (err) {
    console.error('Payroll fetch error:', err)
    }
}

const getNetPay = (p) => {
    return (
    p.earnings -
    (p.fed_tax + p.fed_med + p.fed_SS + p.state_tax + p.retire_401k + p.healthCare)
    )
}

const toCurrency = (value) => {
    return new Intl.NumberFormat('en-US', {
    style: 'currency',
    currency: 'USD',
    }).format(value)
}

const formatDate = (dateStr) => {
    return new Date(dateStr).toLocaleDateString()
}

onMounted(fetchPayroll)
</script>

<style scoped>
table {
    width: 100%;
    border-collapse: collapse;
    margin-top: 1rem;
}
th, td {
    padding: 0.5rem;
    border: 1px solid #ccc;
    text-align: left;
}
th {
    background-color: #f0f0f0;
}
</style>
