<template>
    <div class="payroll-view">
        <h1>Payroll History</h1>
        
        <!-- Admin Filters -->
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
            </div>

            <div class="filter-group" v-if="viewMode === 'employee'">
                <label>Employee ID:</label>
                <input v-model="employeeId" type="number" placeholder="Enter Employee ID" />
            </div>

            <div class="filter-group" v-if="viewMode === 'division'">
                <label>Division:</label>
                <select v-model="selectedDivision" required>
                    <option value="" disabled>Select a Division</option>
                    <option v-for="div in divisions" :key="div" :value="div">{{ div }}</option>
                </select>
            </div>

            <div class="filter-group">
                <label>Sort By:</label>
                <select v-model="sortBy">
                    <option value="employeeId">Employee ID</option>
                    <option value="date">Date</option>
                    <option value="netPay">Net Pay</option>
                    <option value="division">Division</option>
                </select>
                <select v-model="sortOrder">
                    <option value="asc">Ascending</option>
                    <option value="desc">Descending</option>
                </select>
            </div>
        </div>

        <!-- Payroll Table -->
        <div v-if="payroll.length > 0" class="payroll-table">
            <table>
                <thead>
                    <tr>
                        <th v-if="isAdmin && (viewMode === 'all' || viewMode === 'employee')">Employee ID</th>
                        <th v-if="isAdmin && (viewMode === 'all' || viewMode === 'employee')">Name</th>
                        <!-- Admin can select jobTitle and division -->
                        <th v-if="isAdmin && viewMode === 'jobTitle'">Job Title</th>
                        <th v-if="isAdmin && viewMode === 'division'">Division</th>
                        <th>Pay Date</th>
                        <th>Earnings</th>
                        <!-- Remove deductions for job title and division selections, -->
                        <th v-if="viewMode !== 'jobTitle' && viewMode !== 'division'">Federal Tax</th>
                        <th v-if="viewMode !== 'jobTitle' && viewMode !== 'division'">Medicare</th>
                        <th v-if="viewMode !== 'jobTitle' && viewMode !== 'division'">Social Security</th>
                        <th v-if="viewMode !== 'jobTitle' && viewMode !== 'division'">State Tax</th>
                        <th v-if="viewMode !== 'jobTitle' && viewMode !== 'division'">401k</th>
                        <th v-if="viewMode !== 'jobTitle' && viewMode !== 'division'">Health Care</th>
                        <th v-if="viewMode !== 'jobTitle' && viewMode !== 'division'">Net Pay</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="p in sortedPayroll" :key="p.id">
                        <td v-if="isAdmin && (viewMode === 'all' || viewMode === 'employee')">{{ p.employee?.empid }}</td>
                        <td v-if="isAdmin && (viewMode === 'all' || viewMode === 'employee')">{{ p.employee?.firstName }} {{ p.employee?.lastName }}</td>
                        <td v-if="isAdmin && viewMode === 'jobTitle'">{{ p.jobTitle }}</td>
                        <td v-if="isAdmin && viewMode === 'division'">{{ p.division }}</td>
                        <!-- Format the date differently -->
                        <td>{{ viewMode === 'jobTitle' || viewMode === 'division' ? p.displayDate : formatDate(p.payDate) }}</td>
                        <td>${{ formatSalary(p.earnings) }}</td>
                        <!-- Remove deductions for job title and division selections, -->
                        <td v-if="viewMode !== 'jobTitle' && viewMode !== 'division'">${{ formatSalary(p.fed_tax) }}</td>
                        <td v-if="viewMode !== 'jobTitle' && viewMode !== 'division'">${{ formatSalary(p.fed_med) }}</td>
                        <td v-if="viewMode !== 'jobTitle' && viewMode !== 'division'">${{ formatSalary(p.fed_SS) }}</td>
                        <td v-if="viewMode !== 'jobTitle' && viewMode !== 'division'">${{ formatSalary(p.state_tax) }}</td>
                        <td v-if="viewMode !== 'jobTitle' && viewMode !== 'division'">${{ formatSalary(p.retire_401k) }}</td>
                        <td v-if="viewMode !== 'jobTitle' && viewMode !== 'division'">${{ formatSalary(p.healthCare) }}</td>
                        <td v-if="viewMode !== 'jobTitle' && viewMode !== 'division'">${{ formatSalary(calculateNetPay(p)) }}</td>
                    </tr>
                </tbody>
            </table>
        </div>
        <p v-else class="no-records">No payroll records found</p>
    </div>
</template>

<script setup>
import { ref, computed, onMounted, watch } from 'vue'
import axios from 'axios'

const isAdmin = localStorage.getItem('role') === 'ADMIN'
const payroll = ref([])
const viewMode = ref('all')
const selectedYear = ref(new Date().getFullYear())
const selectedMonth = ref(new Date().getMonth() + 1)
const employeeId = ref('')
const sortBy = ref('employeeId')
const sortOrder = ref('asc')
const selectedDivision = ref('')

const years = Array.from({ length: 5 }, (_, i) => new Date().getFullYear() - i)
const months = [
    'January', 'February', 'March', 'April', 'May', 'June',
    'July', 'August', 'September', 'October', 'November', 'December'
]

const divisions = ref([])

// Configure axios to send credentials
axios.defaults.withCredentials = true

