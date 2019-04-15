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
    @import url('https://fonts.googleapis.com/css?family=Lato:400,700');
    #app{
        font-family: 'Lato', sans-serif;
        /*color: #505962;*/
        /*overflow: hidden;*/
    }
</style>
