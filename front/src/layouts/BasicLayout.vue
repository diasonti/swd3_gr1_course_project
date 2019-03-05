<template>
	<div>
		<Header />
		<Navigation />

		<Footer />
	</div>
</template>

<script>
    import Header from '@/components/Header'
    import Navigation from "@/components/Navigation";
    import Footer from "@/components/Footer";

    export default {
        name: "BasicLayout",
        components: {Footer, Navigation, Header}
    }
</script>

<style scoped>

</style>