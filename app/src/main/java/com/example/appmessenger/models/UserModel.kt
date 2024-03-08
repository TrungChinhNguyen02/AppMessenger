package com.example.appmessenger.models

import com.google.firebase.Timestamp

class UserModel(
    var mEmail: String,
    var mUserName: String,
    var mUId: String
) {
    // Empty constructor for Firebase deserialization
    constructor() : this("", "", "")
}