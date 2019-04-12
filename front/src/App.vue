<template>
    <div id="app">
        <Header/>

        <router-view :key="$route.fullPath"></router-view>

        <Footer/>
    </div>
</template>

<script>
    import Header from "@/components/Header";
    import Footer from "@/components/Footer";

    export default {
        components: {Header, Footer}
    }
</script>

<style>
    html {
        position: relative;
        min-height: 100%;
    }

    body {
        margin-bottom: 40px; /* Footer height */
    }

    footer {
        position: absolute;
        bottom: 0;
        width: 100%;
        height: 40px; /* Fixed height of the footer */
    }
</style>
