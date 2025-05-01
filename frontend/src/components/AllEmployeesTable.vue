
<template>
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
        <th>Action</th>
        </tr>
    </thead>
    <tbody>
        <tr v-for="e in employees" :key="e.empid">
        <td>{{ e.empid }}</td>
        <td>{{ e.firstName }}</td>
        <td>{{ e.lastName }}</td>
        <td>{{ e.email }}</td>
        <td>{{ e.hireDate }}</td>
        <td>{{ e.salary }}</td>
        <td>{{ e.ssn }}</td>
        <td>{{ e.gender }}</td>
        <td>{{ e.identifiedRace }}</td>
        <td>{{ e.dob }}</td>
        <td>{{ e.phone }}</td>
        <td>
            <button @click="$emit('edit',e)">Edit</button>
            <button @click="$emit('delete', e.empid)">Delete</button>
        </td>
        </tr>
    </tbody>
    </table>
</template>

<script setup>
defineProps(['employees'])
defineEmits(['edit', 'delete'])
</script>