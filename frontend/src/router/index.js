import { createRouter, createWebHistory } from 'vue-router'
import AdminView from '../views/AdminView.vue'
import EmployeeView from '../views/EmployeeView.vue'
import AuthenticationView from '../views/AuthenticationView.vue'
import PayrollView from '../views/PayrollView.vue'

const routes = [
  { path: '/admin',
    component: AdminView,
    meta: {requiresRole: "ADMIN"}
  },
  { path: '/employee',
    component: EmployeeView,
    meta: {requiresRole: 'EMPLOYEE'}
  },
  { path: '/', component: AuthenticationView },

  { path: '/pay-history',
    component: PayrollView
  }

]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes
})

// Navigation guard
router.beforeEach((to, from, next) => {
  const requiredRole = to.meta.requiresRole
  const currentRole = localStorage.getItem("role")

  if (requiredRole && requiredRole !== currentRole) {
    alert("Access denied.")
    next('/') // redirect to login
  } else {
    next()
  }
})

export default router