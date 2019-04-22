<template>
    <nav class="navbar navbar-fixed-top navbar-expand-sm navbar-dark bg-dark fixed-top" v-bind:class="{opacityClass:!authenticated}">
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
                <li class="nav-item logout">
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

    /*.opacityClass{*/
        /*opacity: 0.6;*/
    /*}*/
    /*.fixed-top  {*/
        /*-webkit-backface-visibility: hidden;*/
    /*}*/

    .navbar{
        text-transform: uppercase;
        font-weight: 700;
        font-size: .9rem;
        letter-spacing: .1rem;
        background: rgb(0,0,0)!important;
        /*background: rgba(0,0,0,0.6)!important;*/

    }

    .navbar-brand img{
        height: 2rem;
    }

    .navbar li{
        padding-right: .9rem;
    }

    .navbar-dark .navbar-nav .nav-link{
        color:white;
        padding-top: .8rem;
    }

    .navbar-dark .navbar-nav .nav-link:hover{
        color:#1ebba3;
    }

    .logout a:hover {
        color: red !important;
        transition: color;
        transition-duration: 200ms;
    }


</style>
