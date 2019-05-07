<template>
    <div class="messaging">
        <div class="inbox_msg">
            <div class="inbox_people">
                <div class="headind_srch">
                    <div class="recent_heading">
                        <h4>Chats list</h4>
                    </div>
                </div>
                <div class="inbox_chat">

                    <div class="chat_list" v-for="chat in chatList" :key="chat.id" @click="activeChat = chat">
                        <div class="chat_people">
                            <div class="chat_ib">
                                <h5>{{chat.matchedUser.name}} <span class="chat_date">{{chat.matchedAt}}</span></h5>
                                <p><span v-if="chat.lastMessage != null">{{chat.lastMessage.text}}</span></p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="mesgs">
                <div class="msg_history">
                    <div v-for="msg in activeChatMessages" :key="msg.id"
                         :class="{ 'outgoing_msg': msg.mine, 'incoming_msg': !msg.mine }">
                        <div :class="{ 'sent_msg': msg.mine, 'received_msg': !msg.mine }">
                            <div :class="{ 'received_withd_msg': !msg.mine }">
                                <p>{{msg.text}}</p>
                                <span class="time_date">{{msg.sentAt}}</span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="type_msg" v-if="activeChat != null">
                    <div class="input_msg_write">
                        <input v-model.trim="newMessageText" type="text" class="write_msg"
                               placeholder="Type a message" v-on:keyup.enter="sendMsg"/>
                        <button @click="sendMsg" class="msg_send_btn" type="button" :disabled="newMessageTextClear">
                            <i class="fa fa-paper-plane-o" aria-hidden="true"></i>
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "ChatList",
        data() {
            return {
                chatList: [],
                activeChat: null,
                activeChatMessages: [],

                loading: 'loading',
                loaded: 'loaded',
                error: 'error',

                listStatus: 'loading',
                chatStatus: 'loading',
                errorCode: null,

                newMessageText: '',
                newMessageStatus: 'loaded',

                chatRefresher: null
            }
        },
        watch: {
            activeChat: function (chat) {
                if (chat != null) {
                    this.loadChat();
                }
            }
        },
        computed: {
            newMessageTextClear() {
                return this.newMessageText == null || this.newMessageText == "";
            }
        },
        methods: {
            loadChatsList() {
                this.listStatus = this.loading;
                const context = this;
                this.axios.get('/chat/list')
                    .then(response => {
                        if (response.data.status === 'ok') {
                            this.chatList = response.data.content[0];
                            this.listStatus = context.loaded;
                        } else if (response.data.status === 'error') {
                            this.listStatus = context.error;
                            this.errorCode = response.data.content[0];
                        }
                    }).catch((error) => {
                    this.listStatus = context.error;
                });
            },
            loadChat() {
                if(this.activeChat == null) {
                    return;
                }
                this.chatStatus = this.loading;
                const context = this;
                const chat = this.activeChat;
                this.axios.get('/chat/get', {
                    params: {
                        matchId: chat.id,
                        from: 0,
                        to: 10
                    }
                }).then(response => {
                    if (response.data.status === 'ok') {
                        context.activeChatMessages = response.data.content[0];
                        context.chatStatus = context.loaded;
                    } else if (response.data.status === 'error') {
                        context.chatStatus = context.error;
                        context.errorCode = response.data.content[0];
                    }
                }).catch((error) => {
                    context.chatStatus = context.error;
                });
            },
            sendMsg() {
                if(this.newMessageTextClear) {
                    return;
                }
                this.newMessageStatus = this.loading;
                const context = this;
                const formData = new FormData();
                formData.append('matchId', this.activeChat.id);
                formData.append('text', this.newMessageText);
                this.axios.post('/chat/send', formData)
                    .then(response => {
                        if (response.data.status === 'ok') {
                            context.newMessageText = "";
                            context.newMessageStatus = context.loaded;
                            context.loadChat(this.activeChat);
                        } else if (response.data.status === 'error') {
                            context.newMessageStatus = context.error;
                            context.errorCode = response.data.content[0];
                        }
                    }).catch((error) => {
                    context.newMessageStatus = context.error;
                });
            }
        },
        mounted() {
            if (!this.$store.getters.token) {
                this.$router.replace('/');
                return;
            }
            this.loadChatsList();
            this.loadChat();
            this.chatRefresher = setInterval(this.loadChat, 3 * 1000);
        },
        beforeDestroy() {
            clearInterval(this.chatRefresher);
        }
    }
