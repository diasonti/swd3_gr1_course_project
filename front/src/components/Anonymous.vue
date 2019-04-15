<template>

    <div>

        <div>


            <div class="landing">
                <div class="home-wrap">
                    <div class="home-inner"></div>
                </div>
            </div>

            <div class="caption text-center">
                <h1>Welcome to DTinder</h1>
                <h3>Meet your Soulmate here</h3>
                <a class="btn btn-outline-light btn-lg" data-toggle="modal" data-target="#exampleModalLong">Get
                    Started</a>
            </div>


        </div>

        <div class="modal fade" id="exampleModalLong" tabindex="-1" role="dialog"
             aria-labelledby="exampleModalLongTitle"
             aria-hidden="true" data-backdrop="false">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLongTitle">Welcome to BestJob</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <div class="text-center" v-if="currentTab === 'login'">
                            <div>
                                <h2>Sign in</h2>
                                <div class="alert alert-danger" role="alert"
                                     v-if="loginErrors.some(e => e === 'bad.credentials')">
                                    Wrong username and/or password.
                                </div>
                                <form>
                                    <div class="form-group">
                                        <label for="logInUsernameInput">Username</label>
                                        <input v-model="logInUsername" type="text" class="form-control textbox"
                                               id="logInUsernameInput"
                                               placeholder="Enter username" @keyup.enter="submitLogIn">
                                    </div>
                                    <div class="form-group">
                                        <label for="logInPasswordInput">Password</label>
                                        <input v-model="logInPassword" type="password" class="form-control textbox"
                                               id="logInPasswordInput"
                                               placeholder="Enter password" @keyup.enter="submitLogIn">
                                    </div>
                                    <div class="form-group form-check">
                                        <input v-model="logInRememberMe" type="checkbox" class="form-check-input"
                                               id="logInRememberMe">
                                        <label class="form-check-label" for="logInRememberMe">Keep me signed in</label>
                                    </div>
                                    <button v-if="!loginInProgress" @click="submitLogIn" type="button"
                                            class="btn btn-primary">Sign
                                        in
                                    </button>
                                    <button v-if="loginInProgress" class="btn btn-primary" type="button" disabled>
                                        <span class="spinner-border spinner-border-sm" role="status"
                                              aria-hidden="true"></span>
                                        Loading...
                                    </button>
                                    <hr/>
                                    <a @click="currentTab = 'register'" href="#">I don't have an account</a>
                                </form>
                            </div>
                        </div>

                        <div class="text-center" v-if="currentTab === 'register'">
                            <div>
                                <h2>Sign up</h2>
                                <div class="alert alert-success" role="alert" v-if="registerSuccess">
                                    Successfully registered. You can <a href="#"
                                                                        @click="currentTab = 'login'; registerSuccess = false;">sign
                                    in</a> now.
                                </div>
                                <div class="alert alert-danger" role="alert" v-if="registerFailed">
                                    Something went wrong, please try again.
                                </div>
                                <form id="registerForm">
                                    <div class="form-group">
                                        <label for="registerUsernameInput">Username</label>
                                        <input v-model="registerUsername" type="text"
                                               class="form-control no-autocomplete textbox"
                                               id="registerUsernameInput"
                                               :class="{'is-invalid': registrationUsernameError}"
                                               placeholder="Enter username" @keyup.enter="submitRegistration" readonly>
                                        <div class="invalid-feedback"
                                             v-if="registerErrors.some(e => e === 'username.length.error')">The
                                            username should be at least 1 symbol long
                                        </div>
                                        <div class="invalid-feedback"
                                             v-if="registerErrors.some(e => e === 'username.is.taken')">The
                                            username is already taken, try another one
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="registerPasswordInput1">Password</label>
                                        <input v-model="registerPassword1" type="password"
                                               class="form-control no-autocomplete textbox"
                                               id="registerPasswordInput1"
                                               :class="{'is-invalid': registrationPassword1Error}"
                                               placeholder="Enter password" @keyup.enter="submitRegistration" readonly>
                                        <div class="invalid-feedback"
                                             v-if="registerErrors.some(e => e === 'password.too.weak')">The
                                            password should be at least 6 symbols long
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="registerPasswordInput2">Confirm password</label>
                                        <input v-model="registerPassword2" type="password"
                                               class="form-control no-autocomplete textbox"
                                               id="registerPasswordInput2"
                                               :class="{'is-invalid': registrationPassword2Error}"
                                               placeholder="Enter password again" @keyup.enter="submitRegistration"
                                               readonly>
                                        <div class="invalid-feedback"
                                             v-if="registerErrors.some(e => e === 'passwords.not.match')">
                                            Passwords are not same
                                        </div>
                                    </div>
                                    <button v-if="!registerInProgress" @click="submitRegistration" type="button"
                                            class="btn btn-primary">Sign up
                                    </button>
                                    <button v-if="registerInProgress" class="btn btn-primary" type="button" disabled>
                                        <span class="spinner-border spinner-border-sm" role="status"
                                              aria-hidden="true"></span>
                                        Loading...
                                    </button>
                                    <hr/>
                                    <a @click="currentTab = 'login'" href="#">I already have an account</a>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
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
                logInRememberMe: false,

                loginInProgress: false,
                loginErrors: [],


                registerUsername: '',
                registerPassword1: '',
                registerPassword2: '',

                registerSuccess: false,
                registerInProgress: false,
                registerFailed: false,

                registerErrors: [],

                image: "../assets/bg1.jpg"
            }
        },
        watch: {
            currentTab: function (tab) {
                if (tab != null) {
                    this.logInUsername = '';
                    this.logInPassword = '';
                    this.logInRememberMe = false;
                    this.loginErrors = [];

                    this.registerUsername = '';
                    this.registerPassword1 = '';
                    this.registerPassword2 = '';
                    this.registerSuccess = false;
                    this.registerFailed = false;
                    this.registerErrors = [];
                }
            }
        },
        computed: {
            registrationUsernameError() {
                const errors = ['username.length.error', 'username.is.taken'];
                return this.registerErrors.some(r => errors.includes(r));
            },
            registrationPassword1Error() {
                const errors = ['password.too.weak'];
                return this.registerErrors.some(r => errors.includes(r));
            },
            registrationPassword2Error() {
                const errors = ['passwords.not.match'];
                return this.registerErrors.some(r => errors.includes(r));
            }
        },
        methods: {
            submitLogIn() {
                this.loginInProgress = true;
                this.$store.dispatch('checkCredentials', {
                    username: this.logInUsername,
                    password: this.logInPassword,
                    rememberMe: this.logInRememberMe,
                }).then(() => {
                    this.loginInProgress = false;
                    this.$router.push("profile");
                }).catch((error) => {
                    this.loginInProgress = false;
                    if (error === 'bad.credentials')
                        this.loginErrors = [error];
                })
            },
            submitRegistration() {
                this.registerInProgress = true;
                this.registerSuccess = false;
                const formData = new FormData();
                formData.append('username', this.registerUsername);
                formData.append('password', this.registerPassword1);
                formData.append('passwordVerification', this.registerPassword2);
                this.axios.post('/registration/submit', formData)
                    .then(response => {
                        this.registerInProgress = false;
                        if (response.data.status === 'ok') {
                            this.registerSuccess = true;
                            this.registerErrors = [];
                            $('#registerForm')[0].reset();
                            this.registerFailed = false;
                        } else if (response.data.status === 'error') {
                            this.registerErrors = response.data.content[0];
                            this.registerFailed = false;
                        }
                    }).catch((error) => {
                    this.registerInProgress = false;
                    if (error)
                        this.registerFailed = true;
                });
            }
        },
        created() {
            if (this.$store.getters.token) {
                this.$router.replace('/profile');
            }
        },
        updated() {
            this.$nextTick(function () {
                $('input.no-autocomplete').removeAttr('readonly');
            })
        }
    }
