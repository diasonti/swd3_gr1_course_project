<template>
    <header class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="brand-centered">
            <router-link to="/" class="navbar-brand">DTinder</router-link>
        </div>

        <div class="navbar-collapse">
            <ul class="navbar-nav mr-auto">
            </ul>
            <form class="form-inline my-2 my-lg-0">
                <button v-if="authenticated" @click="logOut" type="button" class="btn btn-primary" id="logOutButton">Log out</button>
            </form>
        </div>
    </header>
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
    header {
        height: 50px;
    }

    .brand-centered {
        display: flex;
        justify-content: center;
        position: absolute;
        width: 100%;
        left: 0;
        top: 0;
        z-index: 1;
    }

    .brand-centered .navbar-brand {
        display: flex;
        align-items: center;
    }

    #logOutButton {
        z-index: 2;
    }

</style>