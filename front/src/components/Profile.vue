<template>
    <div class="container-fluid">
        <div class="row">
            <div class="col-sm-6 userProfile">
                <h1>My user profile</h1>

                <div v-if="status.userProfile.load === inProgress" class="spinner-border text-info" role="status">
                    <span class="sr-only">Loading...</span>
                </div>

                <form v-if="status.userProfile.load === success">
                    <div class="form-group">
                        <label for="profileNameInput">My name</label>
                        <input v-model="userProfile.name" type="text" class="form-control" id="profileNameInput"
                               placeholder="My name">
                    </div>
                    <div class="form-group">
                        <label for="profileGenderInput">My gender</label>
                        <select v-model="userProfile.gender" class="form-control" id="profileGenderInput">
                            <option value="MALE">Male</option>
                            <option value="FEMALE">Female</option>
                            <option value="OTHER">Other</option>
                            <option value="NOT_SPECIFIED">I'd rather not tell</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="profileAgeInput">My age</label>
                        <input v-model.number="userProfile.age" type="number" class="form-control" id="profileAgeInput"
                               placeholder="My age">
                    </div>
                    <div class="form-group">
                        <label for="profileLocationInput">Where am I</label>
                        <input v-model="userProfile.location" type="text" class="form-control" id="profileLocationInput"
                               placeholder="My city">
                    </div>
                    <div class="form-group">
                        <label for="profileDescriptionInput">My description text</label>
                        <textarea v-model.trim="userProfile.description" class="form-control"
                                  id="profileDescriptionInput" rows="3"></textarea>
                    </div>
                    <button v-if="status.userProfile.save !== inProgress" @click="saveUserProfile" type="button" class="btn btn-primary">Save my profile</button>
                    <button v-if="status.userProfile.save === inProgress" class="btn btn-primary" type="button" disabled>
                        <span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>
                        Loading...
                    </button>
                </form>
            </div>
            <div class="col-sm-6 mmPreferences">
                <h1>My matchmaking preferences</h1>

                <div v-if="status.mmPref.load === inProgress" class="spinner-border text-info" role="status">
                    <span class="sr-only">Loading...</span>
                </div>

                <form v-if="status.mmPref.load === success">
                    <div class="form-group">
                        <label for="mmPrefGenderInput">I want to find</label>
                        <select v-model="mmPref.gender" class="form-control" id="mmPrefGenderInput">
                            <option value="MALE">Male</option>
                            <option value="FEMALE">Female</option>
                            <option value="ANY">Anyone</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="mmPrefAgeMaxInput">Min age (18+)</label>
                        <input v-model.number="mmPref.ageMin" type="number" class="form-control" id="mmPrefAgeMinInput"
                               placeholder="Not younger than">
                    </div>
                    <div class="form-group">
                        <label for="mmPrefAgeMaxInput">Max age</label>
                        <input v-model.number="mmPref.ageMax" type="number" class="form-control" id="mmPrefAgeMaxInput"
                               placeholder="Not older than">
                    </div>
                    <button v-if="status.mmPref.save !== inProgress" @click="saveMmPreferences" type="button" class="btn btn-primary">Save my preferences</button>
                    <button v-if="status.mmPref.save === inProgress" class="btn btn-primary" type="button" disabled>
                        <span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>
                        Loading...
                    </button>
                </form>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "Profile",
        data() {
            return {
                userProfile: {
                    name: "",
                    gender: "",
                    age: "",
                    location: "",
                    description: ""
                },
                mmPref: {
                    gender: "",
                    ageMin: "",
                    ageMax: ""
                },

                // Possible statuses
                inProgress: 'inProgress',
                success: 'success',
                error: 'error',
                status: {
                    userProfile: {
                        save: "",  //  inProgress, success, error
                        load: "",  //  inProgress, success, error
                    },
                    mmPref: {
                        save: "",  //  inProgress, success, error
                        load: "",  //  inProgress, success, error
                    }
                }
            }
        },
        methods: {
            saveUserProfile() {
                this.status.userProfile.save = this.inProgress;
                const formData = new FormData();
                formData.append('name', this.userProfile.name);
                formData.append('gender', this.userProfile.gender);
                formData.append('age', this.userProfile.age);
                formData.append('location', this.userProfile.location);
                formData.append('description', this.userProfile.description);
                this.axios.post('/profile/info', formData)
                    .then(response => {
                        if (response.data.status === 'ok') {
                            this.status.userProfile.save = this.success;
                        } else if (response.data.status === 'error') {
                            this.status.userProfile.save = this.error;
                        }
                    }).catch((error) => {
                    if (error || !error)
                        this.status.userProfile.save = this.error;
                });
            },
            saveMmPreferences() {
                this.status.mmPref.save = this.inProgress;
                const formData = new FormData();
                formData.append('gender', this.mmPref.gender);
                formData.append('ageMin', this.mmPref.ageMin);
                formData.append('ageMax', this.mmPref.ageMax);
                this.axios.post('/profile/pref', formData)
                    .then(response => {
                        if (response.data.status === 'ok') {
                            this.status.mmPref.save = this.success;
                        } else if (response.data.status === 'error') {
                            this.status.mmPref.save = this.error;
                        }
                    }).catch((error) => {
                    if (error || !error)
                        this.status.mmPref.save = this.error;
                });
            }
        },
        mounted() {
            if (!this.$store.getters.token) {
                this.$router.replace('/');
                return;
            }
            this.status.userProfile.load = this.inProgress;
            this.status.mmPref.load = this.inProgress;

            this.axios.get('/profile/info')
                .then(response => {
                    if (response.data.status === 'ok') {
                        this.userProfile = response.data.content[0];
                        this.status.userProfile.load = this.success;
                    } else if (response.data.status === 'error') {
                        this.status.userProfile.load = this.error;
                    }
                }).catch((error) => {
                if (error || !error)
                    this.status.userProfile.load = this.error;
            });
            this.axios.get('/profile/pref')
                .then(response => {
                    if (response.data.status === 'ok') {
                        this.mmPref = response.data.content[0];
                        this.status.mmPref.load = this.success;
                    } else if (response.data.status === 'error') {
                        this.status.mmPref.load = this.error;
                    }
                }).catch((error) => {
                if (error || !error)
                    this.status.mmPref.load = this.error;
            });
        }
    }
</script>

<style scoped>

</style>
