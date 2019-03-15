<template>

    <div class="container-fluid">
        <div class="col-3"></div>
        <div class="col-6">
            <div class="card">
                <div class="card-header">What about this one?</div>
                <div class="card-body">
                    <p>Name: {{currentCandidate.name}}</p>
                    <p>Age: {{currentCandidate.age}}</p>
                    <p>Gender: {{currentCandidate.gender}}</p>
                    <p>From: {{currentCandidate.location}}</p>
                    <p>From: {{currentCandidate.description}}</p>
                </div>
                <div class="card-footer">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col">
                                <button @click="like" type="button" class="btn btn-success">Like</button>
                            </div>
                            <div class="col">
                                <button @click="dislike" type="button" class="btn btn-danger">Dislike</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-3"></div>
    </div>
</template>

<script>
    export default {
        name: "Swiper",
        data() {
            return {
                id: "",
                currentCandidate: {
                    name: "name",
                    gender: "gender",
                    age: "age",
                    location: "location",
                    description: "Some quick example text to build on the card title and make up the bulk of the card's content.",
                },
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
                        if (response.data.status === 'ok') {
                            this.next();
                        } else if (response.data.status === 'error') {
                            this.voteError = true;
                        }
                    }).catch((error) => {
                    if (error)
                        this.voteError = true;
                });
            },
            dislike() {
                const formData = new FormData();
                formData.append('id', this.id);
                this.axios.post('/mm/dislike', formData)
                    .then(response => {
                        if (response.data.status === 'ok') {
                            this.next();
                        } else if (response.data.status === 'error') {
                            this.voteError = true;
                        }
                    }).catch((error) => {
                    if (error)
                        this.voteError = true;
                });
            },
            next() {
                this.loadingError = false;
                this.loading = true;
                this.axios.get('/mm/next')
                    .then(response => {
                        if (response.data.status === 'ok') {
                            const profile = response.data.content[0];
                            this.currentCandidate.id = profile.id;
                            this.currentCandidate.name = profile.name;
                            this.currentCandidate.gender = profile.gender;
                            this.currentCandidate.age = profile.age;
                            this.currentCandidate.location = profile.location;
                            this.currentCandidate.description = profile.description;
                        } else if (response.data.status === 'error') {
                            this.loadingError = true;
                        }
                    }).catch((error) => {
                    if (error)
                        this.loadingError = true;
                });
                this.loading = false;
            }
        },
        created: function () {
            if (!this.$store.getters.token) {
                this.$router.replace('/');
                return;
            }
            this.next();
        }
    }
</script>

<style scoped>

</style>