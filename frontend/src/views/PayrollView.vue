<template>
    <div>
        <h1>Payroll History</h1>
        
        <!-- Filters -->
        <div class="filters" v-if="isAdmin">
            <div class="filter-group">
                <label>View By:</label>
                <select v-model="viewMode">
                    <option value="all">All Payroll</option>
                    <option value="jobTitle">By Job Title</option>
                    <option value="division">By Division</option>
                    <option value="employee">By Employee</option>
                </select>
            </div>

            <div class="filter-group" v-if="viewMode === 'jobTitle' || viewMode === 'division'">
                <label>Year:</label>
                <select v-model="selectedYear">
                    <option v-for="year in years" :key="year" :value="year">{{ year }}</option>
                </select>
                <label>Month:</label>
                <select v-model="selectedMonth">
                    <option v-for="(month, index) in months" :key="index" :value="index + 1">{{ month }}</option>
                </select>
            </div>

            <div class="filter-group" v-if="viewMode === 'employee'">
                <label>Employee ID:</label>
                <input v-model="employeeId" type="number" placeholder="Enter Employee ID" />
            </div>

            <div class="filter-group">
                <label>Sort By:</label>
                <select v-model="sortBy">
                    <option value="employeeId">Employee ID</option>
                    <option value="date">Date</option>
                    <option value="netPay">Net Pay</option>
                </select>
                <select v-model="sortOrder">
                    <option value="asc">Ascending</option>
                    <option value="desc">Descending</option>
                </select>
            </div>
        </div>

        <!-- Payroll Table -->
        <div v-if="payroll.length === 0">
            <p>No payroll records found.</p>
        </div>

        <div v-else>
            <table>
                <thead>
                    <tr>
                        <th v-if="viewMode === 'all' || viewMode === 'employee'">Employee ID</th>
                        <th v-if="viewMode === 'all' || viewMode === 'employee'">Name</th>
                        <th v-if="viewMode === 'jobTitle'">Job Title</th>
                        <th v-if="viewMode === 'division'">Division</th>
                        <th>Pay Date</th>
                        <th>Earnings</th>
                        <th v-if="viewMode === 'all' || viewMode === 'employee'">Federal Tax</th>
                        <th v-if="viewMode === 'all' || viewMode === 'employee'">Medicare</th>
                        <th v-if="viewMode === 'all' || viewMode === 'employee'">Social Security</th>
                        <th v-if="viewMode === 'all' || viewMode === 'employee'">State Tax</th>
                        <th v-if="viewMode === 'all' || viewMode === 'employee'">401k</th>
                        <th v-if="viewMode === 'all' || viewMode === 'employee'">Healthcare</th>
                        <th>Net Pay</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="p in sortedPayroll" :key="p.id">
                        <td v-if="viewMode === 'all' || viewMode === 'employee'">{{ p.employee.empid }}</td>
                        <td v-if="viewMode === 'all' || viewMode === 'employee'">{{ p.employee.firstName }} {{ p.employee.lastName }}</td>
                        <td v-if="viewMode === 'jobTitle'">{{ p.jobTitle }}</td>
                        <td v-if="viewMode === 'division'">{{ p.division }}</td>
                        <td>{{ formatDate(p.payDate) }}</td>
                        <td>{{ toCurrency(p.earnings) }}</td>
                        <td v-if="viewMode === 'all' || viewMode === 'employee'">{{ toCurrency(p.fed_tax) }}</td>
                        <td v-if="viewMode === 'all' || viewMode === 'employee'">{{ toCurrency(p.fed_med) }}</td>
                        <td v-if="viewMode === 'all' || viewMode === 'employee'">{{ toCurrency(p.fed_SS) }}</td>
                        <td v-if="viewMode === 'all' || viewMode === 'employee'">{{ toCurrency(p.state_tax) }}</td>
                        <td v-if="viewMode === 'all' || viewMode === 'employee'">{{ toCurrency(p.retire_401k) }}</td>
                        <td v-if="viewMode === 'all' || viewMode === 'employee'">{{ toCurrency(p.healthCare) }}</td>
                        <td>{{ toCurrency(getNetPay(p)) }}</td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</template>

<script setup>
import { ref, computed, onMounted, watch } from 'vue'
import axios from 'axios'
import { useRouter } from 'vue-router'

