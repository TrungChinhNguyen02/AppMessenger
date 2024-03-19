package com.example.appmessenger.models

import com.google.firebase.Timestamp


class ChatRoomModel {
    var chatroomId: String? = null
    var userIds: List<String?>? = null
    var lastMessageTimestamp: Timestamp = Timestamp.now()
    var lastMessageSenderId: String? = null
    var lastMessage: String? = null

    constructor(
        chatroomId: String? = null,
        userIds: List<String?>,
        lastMessageTimestamp: Timestamp,
        lastMessageSenderId: String? = null,
        lastMessageL: String? = null
    ) {
        this.chatroomId = chatroomId
        this.userIds = userIds
        this.lastMessageTimestamp = lastMessageTimestamp
        this.lastMessageSenderId = lastMessageSenderId
        this.lastMessage = lastMessage
    }

    // No-argument constructor required by Firebase for deserialization
    constructor()
}