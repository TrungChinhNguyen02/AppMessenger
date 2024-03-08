package com.example.appmessenger.models

import com.google.firebase.Timestamp

class ChatModel {
    var message: String? = null
    var senderId: String? = null
    var timestamp: Timestamp? = null

    constructor() // Empty constructor required by Firebase for deserialization

    constructor(message: String?, senderId: String?, timestamp: Timestamp?) {
        this.message = message
        this.senderId = senderId
        this.timestamp = timestamp
    }
}