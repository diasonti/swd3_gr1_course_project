<template>

    <div class="container-fluid">
        <div class="row">
            <div class="col"></div>


            <div class="col" v-if="currentTab === 'login'">
                <h2>Sign in</h2>
                <form>
                    <div class="form-group">
                        <label for="logInUsernameInput">Username</label>
                        <input v-model="logInUsername" type="text" class="form-control" id="logInUsernameInput"
                               placeholder="Enter username" @keyup.enter="submitLogIn">
                    </div>
                    <div class="form-group">
                        <label for="logInPasswordInput">Password</label>
                        <input v-model="logInPassword" type="password" class="form-control" id="logInPasswordInput"
                               placeholder="Enter password" @keyup.enter="submitLogIn">
                    </div>
                    <button @click="submitLogIn" type="button" class="btn btn-primary">Sign in</button>
                    <br/>
                    <a @click="currentTab = 'register'" href="#">Don't have an account yet?</a>
                </form>
            </div>


            <div class="col" v-if="currentTab === 'register'">
                <h2>Sign up</h2>
                <form>
                    <div class="form-group">
                        <label for="registerUsernameInput">Username</label>
                        <input v-model="registerUsername" type="text" class="form-control no-autocomplete" id="registerUsernameInput"
                               placeholder="Enter username" @keyup.enter="submitRegistration" readonly>
                    </div>
                    <div class="form-group">
                        <label for="registerPasswordInput1">Password</label>
                        <input v-model="registerPassword1" type="password" class="form-control no-autocomplete" id="registerPasswordInput1"
                               placeholder="Enter password" @keyup.enter="submitRegistration" readonly>
                    </div>
                    <div class="form-group">
                        <label for="registerPasswordInput2">Confirm password</label>
                        <input v-model="registerPassword2" type="password" class="form-control no-autocomplete" id="registerPasswordInput2"
                               placeholder="Enter password again" @keyup.enter="submitRegistration" readonly>
                    </div>
                    <button @click="submitRegistration" type="button" class="btn btn-primary">Sign up</button>
                    <br/>
                    <a @click="currentTab = 'login'" href="#">Already have an account?</a>
                </form>
            </div>


            <div class="col"></div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "Anonymous",
        props: [],
        data() {
            return {
                currentTab: 'login',
                logInUsername: '',
                logInPassword: '',
                logInFailed: false,
                registerUsername: '',
                registerPassword1: '',
                registerPassword2: '',
                registerSuccess: false,
                registerFailed: false
            }
        },
        methods: {
            submitLogIn() {
                this.$store.dispatch('checkCredentials', {
                    username: this.logInUsername,
                    password: this.logInPassword,
                }).then(() => {
                    this.$router.push("profile");
                }).catch(() => {
                    this.logInFailed = true
                })
            },
            submitRegistration() {
                this.registerSuccess = false;
                this.registerFailed = false;
                const formData = new FormData();
                formData.append('username', this.registerUsername);
                formData.append('password', this.registerPassword1);
                formData.append('passwordVerification', this.registerPassword2);
                this.axios.post('/registration/submit', formData)
                    .then(response => {
                        if(response.data.status === 'ok') {
                            this.registerSuccess = true;
                        } else if(response.data.status === 'error') {
                            this.registerFailed = true;
                        }
                    }).catch((error) => {
                        if(error)
                            this.registerFailed = true;
                });
            }
        },
        created: function() {
            if(this.$store.getters.token) {
                this.$router.replace('/profile');
            }

            // Workaround to prevent form autocomplete
            setTimeout(function () {
                const inputs = $(".no-autocomplete");
                inputs.attr('readonly', false);
            }, 100);
        }
    }
</script>

<style scoped>

</style>