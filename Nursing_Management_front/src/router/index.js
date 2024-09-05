import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import DepartmentView from '@/views/DepartmentView.vue'
import CustomView from '@/views/CustomView.vue'
import FamilyView from '@/views/FamilyView.vue'
import IndexView from '@/views/IndexView.vue'
import StaffView from '@/views/StaffView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
      // 重定向到首页
      redirect: '/index',
      children: [
        {
          path: '/dept',
          component: DepartmentView
        }, {
          path: '/cut',
          component: CustomView
        }, {
          path: '/fam',
          component: FamilyView
        }, {
          path: '/index',
          component: IndexView
        }, {
          path: '/staff',
          component: StaffView
        }
      ]
    },{
      path: '/about',
      name: 'about',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/AboutView.vue')
    }
  ]
})

export default router
