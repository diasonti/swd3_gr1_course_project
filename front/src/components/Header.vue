<template>
    <nav class="navbar navbar-fixed-top navbar-expand-sm navbar-dark bg-dark">
        <router-link tag="div" to="/" class="navbar-brand nav-justified"><a><img src="../assets/logo.png" height="25" width="25"/>DTinder</a></router-link>
        <button v-if="authenticated" class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#collapsingNavbar">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div v-if="authenticated" class="navbar-collapse collapse justify-content-center" id="collapsingNavbar">
            <ul class="navbar-nav text-center">
                <router-link tag="li" to="/profile" active-class="active" class="nav-item">
                    <a class="nav-link">Profile</a>
                </router-link>
                <router-link tag="li" to="/matchmaking" active-class="active" class="nav-item">
                    <a class="nav-link">Matchmaking</a>
                </router-link>
                <router-link tag="li" to="/chat" active-class="active" class="nav-item">
                    <a class="nav-link">Chat</a>
                </router-link>
                <li class="nav-item bg-warning logout">
                    <a @click="logOut" href="#" class="nav-link">Log out</a>
                </li>
            </ul>
        </div>
    </nav>
</template>

<script>
    export default {
        name: "Header",
        computed: {
            authenticated: function () {
                return this.$store.getters.token;
            },
        },
        methods: {
            logOut() {
                this.$store.dispatch('logOut', {})
                    .then(() => {
                        this.$router.push("/");
                    }).catch(() => {

                })
            }
        }
    }
</script>

<style scoped>
    .nav-item {
        min-width: 100px;
        border-radius: 10px;
    }

    .nav-item:hover {
        background-color: #17a2b8 !important;
        transition: background-color;
        transition-duration: 200ms;
    }

    .logout:hover {
        background-color: #dc3545 !important;
        transition: background-color;
        transition-duration: 200ms;
    }
</style>
