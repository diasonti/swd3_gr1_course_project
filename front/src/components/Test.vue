<template>
	<div class="hello">
		<h2>Counter: {{ counter }}</h2>
		<button @click="counter++">Increment</button>
	</div>
</template>

<script>
    export default {
        name: 'Test',
        props: {
            msg: String
        },
        data: function () {
            return {
                counter: 0
            };
        }
    }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
