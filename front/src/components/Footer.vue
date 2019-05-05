<template>
    <div>
        <div class="blankSpace"></div>
        <footer class="text-center footer fixed-bottom" v-if="authenticated">
            © 2019 Group 1. All rights reserved
        </footer>
    </div>

</template>

<script>
    export default {
        name: "Footer",
        computed: {
            authenticated: function () {
                return this.$store.getters.token;
            },
        }
    }
</script>

<style scoped>
    footer {
        height: 25px;
        background-color: black;
        color: white;
        bottom: 0;
    }

    .blankSpace{
        height: 50px;
    }
</style>
