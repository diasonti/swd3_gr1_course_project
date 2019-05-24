<template>
    <div class="row">
        <div class="messaging col-lg-8 col-md-10 col-sm-12 offset-lg-2 offset-md-1">
            <div class="inbox_msg row">
                <div class="inbox_people col-sm-3 col-xs-2">
                    <div class="headind_srch">
                            <h4>Conversations</h4>
                    </div>
                    <div class="inbox_chat">

                        <div v-for="chat in chatList" :key="chat.id" @click="activeChat = chat"
                             :class="{chat_list:true, active_chat: chat==activeChat}">
                            <div class="chat_people">
                                <div class="d-flex justify-content-between align-items-center">
                                    <h5 class="font-weight-bold ">{{chat.matchedUser.name}}</h5>
                                    <p class="" v-if="chat.lastMessage != null">{{chat.lastMessage.sentAt}}</p>
                                </div>
                                    <p v-if="chat.lastMessage != null">{{chat.lastMessage.text}}</p>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="mesgs col-sm-9 col-xs-10">
                    <div class="chat_heading">
                        <div v-if="activeChat != null">
                            <div class="row">
                                <div class="col-4 offset-4">
                                    <h4>{{activeChat.matchedUser.name}}</h4>
                                </div>
                                <div class="col-4">
                                    <p class="text-muted">Matched at {{activeChat.matchedAt.slice(11,16)+' '+activeChat.matchedAt.slice(2,10)}}</p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="msg_history" @scroll="loadOnScroll">
                        <div class="text-center">
                            <div v-if="chatStatus === loading" class="spinner-border" role="status">
                                <span class="sr-only" >oading...</span>
                            </div>
                        </div>

                        <div v-for="msg in activeChatMessages" :key="msg.id"
                             :class="{ 'outgoing_msg': msg.mine, 'incoming_msg': !msg.mine }">
                            <div :class="{ 'sent_msg': msg.mine, 'received_msg': !msg.mine }">
                                <div :class="{ 'received_withd_msg': !msg.mine }">
                                    <p class="px-3 py-2">{{msg.text}}</p>
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
                chatNums: 10,

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
            loadOnScroll(){
                const chatHistory = this.$el.querySelector(".msg_history");
                if (chatHistory.scrollTop === 0 && this.chatNums <=30){
                    this.chatNums += 10;
                    this.loadChat();
                }
            },
            scrollChatHistory() {
                const chatHistory = this.$el.querySelector(".msg_history");
                chatHistory.scrollTop = chatHistory.scrollHeight;
            },
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
                if (this.activeChat == null) {
                    return;
                }
                this.chatStatus = this.loading;
                const context = this;
                const chat = this.activeChat;
                this.axios.get('/chat/get', {
                    params: {
                        matchId: chat.id,
                        from: 0,
                        to: context.chatNums
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
                if (this.newMessageTextClear) {
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
                            context.loadChat();
                            context.scrollChatHistory();

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
            this.chatRefresher = setInterval(this.loadChat, 60 * 1000);
        },
        beforeDestroy() {
            clearInterval(this.chatRefresher);
        }
    }
</script>

<style scoped>


    .text-justify {
        text-align: justify;
    }
    /*main class*/
    .messaging {
        margin-top: 100px;
    }

    /*chat box*/
    .inbox_msg {
        background: white;
        border: 1px solid black;
        clear: both;
        overflow: hidden;
    }

    /*  **********************************************Left contact class **********************************************  */
    .inbox_people {
        background: white none repeat scroll 0 0;
        overflow: hidden;
        padding: 0;
        border-right: 1px solid black;
    }

    /* ************************Heading of the contact************************ */
    .headind_srch {
        padding: 10px;
        overflow: hidden;
        margin: 0;
        border-bottom: 1px beige solid;
        display: flex;
        align-items: center;
        justify-content: center;
    }
    .headind_srch h4{
        margin: 0;
    }

    .inbox_chat {
        height: 550px;
        overflow-y: scroll;
    }

    /* ************************Contact list************************ */

    .chat_people {
        overflow: hidden;
        clear: both;
    }

    .chat_list {
        /*border-bottom: 1px solid #c4c4c4;*/
        margin: 0;
        padding: 18px 16px 10px;
        cursor: pointer;
    }

    .chat_list h5 {
        font-size: 15px;
        color: #464646;
        margin: 0 0 8px 0;
    }

    .chat_list h5 .chat_date {
        font-size: 13px;
    }

    .chat_list p {
        font-size: 14px;
        color: #989898;
    }

    .chat_list:hover {
        background-color: #F3F6FA;
    }

    .active_chat {
        background: #6D8FAE !important;
    }

    .active_chat h5, p {
        color: white;
    }

    .active_chat p {
        color: white;
    }


    /*  **********************************************Right chat class **********************************************  */

    .mesgs {
        padding: 0 15px 0 25px;
    }

    /*  ********************************* Heading of the chat *********************************  */

    .chat_heading{
        padding: 10px;
        overflow: hidden;
        margin: 0;
        border-bottom: 1px beige solid;
        /*display: flex;*/
        /*align-items: center;*/
        /*justify-content: center;*/
    }

    .chat_heading h4{
        margin: 0;
    }

    .chat_heading p{
        color: black;
        margin: 0;
    }


    /*  ********************************* chat message list *********************************  */
    .mesgs .msg_history {
        height: 516px;
        overflow-y: auto;
    }

    .time_date {
        color: #747474;
        display: block;
        font-size: 12px;
        margin: 8px 0 0;
    }

    /*  ********************************* message input area *********************************  */
    .mesgs .type_msg {
        border-top: 1px solid #c4c4c4;
        position: relative;
    }

    img {
        max-width: 100%;
    }

    .top_spac {
        margin: 20px 0 0;
    }

    .recent_heading {
    }

    .srch_bar {
        display: inline-block;
        text-align: right;
        width: 60%;
        padding:
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

    .chat_img {
        float: left;
        width: 11%;
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
        color: #646464;
    }

    .sent_msg p, .received_withd_msg p {
        border-radius: 15px;
        -webkit-box-sizing: border-box;
        -moz-box-sizing: border-box;
        box-sizing: border-box;
        font-size: 14px;
        margin: 0;
        width: 100%;
    }

    .sent_msg p {
        background: #5581C9 none repeat scroll 0 0;
        color: #fff;
    }

    .received_withd_msg {
        width: 57%;
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

</style>
