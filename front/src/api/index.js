import Vue from 'vue'
import VueAxios from 'vue-axios'
import axios from 'axios'
import store from '@/store'

axios.defaults.baseURL = process.env.NODE_ENV === 'production' ? 'https://dtinder.diasonti.com/api' : 'http://localhost:5000/api';

axios.interceptors.request.use((config) => {
    if (store.getters.token) {
        config.headers.common['Authorization'] = 'Bearer ' + store.getters.token;
    }
    return config
}, (error) => {
    return Promise.reject(error)
});

Vue.use(VueAxios, axios);
