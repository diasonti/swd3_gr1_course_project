<template>
	<nav class="navbar navbar-expand-sm navbar-light bg-light">
		<span></span>
		<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarContent" aria-controls="navbarContent" aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>

		<div class="collapse navbar-collapse nav-wrapper" id="navbarContent">
			<ul class="navbar-nav nav-pills nav-fill">
				<router-link tag="li" to="/profile" active-class="active" class="nav-item">
					<a class="nav-link">Profile</a>
				</router-link>
				<router-link tag="li" to="/matchmaking" active-class="active" class="nav-item">
					<a class="nav-link">Matchmaking</a>
				</router-link>
				<router-link tag="li" to="/chat" active-class="active" class="nav-item">
					<a class="nav-link">Chat</a>
				</router-link>
			</ul>
		</div>
	</nav>

</template>

<script>
    export default {
        name: "Navigation",
        props: []
    }
</script>

<style scoped>
	.navbar-nav {
		width: 100%;
	}
	/*.navbar-nav {*/
		/*float:none;*/
		/*display: block;*/
		/*margin:0 auto;*/
		/*text-align: center;*/
	/*}*/

	/*.nav-item {*/
		/*float:none;*/
		/*display: inline-block;*/
		/*width: 180px;*/
	/*}*/
</style>
