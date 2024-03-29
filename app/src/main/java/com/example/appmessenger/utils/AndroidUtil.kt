package com.example.appmessenger.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.appmessenger.models.UserModel


class AndroidUtil {
    fun passUserModelAsIntent(intent: Intent, model: UserModel) {
        intent.putExtra("username", model.mUserName)
        intent.putExtra("email", model.mEmail)
        intent.putExtra("userId", model.mUId)

    }

    fun getUserModelFromIntent(intent: Intent): UserModel? {
        val userModel = UserModel()
        userModel.mUserName = intent.getStringExtra("username").toString()
        userModel.mEmail = intent.getStringExtra("email").toString()
        userModel.mUId = intent.getStringExtra("userId").toString() // Correct assignment for mUId
        return userModel
    }

    fun setProfilePic(context: Context, imageUri: Uri, imageView: ImageView) {
        Glide.with(context)
            .load(imageUri)
            .apply(RequestOptions.circleCropTransform())
            .into(imageView)
    }
}