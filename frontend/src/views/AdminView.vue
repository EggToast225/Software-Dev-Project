<template>
  <div>
    <h1>Admin Dashboard</h1>

    <!-- Add New Employee -->
    <h2>Add New Employee</h2>
    <fieldset>
      <legend>Personal Info</legend>
      <input v-model="form.firstName" placeholder="First Name" />
      <input v-model="form.lastName" placeholder="Last Name" />
      <input v-model="form.dob" placeholder="Date of Birth (YYYY-MM-DD)" />
      <input v-model="form.gender" placeholder="Gender" />
      <input v-model="form.identifiedRace" placeholder="Identified Race" />
      <input v-model="form.ssn" placeholder="SSN" />
    </fieldset>

    <fieldset>
      <legend>Contact Info</legend>
      <input v-model="form.email" placeholder="Email" />
      <input v-model="form.phone" placeholder="Phone" />
    </fieldset>

    <fieldset>
      <legend>Job Info</legend>
      <input v-model="form.hireDate" placeholder="Hire Date (YYYY-MM-DD)" />
      <input v-model="form.salary" placeholder="Salary" />
    </fieldset>

    <fieldset>
      <legend>Address Info</legend>
      <input v-model="form.address.street" placeholder="Street" />
      <input v-model="form.address.cityName" placeholder="City" />
      <input v-model="form.address.stateName" placeholder="State" />
      <input v-model="form.address.zip" placeholder="ZIP Code" />
    </fieldset>

    <button @click="addEmployee">Add Employee</button>

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

    <!-- Search Results -->
    <div v-if="showSearchResults">
      <h2>Search Results</h2>
      <p v-if="filteredEmployees.length === 0">No results found.</p>
      <table v-else>
        <thead>
          <tr>
            <th>ID</th>
            <th>First</th>
            <th>Last</th>
            <th>Email</th>
            <th>Hire Date</th>
            <th>Salary</th>
            <th>SSN</th>
            <th>Gender</th>
            <th>Identified Race</th>
            <th>Date of Birth</th>
            <th>Phone</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="e in filteredEmployees" :key="e.empid">
            <td>{{ e.empid }}</td>
            <td>{{ e.firstName }}</td>
            <td>{{ e.lastName }}</td>
            <td>{{ e.email }}</td>
            <td>{{ formatDate(e.hireDate) }}</td>
            <td>{{ e.salary }}</td>
            <td>{{ e.ssn }}</td>
            <td>{{ e.gender }}</td>
            <td>{{ e.identifiedRace }}</td>
            <td>{{ formatDate(e.dob) }}</td>
            <td>{{ e.phone }}</td>

            <td>
              <button @click="startEdit(e)">Edit</button>
              <button @click="deleteEmployee(e.empid)">Delete</button>
            </td>

          </tr>
        </tbody>
      </table>
    </div>

    <!-- Editing Employee -->
    <div v-if="editingEmployee">
      <h2>Edit Employee</h2>
      <fieldset>
        <legend>Update Info</legend>
        <fieldset>
          <legend>Personal Info</legend>
          <input v-model="editingEmployee.firstName" placeholder="First Name" />
          <input v-model="editingEmployee.lastName" placeholder="Last Name" />
          <input v-model="editingEmployee.dob" placeholder="Date of Birth (YYYY-MM-DD)" />
          <input v-model="editingEmployee.gender" placeholder="Gender" />
          <input v-model="editingEmployee.identifiedRace" placeholder="Identified Race" />

          <input v-model="editingEmployee.ssn" placeholder="SSN" />
        </fieldset>

        <fieldset>
          <legend>Contact Info</legend>
          <input v-model="editingEmployee.email" placeholder="Email" />
          <input v-model="editingEmployee.phone" placeholder="Phone" />
        </fieldset>

        <fieldset>
          <legend>Job Info</legend>
          <input v-model="editingEmployee.hireDate" placeholder="Hire Date (YYYY-MM-DD)" />
          <input v-model="editingEmployee.salary" placeholder="Salary" />
        </fieldset>

        <fieldset>
          <legend>Address Info</legend>
          <input v-model="editingEmployee.address.street" placeholder="Street" />
          <input v-model="editingEmployee.address.cityName" placeholder="City" />
          <input v-model="editingEmployee.address.stateName" placeholder="State" />
          <input v-model="editingEmployee.address.zip" placeholder="Zipcode" />

        </fieldset>

      </fieldset>
      <button @click="submitEdit">Save Changes</button>
      <button @click="cancelEdit">Cancel</button>
    </div>

    <h2> Salary Manager</h2>
    <SalaryManager>
    </SalaryManager>


    <!-- All Employees Table -->
    <h2>All Employees</h2>
    <table>
      <thead>
        <tr>
          <th>ID</th>
          <th>First</th>
          <th>Last</th>
          <th>Email</th>
          <th>Hire Date</th>
          <th>Salary</th>
          <th>SSN</th>
          <th>Gender</th>
          <th>Identified Race</th>
          <th>Date of Birth</th>
          <th>Phone</th>
          <th>Address</th>
          <th>Action</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="e in employees" :key="e.empid">
          <td>{{ e.empid }}</td>
          <td>{{ e.firstName }}</td>
          <td>{{ e.lastName }}</td>
          <td>{{ e.email }}</td>
          <td>{{ formatDate(e.hireDate) }}</td>
          <td>{{ e.salary }}</td>
          <td>{{ e.ssn }}</td>
          <td>{{ e.gender }}</td>
          <td>{{ e.identifiedRace }}</td>
          <td>{{ formatDate(e.dob) }}</td>
          <td>{{ e.phone }}</td>
          <td>{{ formatAddress(e.address) }}</td>
          <td>
            <button @click="startEdit(e)">Edit</button>
            <button @click="deleteEmployee(e.empid)">Delete</button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>


  <button @click="goToPayHistory">View Pay History</button>


