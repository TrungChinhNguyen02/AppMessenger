package com.example.appmessenger.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appmessenger.R
import com.example.appmessenger.models.ChatRoomModel
import com.example.appmessenger.models.UserModel
import com.example.appmessenger.ui.activity.ChatActivity
import com.example.appmessenger.utils.AndroidUtil
import com.example.appmessenger.utils.FirebaseUtill
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class RecentChatUserAdapter(
    options: FirestoreRecyclerOptions<ChatRoomModel>,
    private val context: Context
) : FirestoreRecyclerAdapter<ChatRoomModel, RecentChatUserAdapter.ChatroomModelViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatroomModelViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_userchat, parent, false)
        return ChatroomModelViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatroomModelViewHolder, position: Int, model: ChatRoomModel) {
        Log.d("onBindViewHolder", "MessengerFragment: kết noois dữ liệu thành công")

        FirebaseUtill().getOtherUserFromChatroom(model.userIds!!)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val lastMessageSentByMe = model.lastMessageSenderId == FirebaseUtill().currentUserId()

                    val otherUserModel = task.result?.toObject(UserModel::class.java)

                    FirebaseUtill().getAvatarUserOther(otherUserModel!!.mUId)
                        .getDownloadUrl()
                        .addOnCompleteListener { t ->
                            if (t.isSuccessful()) {
                                val uri: Uri = t.getResult()
                             Glide.with(context).load(uri).into(holder.avatarOther)
                            }
                        }
                    holder.usernameText.text = otherUserModel?.mUserName
                    holder.lastMessageText.text = if (lastMessageSentByMe) "You : ${model.lastMessage}" else model.lastMessage
                    holder.lastMessageTime.text = FirebaseUtill().timestampToString(model.lastMessageTimestamp)

                    holder.itemView.setOnClickListener { v: View? ->
                        //navigate to chat activity
                        val intent = Intent(context, ChatActivity::class.java)
                        AndroidUtil().passUserModelAsIntent(intent, otherUserModel!!)
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        context.startActivity(intent)
                    }
                }
            }
    }
    inner class ChatroomModelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var usernameText: TextView = itemView.findViewById(R.id.user_name_text)
        var lastMessageText: TextView = itemView.findViewById(R.id.last_message_text)
        var lastMessageTime: TextView = itemView.findViewById(R.id.last_message_time_text)
        var avatarOther: ImageView = itemView.findViewById(R.id.avatarother)
    }
}