const fetchPayrollData = async () => {
    try {
        if (isAdmin) {
            if (viewMode.value === 'all') {
                const response = await axios.get('/api/admin/payroll/all')
                payroll.value = response.data
            } else if (viewMode.value === 'jobTitle') {
                const allMonthsData = []
                for (let month = 1; month <= 12; month++) {
                    try {
                        const response = await axios.get('/api/admin/job-titles/monthly-net-pay', {
                            params: { 
                                year: selectedYear.value,
                                month: month
                            }
                        })
                        const monthData = response.data.map(item => ({
                            jobTitle: item.jobTitle || item.title,
                            earnings: item.totalEarnings || item.earnings || 0,
                            payDate: new Date(selectedYear.value, month - 1, 1),
                            fed_tax: 0,
                            fed_med: 0,
                            fed_SS: 0,
                            state_tax: 0,
                            retire_401k: 0,
                            healthCare: 0,
                            displayDate: `${months[month - 1]} ${selectedYear.value}`
                        }))
                        allMonthsData.push(...monthData)
                    } catch (error) {
                        console.error(`Error fetching data for ${months[month - 1]} ${selectedYear.value}:`, error)
                    }
                }
                payroll.value = allMonthsData
            } else if (viewMode.value === 'division') {
                const allMonthsData = []
                for (let month = 1; month <= 12; month++) {
                    try {
                        const response = await axios.get('/api/admin/division/monthly-net-pay', {
                            params: { 
                                year: selectedYear.value,
                                month: month
                            }
                        })
                        const monthData = response.data.map(item => ({
                            division: item.division,
                            earnings: item.earnings || 0,
                            payDate: new Date(selectedYear.value, month - 1, 1),
                            fed_tax: 0,
                            fed_med: 0,
                            fed_SS: 0,
                            state_tax: 0,
                            retire_401k: 0,
                            healthCare: 0,
                            displayDate: `${months[month - 1]} ${selectedYear.value}`
                        }))
                        allMonthsData.push(...monthData)
                    } catch (error) {
                        console.error(`Error fetching data for ${months[month - 1]} ${selectedYear.value}:`, error)
                    }
                }
                // Filter by selected division if one is selected
                if (selectedDivision.value) {
                    payroll.value = allMonthsData.filter(item => item.division === selectedDivision.value)
                } else {
                    payroll.value = allMonthsData
                }
            } else if (viewMode.value === 'employee') {
                const empid = employeeId.value || localStorage.getItem('empid')
                const response = await axios.get(`/api/admin/division/monthly-net-pay/${empid}`)
                payroll.value = response.data
            }
        } else {
            // Employee view - only show their own payroll
            const email = localStorage.getItem('email')
            if (!email) {
                console.error('No email found in localStorage')
                return
            }
            const response = await axios.get(`/api/employee/payroll?email=${email}`)
            payroll.value = response.data
        }
    } catch (error) {
        console.error('Error fetching payroll data:', error)
    }
}

const sortedPayroll = computed(() => {
    if (!isAdmin) return payroll.value
    
    return [...payroll.value].sort((a, b) => {
        let comparison = 0
        if (sortBy.value === 'employeeId') {
            comparison = (a.employee?.empid || 0) - (b.employee?.empid || 0)
        } else if (sortBy.value === 'date') {
            comparison = new Date(a.payDate) - new Date(b.payDate)
        } else if (sortBy.value === 'netPay') {
            comparison = calculateNetPay(a) - calculateNetPay(b)
        } else if (sortBy.value === 'division') {
            const divA = a.division || ''
            const divB = b.division || ''
            comparison = divA.localeCompare(divB)
        }
        return sortOrder.value === 'asc' ? comparison : -comparison
    })
})

const formatDate = (dateString) => {
    if (!dateString) return 'N/A'
    return new Date(dateString).toLocaleDateString()
}

const formatSalary = (amount) => {
    if (!amount) return '0.00'
    return amount.toFixed(2)
}

const calculateNetPay = (record) => {
    return record.earnings -
            (record.fed_tax || 0) -
            (record.state_tax || 0) -
            (record.fed_med || 0) -
            (record.fed_SS || 0) -
            (record.retire_401k || 0) -
            (record.healthCare || 0)
}

//  Fetch divisions for division drop down
const fetchDivisions = async () => {
    try {
        const response = await axios.get('/api/admin/divisions')
        divisions.value = response.data
    } catch (error) {
        console.error('Error fetching divisions:', error)
    }
}

// Watch for changes in filters
watch([viewMode, selectedYear, selectedMonth, employeeId, selectedDivision], () => {
    if (viewMode.value === 'employee' && !employeeId.value) {
        payroll.value = [] // Clear payroll data if no employee ID is provided
        return
    }
    fetchPayrollData()
})

onMounted(() => {
    fetchPayrollData()
    fetchDivisions()
})
</script>

<style scoped>
.payroll-view {
    padding: 20px;
    max-width: 1200px;
    margin: 0 auto;
}

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

.payroll-table {
    overflow-x: auto;
    margin-top: 20px;
}

table {
    width: 100%;
    border-collapse: collapse;
    margin-top: 10px;
}

th, td {
    padding: 10px;
    text-align: left;
    border-bottom: 1px solid #ddd;
}

th {
    background-color: #f5f5f5;
    font-weight: bold;
}

tr:hover {
    background-color: #f9f9f9;
}

.no-records {
    text-align: center;
    padding: 20px;
    color: #666;
}
</style>
