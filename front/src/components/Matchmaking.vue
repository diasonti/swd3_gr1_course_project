<template>
    <div class="card" style="width: 18rem;">
        <div class="card-body">
            <h5 class="card-title">{{name}}</h5>
            <p class="card-text">Age: {{age}}</p>
            <p class="card-text">Gender: {{gender}}</p>
            <p class="card-text">From: {{location}}</p>
            <p class="card-text">{{description}}</p>
            <button @click="like" type="button" class="btn btn-success">Like</button>
            <button @click="dislike" type="button" class="btn btn-danger">Dislike</button>
        </div>
    </div>
</template>

<script>
    export default {
        name: "Swiper",
        data() {
            return {
                id: "",
                name: "name",
                gender: "gender",
                age: "age",
                location: "location",
                description: "Some quick example text to build on the card title and make up the bulk of the card's content.",
                loading: true,
                loadingError: false,
                voteError: false
            }
        },
        methods: {
            like() {
                const formData = new FormData();
                formData.append('id', this.id);
                this.axios.post('/mm/like', formData)
                    .then(response => {
                        if(response.data.status === 'ok') {
                            this.next();
                        } else if(response.data.status === 'error') {
                            this.voteError = true;
                        }
                    }).catch((error) => {
                    if(error)
                        this.voteError = true;
                });
            },
            dislike() {
                const formData = new FormData();
                formData.append('id', this.id);
                this.axios.post('/mm/dislike', formData)
                    .then(response => {
                        if(response.data.status === 'ok') {
                            this.next();
                        } else if(response.data.status === 'error') {
                            this.voteError = true;
                        }
                    }).catch((error) => {
                    if(error)
                        this.voteError = true;
                });
            },
            next() {
                this.loadingError = false;
                this.loading = true;
                this.axios.get('/mm/next')
                    .then(response => {
                        if(response.data.status === 'ok') {
                            const profile = response.data.content[0];
                            this.id = profile.id;
                            this.name = profile.name;
                            this.gender = profile.gender;
                            this.age = profile.age;
                            this.location = profile.location;
                            this.description = profile.description;
                        } else if(response.data.status === 'error') {
                            this.loadingError = true;
                        }
                    }).catch((error) => {
                    if(error)
                        this.loadingError = true;
                });
                this.loading = false;
            }
        },
        created: function() {
            if(!this.$store.getters.token) {
                this.$router.replace('/');
                return;
            }
            this.next();
        }
    }
</script>

<style scoped>

</style>