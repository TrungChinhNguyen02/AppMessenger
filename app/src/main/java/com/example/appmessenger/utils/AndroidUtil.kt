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
    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    fun passUserModelAsIntent(intent: Intent, model: UserModel) {
        intent.putExtra("username", model.mUserName)
        intent.putExtra("email", model.mEmail)
        intent.putExtra("userId", model.mUId)

    }

    fun getUserModelFromIntent(intent: Intent): UserModel? {
        val userModel = UserModel()
        userModel.mUserName(intent.getStringExtra("username"))
        userModel.mEmail(intent.getStringExtra("email"))
        userModel.mUId(intent.getStringExtra("userId"))
        return userModel
    }
    fun setProfilePic(context: Context, imageUri: Uri, imageView: ImageView) {
        Glide.with(context)
            .load(imageUri)
            .apply(RequestOptions.circleCropTransform())
            .into(imageView)
    }
}