<template>
    <div class="messaging">
        <div class="inbox_msg">
            <div class="inbox_people">
                <div class="headind_srch">
                    <div class="recent_heading">
                        <h4>Chats list</h4>
                    </div>
                    <!--<div class="srch_bar">-->
                    <!--<div class="stylish-input-group">-->
                    <!--<input type="text" class="search-bar" placeholder="Search">-->
                    <!--<span class="input-group-addon">-->
                    <!--<button type="button"> <i class="fa fa-search" aria-hidden="true"></i> </button>-->
                    <!--</span>-->
                    <!--</div>-->
                    <!--</div>-->
                </div>
                <div class="inbox_chat">

                    <div class="chat_list" v-for="chat in chatList" :key="chat.id" @click="loadChat(chat)">
                        <div class="chat_people">
                            <div class="chat_ib">
                                <h5>{{chat.matchedUser.name}} <span class="chat_date">{{chat.matchedAt}}</span></h5>
                                <p><span v-if="chat.lastMessage != null">{{chat.lastMessage.text}}</span></p>
                            </div>
                        </div>
                    </div>
                    <!--
                                        <div class="chat_list active_chat">
                                            <div class="chat_people">
                                                <div class="chat_img"><img src="https://ptetutorials.com/images/user-profile.png" alt="sunil"></div>
                                                <div class="chat_ib">
                                                    <h5>Sunil Rajput <span class="chat_date">Dec 25</span></h5>
                                                    <p>Test, which is a new approach to have all solutions
                                                        astrology under one roof.</p>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="chat_list">
                                            <div class="chat_people">
                                                <div class="chat_img"><img src="https://ptetutorials.com/images/user-profile.png" alt="sunil"></div>
                                                <div class="chat_ib">
                                                    <h5>Sunil Rajput <span class="chat_date">Dec 25</span></h5>
                                                    <p>Test, which is a new approach to have all solutions
                                                        astrology under one roof.</p>
                                                </div>
                                            </div>
                                        </div>
                    -->
                </div>
            </div>
            <div class="mesgs">
                <div class="msg_history">
                    <div v-for="msg in activeChatMessages" :key="msg.id" :class="{ 'outgoing_msg': isMsgSent(msg), 'incoming_msg': !isMsgSent(msg) }">
                        <div :class="{ 'sent_msg': isMsgSent(msg), 'received_msg': !isMsgSent(msg) }">
                            <div :class="{ 'received_withd_msg': !isMsgSent(msg) }">
                                <p>{{msg.text}}</p>
                                <span class="time_date">{{msg.sentAt}}</span>
                            </div>
                        </div>
                    </div>

                    <!--<div class="incoming_msg">-->
                        <!--<div class="received_msg">-->
                            <!--<div class="received_withd_msg">-->
                                <!--<p>Test which is a new approach to have all-->
                                    <!--solutions</p>-->
                                <!--<span class="time_date"> 11:01 AM    |    June 9</span>-->
                            <!--</div>-->
                        <!--</div>-->
                    <!--</div>-->
                    <!--<div class="outgoing_msg">-->
                        <!--<div class="sent_msg">-->
                            <!--<p>Test which is a new approach to have all-->
                                <!--solutions</p>-->
                            <!--<span class="time_date"> 11:01 AM    |    June 9</span></div>-->
                    <!--</div>-->
                </div>
                <div class="type_msg" v-if="activeChat != null">
                    <div class="input_msg_write">
                        <input v-model.trim="newChatMsgText" type="text" class="write_msg"
                               placeholder="Type a message"/>
                        <button @click="sendMsg" class="msg_send_btn" type="button"><i class="fa fa-paper-plane-o"
                                                                                       aria-hidden="true"></i></button>
                    </div>
                </div>
            </div>
        </div>
        <!--<p class="text-center top_spac"> Design by <a target="_blank" href="https://bootsnipp.com/snippets/1ea0N">Sunil Rajput</a></p>-->
    </div>
</template>

<script>
    export default {
        name: "ChatList",
        data() {
            return {
                currentUserId: null,
                chatList: [],
                activeChat: null,
                activeChatMessages: [],
                newChatMsgText: ''
            }
        },
        methods: {
            loadChatsList() {
                this.axios.get('/chat/list')
                    .then(response => {
                        if (response.data.status === 'ok') {
                            // console.log('/chat/list - success');
                            // console.log(response.data)
                            this.chatList = response.data.content[0];
                        } else if (response.data.status === 'error') {
                            // console.log('/chat/list - error');
                            // console.log(response.data)
                        }
                    }).catch((error) => {
                    // console.log('/chat/list - fail');
                    // console.log(error)
                });
            },
            loadChat(chat) {
                const context = this;
                this.activeChat = chat;
                this.axios.get('/chat/get', {
                    params: {
                        matchId: chat.id,
                        from: 0,
                        to: 10
                    }
                })
                    .then(response => {
                        if (response.data.status === 'ok') {
                            console.log('/chat/get - success');
                            console.log(response.data)
                            context.activeChatMessages = response.data.content[0]
                        } else if (response.data.status === 'error') {
                            console.log('/chat/get - error');
                            console.log(response.data)
                        }
                    }).catch((error) => {
                    console.log('/chat/get - fail');
                    console.log(error)
                });
            },
            sendMsg() {
                const context = this;
                const formData = new FormData();
                formData.append('matchId', this.activeChat.id);
                formData.append('text', this.newChatMsgText);
                this.axios.post('/chat/send', formData)
                    .then(response => {
                        if (response.data.status === 'ok') {
                            context.newChatMsgText = "";
                            context.loadChat(this.activeChat);
                        } else if (response.data.status === 'error') {

                        }
                    }).catch((error) => {

                });
            },
            isMsgSent(msg) {
                console.log(msg)
                console.log(msg.sender.id)
                console.log(this.currentUserId)
                return msg.sender.id == this.currentUserId;
            }
        },
        created: function () {
            if (!this.$store.getters.token) {
                this.$router.replace('/');
                return;
            }
            this.currentUserId = this.$store.getters.userId;
            this.loadChatsList();
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
    }

    .msg_history {
        height: 516px;
        overflow-y: auto;
    }
</style>