const router = useRouter()
const payroll = ref([])
const isAdmin = localStorage.getItem('role') === 'ADMIN'
const viewMode = ref('all')
const selectedYear = ref(new Date().getFullYear())
const selectedMonth = ref(new Date().getMonth() + 1)
const employeeId = ref('')
const sortBy = ref('employeeId')
const sortOrder = ref('asc')

const years = Array.from({ length: 5 }, (_, i) => new Date().getFullYear() - i)
const months = [
    'January', 'February', 'March', 'April', 'May', 'June',
    'July', 'August', 'September', 'October', 'November', 'December'
]

const fetchPayroll = async () => {
    try {
        if (viewMode.value === 'all') {
            const res = await axios.get('/api/admin/payroll/all')
            payroll.value = res.data
        } else if (viewMode.value === 'jobTitle') {
            const res = await axios.get('/api/admin/job-titles/monthly-net-pay', {
                params: { year: selectedYear.value, month: selectedMonth.value }
            })
            payroll.value = res.data.map(item => ({
                jobTitle: item.jobTitle,
                earnings: item.earnings || 0,
                payDate: new Date(selectedYear.value, selectedMonth.value - 1, 1),
                fed_tax: 0,
                fed_med: 0,
                fed_SS: 0,
                state_tax: 0,
                retire_401k: 0,
                healthCare: 0
            }))
        } else if (viewMode.value === 'division') {
            const res = await axios.get('/api/admin/division/monthly-net-pay', {
                params: { year: selectedYear.value, month: selectedMonth.value }
            })
            payroll.value = res.data.map(item => ({
                division: item.division,
                earnings: item.earnings || 0,
                payDate: new Date(selectedYear.value, selectedMonth.value - 1, 1),
                fed_tax: 0,
                fed_med: 0,
                fed_SS: 0,
                state_tax: 0,
                retire_401k: 0,
                healthCare: 0
            }))
        } else if (viewMode.value === 'employee') {
            const empid = employeeId.value || localStorage.getItem('empid')
            const res = await axios.get(`/api/admin/division/monthly-net-pay/${empid}`)
            payroll.value = res.data
        }
    } catch (err) {
        console.error('Payroll fetch error:', err)
        let errorMessage = 'Error fetching payroll data'
        
        if (err.response) {
            // The request was made and the server responded with a status code
            // that falls out of the range of 2xx
            if (err.response.status === 403) {
                errorMessage = 'Access denied. Please log in again.'
                router.push('/')
            } else if (err.response.data) {
                // Try to get a more specific error message from the response
                errorMessage = typeof err.response.data === 'string' 
                    ? err.response.data 
                    : err.response.data.message || JSON.stringify(err.response.data)
            }
        } else if (err.request) {
            // The request was made but no response was received
            errorMessage = 'No response from server. Please check your connection.'
        } else {
            // Something happened in setting up the request that triggered an Error
            errorMessage = err.message || 'An unexpected error occurred'
        }
        
        alert(errorMessage)
    }
}

const sortedPayroll = computed(() => {
    return [...payroll.value].sort((a, b) => {
        let comparison = 0
        if (sortBy.value === 'employeeId') {
            comparison = a.employee?.empid - b.employee?.empid
        } else if (sortBy.value === 'date') {
            comparison = new Date(a.payDate) - new Date(b.payDate)
        } else if (sortBy.value === 'netPay') {
            comparison = getNetPay(a) - getNetPay(b)
        }
        return sortOrder.value === 'asc' ? comparison : -comparison
    })
})

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

// Watch for changes in filters
watch([viewMode, selectedYear, selectedMonth, employeeId], () => {
    fetchPayroll()
})

onMounted(fetchPayroll)
</script>

<style scoped>
.filters {
    margin-bottom: 1rem;
    padding: 1rem;
    background-color: #f5f5f5;
    border-radius: 4px;
}

.filter-group {
    display: inline-block;
    margin-right: 1rem;
    margin-bottom: 0.5rem;
}

.filter-group label {
    margin-right: 0.5rem;
}

select, input {
    padding: 0.5rem;
    margin-right: 0.5rem;
    border: 1px solid #ccc;
    border-radius: 4px;
}

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
    position: sticky;
    top: 0;
}

tr:hover {
    background-color: #f5f5f5;
}
</style>