</script>

<style scoped>


    .modal {
        background: rgba(000, 000, 000, 0.8);
        min-height: 1000000px;
    }

    /* css for the fixed background image*/
    .landing {
        position: relative;
        width: 100%;
        height: 100vh;
        display: table;
        z-index: -1;
    }

    .home-wrap {
        clip: rect(0, auto, auto, 0);
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
    }

    .home-inner {
        position: fixed;
        display: table;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background-size: cover;
        background-position: center center;
        -webkit-transform: translateZ(0);
        transform: translateZ(0);
        will-change: transform;
    }

    /* css for caption*/

    .home-inner {
        background-image: url(../assets/adult-astronomy.jpg);
        /* background-size: cover;
        opacity: 0.7; */
    }

    .caption {
        width: 100%;
        max-width: 100%;
        position: absolute;
        top: 38%;
        z-index: 1;
        color: white;
    }

    .caption h1 {
        font-size: 3.8rem;
        font-weight: 700;
        letter-spacing: 0.3rem;
        text-shadow: .1rem .1rem .8rem black;
        padding-bottom: 1rem;
    }

    .caption h3 {
        font-size: 2rem;
        text-shadow: .1rem .1rem .5rem black;
        padding-bottom: 1.6rem;
    }

    .caption .btn-lg {
        text-transform: uppercase;
        border-width: medium;
        border-radius: 0;
        padding: .6rem 1.3rem;
        font-size: 1.1rem;
    }

    /*button {*/
    /*background-color: white;*/
    /*color: black;*/
    /*border: 2px solid black;*/
    /*font-weight: bold;*/
    /*padding: 10px 20px;*/
    /*}*/

    /*button:hover {*/
    /*background-color: black;*/
    /*color: white;*/
    /*}*/

    .textbox {
        min-width: 300px;
        border: black 2px solid;
    }

    .centerDiv {
        display: flex;
        justify-content: center;
        align-items: center;
    }

</style>
