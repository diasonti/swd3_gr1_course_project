<template>

    <div class="container">
        <!--<div class="row">-->
            <!--<div class="col-sm-3"></div>-->
            <!--<div class="col-sm-6">-->
                <div class="matchBox">


                    <div class="alert alert-danger" role="alert" v-if="status === error && errorCode === null">
                        Something's not right. Try reloading the page.
                    </div>

                    <div class="alert alert-warning" role="alert" v-if="errorCode === 'profile.not.filled'">
                        You must fill your
                        <router-link to="/profile" class="alert-link">profile</router-link>
                        first.
                    </div>

                    <div class="alert alert-warning" role="alert" v-if="errorCode === 'no.candidate.available'">
                        No suitable candidates found. Try again later or change your
                        <router-link to="/profile" class="alert-link">search preferences</router-link>
                        .
                    </div>


                    <div class="card" v-if="status === loading">
                        <div class="card-header text-center">Loading next candidate...</div>
                        <div class="card-body">
                            <div class="text-center">
                                <div class="spinner-border" role="status">
                                    <span class="sr-only">Loading...</span>
                                </div>
                            </div>
                        </div>
                        <div class="card-footer">
                            <div class="row">
                                <div class="col-sm-1"></div>
                                <button type="button" class="col-sm btn btn-success" disabled>
                                    <div class="spinner-border spinner-border-sm" role="status">
                                        <span class="sr-only">Loading...</span>
                                    </div>
                                </button>
                                <div class="col-sm-1">&nbsp;</div>
                                <button type="button" class="col-sm btn btn-danger" disabled>
                                    <div class="spinner-border spinner-border-sm" role="status">
                                        <span class="sr-only">Loading...</span>
                                    </div>
                                </button>
                                <div class="col-sm-1"></div>
                            </div>
                        </div>
                    </div>

                    <div class="card" v-if="status === loaded">
                        <div class="card-header text-center">What about this one?</div>
                        <div class="card-body">
                            <p>Name: {{currentCandidate.name}}</p>
                            <p>Age: {{currentCandidate.age}}</p>
                            <p>Gender: {{currentCandidate.gender}}</p>
                            <p>From: {{currentCandidate.location}}</p>
                            <p>Text: {{currentCandidate.description}}</p>
                        </div>
                        <div class="card-footer">
                            <div class="row">
                                <div class="col-sm-1"></div>
                                <button @click="dislike" type="button" class="col-sm btn btn-danger">Dislike</button>
                                <div class="col-sm-1">&nbsp;</div>
                                <button @click="like" type="button" class="col-sm btn btn-success">Like</button>
                                <div class="col-sm-1"></div>
                            </div>
                        </div>
                    </div>
                </div>
            <!--</div>-->
        <!--</div>-->
        <!--<div class="col-sm-3"></div>-->
    </div>
</template>

<script>
    export default {
        name: "Swiper",
        data() {
            return {
                currentCandidate: {
                    id: "",
                    name: "name",
                    gender: "gender",
                    age: "age",
                    location: "location",
                    description: "Some quick example text to build on the card title and make up the bulk of the card's content.",
                },

                loading: "loading",
                loaded: "loaded",
                error: "error",

                status: "loading",
                errorCode: null
            }
        },
        methods: {
            like() {
                this.status = this.loading;
                const formData = new FormData();
                formData.append('id', this.currentCandidate.id);
                this.axios.post('/mm/like', formData)
                    .then(response => {
                        if (response.data.status === 'ok') {
                            this.next();
                        } else if (response.data.status === 'error') {
                            this.status = this.error;
                            this.errorCode = response.data.content[0];
                        }
                    }).catch((error) => {
                    this.status = this.error;
                    this.errorCode = null;
                });
            },
            dislike() {
                this.status = this.loading;
                const formData = new FormData();
                formData.append('id', this.currentCandidate.id);
                this.axios.post('/mm/dislike', formData)
                    .then(response => {
                        if (response.data.status === 'ok') {
                            this.next();
                        } else if (response.data.status === 'error') {
                            this.status = this.error;
                            this.errorCode = response.data.content[0];
                        }
                    }).catch((error) => {
                    this.status = this.error;
                    this.errorCode = null;
                });
            },
            next() {
                this.status = this.loading;
                this.errorCode = null;
                this.axios.get('/mm/next')
                    .then(response => {
                        if (response.data.status === 'ok') {
                            this.currentCandidate = response.data.content[0];
                            this.status = this.loaded;
                        } else if (response.data.status === 'error') {
                            this.status = this.error;
                            this.errorCode = response.data.content[0];
                            this.currentCandidate = null;
                        }
                    }).catch((error) => {
                    this.status = this.error;
                    this.errorCode = null;
                    this.currentCandidate = null;
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

    .matchBox{
        width: 600px;
        height: 400px;
        /*border: 1px solid black;*/
        margin: auto;
        padding: 5px;
        left: 0;
        right: 0;
        top: 0;
        bottom: 0;
        position: absolute;
    }
</style>
