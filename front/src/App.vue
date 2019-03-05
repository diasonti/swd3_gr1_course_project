<template>
    <div id="app">
        <Header />
        <Navigation v-if="authenticated" />
        <router-view :key="$route.fullPath"></router-view>
        <Footer />
    </div>
</template>

<style>

</style>
<script>
    import Header from "@/components/Header";
    import Footer from "@/components/Footer";
    import Navigation from "@/components/Navigation";

    export default {
        components: {Navigation, Footer, Header},
        computed: {
            authenticated: function () {
                return this.$store.getters.token;
            },
        }
    }
</script>