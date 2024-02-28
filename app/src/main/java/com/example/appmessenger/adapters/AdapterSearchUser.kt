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

class AdapterSearchUser(options: FirestoreRecyclerOptions<UserModel>,private val context: Context):
        FirestoreRecyclerAdapter<UserModel, AdapterSearchUser.UserModelViewHolder>(options){
    private var firebaseUtill = FirebaseUtill()
    inner class UserModelViewHolder(itemview: View): RecyclerView.ViewHolder(itemview){
        val username: TextView = itemview.findViewById(R.id.user_name_text)
        val emalText: TextView = itemview.findViewById(R.id.user_name_text)
        val userpic: ImageView= itemview.findViewById(R.id.profile_pic_image_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserModelViewHolder {
            val view = LayoutInflater.from(context).inflate(R.layout.search_user,parent,false)
        return UserModelViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserModelViewHolder, position: Int, model: UserModel) {
       holder.username.text = model.mUserName
        holder.emalText.text = model.mEmail
        if (model.mUId == firebaseUtill.currentUserId()){
            holder.username.text = model.mUserName + "(Me)"
        }
        firebaseUtill.getCurrentProfilePicStorageRef().downloadUrl
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val uri: Uri? = task.result
                    if (uri != null) {
                        AndroidUtil().setProfilePic(context, uri, holder.userpic)
                    }
                }
            }
//        holder.itemView.setOnClickListener {
//            //navigate to chat activity
//            val intent = Intent(context, ChatActivity::class.java)
//            AndroidUtil().passUserModelAsIntent(intent, model)
//            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//            context.startActivity(intent)
//        }

    }
}