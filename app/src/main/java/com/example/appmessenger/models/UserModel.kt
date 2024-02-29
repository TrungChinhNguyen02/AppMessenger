package com.example.appmessenger.models

import com.google.firebase.Timestamp

class UserModel(
    val mEmail: String,
    var mUserName: String,
    val mUId: String?) {
    fun mUserName(stringExtra: String?) {

    }

    fun mEmail(stringExtra: String?) {

    }

    fun mUId(stringExtra: String?) {

    }

    constructor() : this("", "", "")
}