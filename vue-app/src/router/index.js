import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import RegisterView from "../views/RegisterView.vue"
import LogInView from "../views/LogInView.vue"
import FavoritesView from "../views/FavoritesView.vue"
import PlayLists from "../views/PlayListsView.vue"

export const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/register',
      name: 'register',
      component: RegisterView
    },
    {
      path: '/logIn',
      name: 'login',
      component: LogInView
    },
    {
      path: '/favorites',
      name: 'favorites',
      component: FavoritesView
    },
    {
      path : '/playlists',
      name : 'playlists',
      component: PlayLists
    }
  ]
})

export default router
