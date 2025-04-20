import { createRouter, createWebHistory } from 'vue-router'
import AdminView from '../views/AdminView.vue'
import EmployeeView from '../views/EmployeeView.vue'
import AuthenticationView from '../views/AuthenticationView.vue'

const routes = [
  { path: '/admin', component: AdminView },
  { path: '/employee', component: EmployeeView },
  { path: '/', component: AuthenticationView }
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes
})

export default router