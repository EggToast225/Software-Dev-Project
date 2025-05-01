<template>
<div v-if="editingEmployee">
    <h2>Edit Employee</h2>
    <fieldset>
        <legend>Update Info</legend>
        <input v-model="editingEmployee.firstName" placeholder="First Name" />
        <input v-model="editingEmployee.lastName" placeholder="Last Name" />
        <input v-model="editingEmployee.email" placeholder="Email" />
        <input v-model="editingEmployee.phone" placeholder="Phone" />
        <input v-model="editingEmployee.salary" placeholder="Salary" />
        <input v-model="editingEmployee.hireDate" placeholder="Hire Date (YYYY-MM-DD)" />
        <input v-model="editingEmployee.gender" placeholder="Gender" />
        <input v-model="editingEmployee.identifiedRace" placeholder="Identified Race" />
        <input v-model="editingEmployee.dob" placeholder="Date of Birth (YYYY-MM-DD)" />
        <input v-model="editingEmployee.ssn" placeholder="SSN" />
    </fieldset>
    <button @click="submitEdit">Save Changes</button>
    <button @click="cancelEdit">Cancel</button>
</div>
</template>

<script setup>
import { ref, watch } from 'vue'
import axios from 'axios'

const props = defineProps(['modelValue'])
const emit = defineEmits(['update:modelValue', 'saved'])

const editingEmployee = ref({})

watch(() => props.modelValue, (val) => {
    employee.value = { ...val }
})

const submitEdit = async () => {
try {
    const id = editingEmployee.value.empid
    const response = await axios.patch(`/api/admin/${id}`, editingEmployee.value)
    console.log('Employee updated:', response.data)
    emit('saved')
    emit('update:modelValue',null)
    } catch (err) {
    console.error('Update failed:', err)
    }
}
</script>