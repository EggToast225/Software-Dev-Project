<template>
  <div class="app">
    <h1>Employee Management</h1>
    <EmployeeTable />
  </div>
</template>

<script setup>
import EmployeeTable from './components/EmployeeTable.vue'
</script>

<style>
.app {
  font-family: Arial, sans-serif;
  padding: 2rem;
}
</style>