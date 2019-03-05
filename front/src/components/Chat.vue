<template>
    <div></div>
</template>

<script>
    export default {
        name: "Chat",
        data() {
            return {
                matchID: null
            }
        },
        created: function() {
            if(!this.$store.getters.token) {
                this.$router.replace('/');
                return;
            }
            this.matchID = this.$route.params.matchId;
        }
    }
</script>

<style scoped>

</style>
