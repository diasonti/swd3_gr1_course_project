import Vue from 'vue'
import VueAxios from 'vue-axios'
import axios from 'axios'
import store from '@/store'

axios.defaults.baseURL = 'http://localhost:5000/api';

axios.interceptors.request.use((config) => {
    if (store.getters.token) {
        config.headers.common['Authorization'] = 'Basic ' + store.getters.token;
    }
    return config
}, (error) => {
    return Promise.reject(error)
});

Vue.use(VueAxios, axios);
