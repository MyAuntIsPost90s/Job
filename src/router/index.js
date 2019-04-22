import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/components/home/Home'
import List from '@/components/list/List'
import Detail from '@/components/Detail'
import Company from '@/components/Company'
import StuSettings from '@/components/StuSettings'
import HrSettings from '@/components/HrSettings'
import AboutUs from '@/components/AboutUs'
import SearchList from '@/components/list/SearchList'
import ChatManage from '@/components/chat/ChatManage'
import Chat from '@/components/chat/Chat'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Home
    },
    {
      path: '/list',
      name: 'List',
      component: List
    },
    {
      path: '/detail',
      name: 'Detail',
      component: Detail
    },
    {
      path: '/company',
      name: 'Company',
      component: Company
    },
    {
      path: '/stu-settings',
      name: 'StuSettings',
      component: StuSettings
    },
    {
      path: '/hr-settings',
      name: 'HrSettings',
      component: HrSettings
    },
    {
      path: '/aboutUs',
      name: 'AboutUs',
      component: AboutUs
    },
    {
      path:'/searchList',
      name:'SearchList',
      component: SearchList
    },
    {
      path:'/chatManage',
      name:'ChatManage',
      component: ChatManage
    },
    {
      path:'/chat',
      name:'Chat',
      component: Chat
    }
  ]
})
