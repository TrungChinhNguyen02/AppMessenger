package com.example.appmessenger.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appmessenger.R
import com.example.appmessenger.models.ChatModel
import com.example.appmessenger.utils.FirebaseUtill
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class ChatAdapter(options: FirestoreRecyclerOptions<ChatModel>, private val context: Context) :
    FirestoreRecyclerAdapter<ChatModel, ChatAdapter.ChatModelViewHolder>(options) {

    class ChatModelViewHolder (itemView: View): RecyclerView.ViewHolder(itemView){

        var userChatlayout: LinearLayout = itemView.findViewById(R.id.user_chat_layout)
        var youChatLayout: LinearLayout = itemView.findViewById(R.id.you_chat_layout)
        var userChatTextview: TextView = itemView.findViewById(R.id.user_send_text)
        var youChatTextview: TextView = itemView.findViewById(R.id.you_send_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatModelViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_chat_message_row, parent, false)
        return ChatModelViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatModelViewHolder, position: Int, model: ChatModel) {
        if (model.senderId == FirebaseUtill().currentUserId()) {
            holder.userChatlayout.visibility = View.GONE
            holder.youChatLayout.visibility = View.VISIBLE
            holder.youChatTextview.text = model.message
        } else {
            holder.youChatLayout.visibility = View.GONE
            holder.userChatlayout.visibility = View.VISIBLE
            holder.userChatTextview.text = model.message
        }
    }
}