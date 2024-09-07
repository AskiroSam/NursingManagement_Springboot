import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import DepartmentView from '@/views/DepartmentView.vue'
import CustomView from '@/views/CustomView.vue'
import FamilyView from '@/views/FamilyView.vue'
import IndexView from '@/views/IndexView.vue'
import StaffView from '@/views/StaffView.vue'
import TravelView from '@/views/TravelView.vue'
import LoginView from '@/views/LoginView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/login',
      name: 'login',
      component: LoginView
    }, {
      path: '/',
      name: 'home',
      component: HomeView,
      // redirect重定向(父路由即首页)
      redirect: '/index',
      // children表示子路由
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
        }, {
          path: '/travel',
          component: TravelView
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

// //导航守卫
// router.beforeEach((to, from) => {
//   //to - 要访问的位置
//   //from - 起始位置
//   if (to.path == '/login') {
//     return true;
//   } else {
//     //判断sessionStorage中是否有token
//     if (!sessionStorage.getItem('token')) {
//       return "/login";
//     } else {
//       return true;
//     }
//   }
// })

//导航守卫
router.beforeEach((to, from) => {
  //to - 要访问的位置
  //from - 起始位置
  if (to.path == '/login') {
    return true;
  // } else {
  //   //判断sessionStorage中是否有token
  //   if (!sessionStorage.getItem('token')) {
  //     return "/login";
  //   } else {
  //     return true;
  //   }
  }
})


export default router
