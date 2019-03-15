<template>
    <div>
        <div class="userProfile">
            <h1>My user profile</h1>
            <form>
                <div class="form-group">
                    <label for="profileNameInput">My name</label>
                    <input v-model="userProfileName" type="text" class="form-control" id="profileNameInput" placeholder="My name">
                </div>
                <div class="form-group">
                    <label for="profileGenderInput">My gender</label>
                    <select v-model="userProfileGender" class="form-control" id="profileGenderInput">
                        <option value="MALE">Male</option>
                        <option value="FEMALE">Female</option>
                        <option value="OTHER">Other</option>
                        <option value="NOT_SPECIFIED">I'd rather not tell</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="profileAgeInput">My age</label>
                    <input v-model.number="userProfileAge" type="number" class="form-control" id="profileAgeInput" placeholder="My age">
                </div>
                <div class="form-group">
                    <label for="profileLocationInput">Where am I</label>
                    <input v-model="userProfileLocation" type="text" class="form-control" id="profileLocationInput" placeholder="My city">
                </div>
                <div class="form-group">
                    <label for="profileDescriptionInput">About me</label>
                    <textarea v-model.trim="userProfileDescription" class="form-control" id="profileDescriptionInput" rows="3"></textarea>
                </div>
                <button @click="saveUserProfile" type="button" class="btn btn-primary">Save my profile</button>
            </form>
        </div>
        <div class="mmPreferences">
            <h1>My matchmaking preferences</h1>
            <form>
                <div class="form-group">
                    <label for="mmPrefGenderInput">I want to find</label>
                    <select v-model="mmPrefGender" class="form-control" id="mmPrefGenderInput">
                        <option value="MALE">Male</option>
                        <option value="FEMALE">Female</option>
                        <option value="ANY">Any</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="mmPrefAgeMinInput">Min age</label>
                    <input v-model.number="mmPrefAgeMin" type="number" class="form-control" id="mmPrefAgeMinInput" placeholder="Not younger than">
                </div>
                <div class="form-group">
                    <label for="mmPrefAgeMaxInput">Max age</label>
                    <input v-model.number="mmPrefAgeMax" type="number" class="form-control" id="mmPrefAgeMaxInput" placeholder="Not older than">
                </div>
                <button @click="saveMmPreferences" type="button" class="btn btn-primary">Save my preferences</button>
            </form>
        </div>
    </div>
</template>

<script>
    export default {
        name: "Profile",
        data() {
            return {
                userProfileName: "",
                userProfileGender: "",
                userProfileAge: "",
                userProfileLocation: "",
                userProfileDescription: "",
                userProfileSaveSuccess: false,
                userProfileSaveError: false,
                userProfileLoadError: false,
                mmPrefGender: "",
                mmPrefAgeMin: "",
                mmPrefAgeMax: "",
                mmPrefSaveSuccess: false,
                mmPrefSaveError: false,
                mmPrefLoadError: false,
            }
        },
        methods: {
            saveUserProfile() {
                this.userProfileSaveSuccess = false;
                this.userProfileSaveError = false;
                const formData = new FormData();
                formData.append('name', this.userProfileName);
                formData.append('gender', this.userProfileGender);
                formData.append('age', this.userProfileAge);
                formData.append('location', this.userProfileLocation);
                formData.append('description', this.userProfileDescription);
                this.axios.post('/profile/info', formData)
                    .then(response => {
                        if(response.data.status === 'ok') {
                            this.userProfileSaveSuccess = true;
                        } else if(response.data.status === 'error') {
                            this.userProfileSaveError = true;
                        }
                    }).catch((error) => {
                    if(error)
                        this.userProfileSaveError = true;
                });
            },
            saveMmPreferences() {
                this.mmPrefSaveSuccess = false;
                this.mmPrefSaveError = false;
                const formData = new FormData();
                formData.append('genderPreference', this.mmPrefGender);
                formData.append('agePreferenceMin', this.mmPrefAgeMin);
                formData.append('agePreferenceMax', this.mmPrefAgeMax);
                this.axios.post('/profile/pref', formData)
                    .then(response => {
                        if(response.data.status === 'ok') {
                            this.mmPrefSaveSuccess = true;
                        } else if(response.data.status === 'error') {
                            this.mmPrefSaveError = true;
                        }
                    }).catch((error) => {
                    if(error)
                        this.mmPrefSaveError = true;
                });
            }
        },
        created: function() {
            if(!this.$store.getters.token) {
                this.$router.replace('/');
                return;
            }
            this.userProfileLoadError = false;
            this.axios.get('/profile/info')
                .then(response => {
                    if(response.data.status === 'ok') {
                        this.userProfileLoadError = false;
                        const profile = response.data.content[0];
                        this.userProfileName = profile.name;
                        this.userProfileGender = profile.gender;
                        this.userProfileAge = profile.age;
                        this.userProfileLocation = profile.location;
                        this.userProfileDescription = profile.description;
                    } else if(response.data.status === 'error') {
                        this.userProfileLoadError = true;
                    }
                }).catch((error) => {
                if(error)
                    this.userProfileLoadError = true;
            });

            this.mmPrefLoadError = false;
            this.axios.get('/profile/pref')
                .then(response => {
                    if(response.data.status === 'ok') {
                        this.mmPrefLoadError = false;
                        const profile = response.data.content[0];
                        this.mmPrefGender = profile.genderPreference;
                        this.mmPrefAgeMin = profile.agePreferenceMin;
                        this.mmPrefAgeMax = profile.agePreferenceMax;
                    } else if(response.data.status === 'error') {
                        this.mmPrefLoadError = true;
                    }
                }).catch((error) => {
                if(error)
                    this.mmPrefLoadError = true;
            });
        }
    }
</script>

<style scoped>

</style>