</template>



<script setup>


import { ref, onMounted, reactive } from 'vue'
import axios from 'axios'
import { useRouter } from 'vue-router'
import SalaryManager from '../components/SalaryManager.vue'

const router = useRouter()

const authAxios = axios.create({ withCredentials: true })

axios.defaults.withCredentials = true

// Employee form
const form = ref({
  firstName: '',
  lastName: '',
  dob: '',
  gender: '',
  identifiedRace: '',
  ssn: '',
  email: '',
  phone: '',
  hireDate: '',
  salary: '',
  address: {
    street: '',
    zip: '',
    cityName: '',
    stateName: ''
  }
})

const formatAddress = (address) => {
  if (!address) return 'N/A'

  const street = address.street ?? ''
  const zip = address.zip ?? ''
  const city = address.cityName ?? ''
  const state = address.stateName ?? ''

  return `${street}, ${city}, ${state} ${zip}`.trim()
}
const employees = ref([])
const filteredEmployees = ref([])
const showSearchResults = ref(false)

const search = ref({
  firstName: '',
  lastName: '',
  ssn: '',
  empid: ''
})

const formatDate = (dateStr) => {
  if (!dateStr) return '';
  return new Date(dateStr).toLocaleDateString();
}

// Fetch all employees
const fetchEmployees = async () => {
  try {
    const res = await authAxios.get('/api/admin')
    employees.value = res.data
  } catch (err) {
    console.error('Fetch error:', err)
  }
}

// Add new employee
const addEmployee = async () => {
  try {
    // Validate required fields
    if (!form.value.firstName || !form.value.lastName || !form.value.dob ||
      !form.value.gender || !form.value.identifiedRace || !form.value.ssn ||
      !form.value.email || !form.value.phone || !form.value.hireDate ||
      !form.value.salary || !form.value.address.street || !form.value.address.cityName ||
      !form.value.address.stateName || !form.value.address.zip) {
      alert('Please fill in all required fields');
      return;
    }

    // Validate date formats
    const dateRegex = /^\d{4}-\d{2}-\d{2}$/;
    if (!dateRegex.test(form.value.dob) || !dateRegex.test(form.value.hireDate)) {
      alert('Please enter dates in YYYY-MM-DD format');
      return;
    }

    // Validate salary is a number
    if (isNaN(form.value.salary) || form.value.salary <= 0) {
      alert('Please enter a valid salary amount');
      return;
    }

    // Validate SSN format (XXX-XX-XXXX)
    const ssnRegex = /^\d{3}-\d{2}-\d{4}$/;
    if (!ssnRegex.test(form.value.ssn)) {
      alert('Please enter SSN in XXX-XX-XXXX format');
      return;
    }

    // Validate phone number format (XXX-XXX-XXXX)
    const phoneRegex = /^\d{3}-\d{3}-\d{4}$/;
    if (!phoneRegex.test(form.value.phone)) {
      alert('Please enter phone number in XXX-XXX-XXXX format');
      return;
    }

    await authAxios.post('/api/admin', form.value)
    await fetchEmployees()

    // Clears form
    form.value = {
      firstName: '',
      lastName: '',
      dob: '',
      gender: '',
      identifiedRace: '',
      ssn: '',
      email: '',
      phone: '',
      hireDate: '',
      salary: '',
      address: {
        street: '',
        zip: '',
        cityName: '',
        stateName: ''
      }
    }
  } catch (err) {
    console.error('Add error:', err)
    alert('Error adding employee: ' + (err.response?.data || err.message))
  }
}

// Delete employee
const deleteEmployee = async (id) => {
  try {
    await axios.delete(`/api/admin/${id}`)
    await fetchEmployees()
  } catch (err) {
    console.error('Delete error:', err)
  }
}

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
    filteredEmployees.value = res.data
    showSearchResults.value = true
  } catch (err) {
    console.error('Search error:', err)
    filteredEmployees.value = []
    showSearchResults.value = true
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
  filteredEmployees.value = []
  showSearchResults.value = false
}

// Editing Employees
const editingEmployee = ref(null)

const startEdit = (employee) => {
  console.log("Starting edit for: ", employee)
  editingEmployee.value = {
    ...employee,
    address: {
      street: '',
      cityName: '',
      stateName: '',
      zip: '',
      ...employee.address // merge in actual values if they exist
    }
  }
}


const cancelEdit = () => {
  editingEmployee.value = null
}

const submitEdit = async () => {
  try {
    const id = editingEmployee.value.empid
    const response = await axios.patch(`/api/admin/${id}`, editingEmployee.value)
    console.log('Employee updated:', response.data)
    editingEmployee.value = null
    fetchEmployees() // Refresh the list
  } catch (err) {
    console.error('Update failed:', err)
  }
}

const goToPayHistory = () => {
  router.push('/pay-history')
}

onMounted(() => {
  axios.get('api/auth/verify')
    .then(() => {
      const role = localStorage.getItem('role')
      if (!role || role !== 'ADMIN') {
        router.push('/')
        return
      }
    })
  // Fetch employees when the page loads
  fetchEmployees()
})
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

th,
td {
  padding: 0.5rem;
  border: 1px solid #ccc;
  text-align: left;
}
</style>