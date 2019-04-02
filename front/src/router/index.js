import Vue from 'vue'
import Router from 'vue-router'

import Anonymous from "@/components/Anonymous";
import Profile from "@/components/Profile";
import Swiper from "@/components/Matchmaking";
import Chat from "@/components/Chat";
import ChatList from "@/components/ChatList";

Vue.use(Router);

const router = new Router({
    routes: [
        {
            path: '/',
            name: 'anonymous',
            component: Anonymous
        },

        {
            path: '/profile',
            name: 'profile',
            component: Profile
        },

        {
            path: '/matchmaking',
            name: 'matchmaking',
            component: Swiper
        },

        {
            path: '/chat',
            name: 'chatList',
            component: ChatList
        },

        {
            path: '/chat/:matchId',
            name: 'chat',
            component: Chat
        }
    ],
    mode: 'history',
    scrollBehavior (to, from, savedPosition) {
        return savedPosition || { x: 0, y: 0 }
    },
    linkActiveClass: 'is-active'
});

export default router;
