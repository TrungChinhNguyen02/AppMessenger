package com.example.appmessenger.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appmessenger.R
import com.example.appmessenger.models.UserModel
import com.example.appmessenger.ui.activity.ChatActivity
import com.example.appmessenger.utils.AndroidUtil
import com.example.appmessenger.utils.FirebaseUtill
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class AdapterSearchUser(options: FirestoreRecyclerOptions<UserModel>, private val context: Context) :
    FirestoreRecyclerAdapter<UserModel, AdapterSearchUser.UserModelViewHolder>(options) {

    private var firebaseUtill = FirebaseUtill()

    inner class UserModelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val username: TextView = itemView.findViewById(R.id.search_name_text)
        val emailText: TextView = itemView.findViewById(R.id.search_email_text) // Đã thay đổi thành user_email_text
        val userpic: ImageView = itemView.findViewById(R.id.search_pic_image_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserModelViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.search_user, parent, false)
        return UserModelViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserModelViewHolder, position: Int, model: UserModel) {

        if (model.mUId == firebaseUtill.currentUserId()) {
            holder.username.text = model.mUserName + " (Me)"
            holder.emailText.text = model.mEmail
        }else{
            holder.username.text = model.mUserName
            holder.emailText.text = model.mEmail
        }



//        firebaseUtill.getCurrentProfilePicStorageRef().downloadUrl
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    val uri: Uri? = task.result
//                    if (uri != null) {
//                        AndroidUtil().setProfilePic(context, uri, holder.userpic)
//                    }
//                }
//            }

        holder.itemView.setOnClickListener {
            //navigate to chat activity
            val intent = Intent(context, ChatActivity::class.java)
            AndroidUtil().passUserModelAsIntent(intent, model)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }
}