</script>

<style scoped>
    img {
        max-width: 100%;
    }

    .inbox_people {
        background: #f8f8f8 none repeat scroll 0 0;
        float: left;
        overflow: hidden;
        width: 40%;
        border-right: 1px solid #c4c4c4;
    }

    .inbox_msg {
        border: 1px solid #c4c4c4;
        clear: both;
        overflow: hidden;
    }

    .top_spac {
        margin: 20px 0 0;
    }

    .recent_heading {
        float: left;
        width: 40%;
    }

    .srch_bar {
        display: inline-block;
        text-align: right;
        width: 60%;
        padding:
    }

    .headind_srch {
        padding: 10px 29px 10px 20px;
        overflow: hidden;
        border-bottom: 1px solid #c4c4c4;
    }

    .recent_heading h4 {
        color: #05728f;
        font-size: 21px;
        margin: auto;
    }

    .srch_bar input {
        border: 1px solid #cdcdcd;
        border-width: 0 0 1px 0;
        width: 80%;
        padding: 2px 0 4px 6px;
        background: none;
    }

    .srch_bar .input-group-addon button {
        background: rgba(0, 0, 0, 0) none repeat scroll 0 0;
        border: medium none;
        padding: 0;
        color: #707070;
        font-size: 18px;
    }

    .srch_bar .input-group-addon {
        margin: 0 0 0 -27px;
    }

    .chat_ib h5 {
        font-size: 15px;
        color: #464646;
        margin: 0 0 8px 0;
    }

    .chat_ib h5 span {
        font-size: 13px;
        float: right;
    }

    .chat_ib p {
        font-size: 14px;
        color: #989898;
        margin: auto
    }

    .chat_img {
        float: left;
        width: 11%;
    }

    .chat_ib {
        float: left;
        padding: 0 0 0 15px;
        width: 88%;
    }

    .chat_people {
        overflow: hidden;
        clear: both;
    }

    .chat_list {
        border-bottom: 1px solid #c4c4c4;
        margin: 0;
        padding: 18px 16px 10px;
        cursor: pointer;
    }

    .chat_list:hover {
        background-color: grey;
    }

    .inbox_chat {
        height: 550px;
        overflow-y: scroll;
    }

    .active_chat {
        background: #ebebeb;
    }

    .incoming_msg_img {
        display: inline-block;
        width: 6%;
    }

    .received_msg {
        display: inline-block;
        padding: 0 0 0 10px;
        vertical-align: top;
        width: 92%;
    }

    .received_withd_msg p {
        background: #ebebeb none repeat scroll 0 0;
        border-radius: 3px;
        color: #646464;
        font-size: 14px;
        margin: 0;
        padding: 5px 10px 5px 12px;
        width: 100%;
    }

    .time_date {
        color: #747474;
        display: block;
        font-size: 12px;
        margin: 8px 0 0;
    }

    .received_withd_msg {
        width: 57%;
    }

    .mesgs {
        float: left;
        padding: 30px 15px 0 25px;
        width: 60%;
    }

    .sent_msg p {
        background: #05728f none repeat scroll 0 0;
        border-radius: 3px;
        font-size: 14px;
        margin: 0;
        color: #fff;
        padding: 5px 10px 5px 12px;
        width: 100%;
    }

    .outgoing_msg {
        overflow: hidden;
        margin: 26px 0 26px;
    }

    .sent_msg {
        float: right;
        width: 46%;
    }

    .input_msg_write input {
        background: rgba(0, 0, 0, 0) none repeat scroll 0 0;
        border: medium none;
        color: #4c4c4c;
        font-size: 15px;
        min-height: 48px;
        width: 100%;
    }

    .type_msg {
        border-top: 1px solid #c4c4c4;
        position: relative;
    }

    .msg_send_btn {
        background: #05728f none repeat scroll 0 0;
        border: medium none;
        border-radius: 50%;
        color: #fff;
        cursor: pointer;
        font-size: 17px;
        height: 33px;
        position: absolute;
        right: 0;
        top: 11px;
        width: 33px;
    }

    .messaging {
        padding: 0 0 50px 0;
        margin-top: 60px;
    }

    .msg_history {
        height: 516px;
        overflow-y: auto;
    }
</style>
