import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex);

const state = {
    userId: null,
    token: localStorage.getItem('auth_token'),
    error: null
};

const getters = {
    token: state => {
        return state.token;
    },

    error: state => {
        return state.error;
    },

    userId: state => {
        return state.userId;
    }
};

const mutations = {
    LOGIN_SUCCESS (state, token) {
        state.token = token;
        state.error = null;
    },
    SET_USER_ID (state, userId) {
        state.userId = userId;
        state.error = null;
    },
    LOGIN_FAILURE (state, error) {
        state.token = null;
        state.userId = null;
        state.error = error;
    },
    LOGOUT (state) {
        state.token = null;
        state.userId = null;
        state.error = null;
    }
};

const actions = {
    checkCredentials (context, auth) {
        var formData = new FormData();
        formData.append('username', auth.username);
        formData.append('password', auth.password);

        return Vue.axios.post('/auth/token', formData)
            .then(response => {
                if(response.data.validCredentials) {
                    localStorage.setItem('auth_token', response.data.authToken);
                    context.commit('LOGIN_SUCCESS', response.data.authToken, response.data.userId);
                    context.commit('SET_USER_ID', response.data.userId);
                }
            }).catch((error) => {
                context.commit('LOGIN_FAILURE', error);
                throw error
            });
    },
    logOut (context) {
        localStorage.removeItem('auth_token');
        context.commit('LOGOUT');
    }
};

export default new Vuex.Store({
    strict: process.env.NODE_ENV !== 'production',
    state,
    getters,
    mutations,
    actions
});
