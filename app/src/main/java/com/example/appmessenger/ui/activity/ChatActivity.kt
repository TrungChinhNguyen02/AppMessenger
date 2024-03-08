package com.example.appmessenger.ui.activity


import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appmessenger.adapters.ChatAdapter
import com.example.appmessenger.base.BaseActivity
import com.example.appmessenger.databinding.ActivityChatBinding
import com.example.appmessenger.models.ChatModel
import com.example.appmessenger.models.ChatRoomModel
import com.example.appmessenger.models.UserModel
import com.example.appmessenger.utils.AndroidUtil
import com.example.appmessenger.utils.FirebaseUtill
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.Timestamp
import com.google.firebase.firestore.Query

class ChatActivity : BaseActivity<ActivityChatBinding>() {
    val clearText: String? = null
    var otherId: String? = null
    var chatroomId: String? = null
    var chatroomModel: ChatRoomModel? = null
    var chatModel: ChatModel? = null
    var adapter: ChatAdapter? = null
    var recyclerView: RecyclerView? = null
    var otherUser: UserModel? = null
    override fun getViewBinding(): ActivityChatBinding {
        return ActivityChatBinding.inflate(layoutInflater)
    }

    override fun createView() {
        recyclerView = binding.rvcChat
        otherUser = AndroidUtil().getUserModelFromIntent(intent)
        if (otherUser == null) {
            Log.d("ahihi", "createView: không có dữ liệu")
            // Handle the case when otherUser is null (e.g., show an error message)
            // You can return or finish the activity or take appropriate action.
            return
        }
        otherId = otherUser!!.mUId


        //get UserModel
        chatroomId =
            FirebaseUtill().getChatroomId(FirebaseUtill().currentUserId()!!, otherId!!)
        binding.backChat.setOnClickListener {
            startActivity(HomeActivity::class.java)
        }
        binding.sendMessenger.setOnClickListener {
            val messenger =  binding.inputMessenger.text.toString().trim()
            if (messenger.isEmpty()){
                return@setOnClickListener
            }else{
                sendMessenger(messenger)
            }
        }

        binding.usernamechat.text = otherUser!!.mUserName

        getOrCreateChatroomModel()
        setupChatRecyclerview()
    }

    private fun setupChatRecyclerview() {
        val query: Query = FirebaseUtill().chatRoomMessage(chatroomId!!)
            .orderBy("timestamp", Query.Direction.DESCENDING)
        val options: FirestoreRecyclerOptions<ChatModel> =
            FirestoreRecyclerOptions.Builder<ChatModel>()
                .setQuery(query, ChatModel::class.java).build()
        adapter = ChatAdapter(options, this)
        val manager = LinearLayoutManager(this)
        manager.setReverseLayout(true)
        recyclerView!!.setLayoutManager(manager)
        recyclerView!!.setAdapter(adapter)
        adapter!!.startListening()
        adapter!!.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                recyclerView!!.smoothScrollToPosition(0)
            }
        })
    }


    private fun sendMessenger(message: String) {
        chatroomModel?.apply {
            lastMessageTimestamp = Timestamp.now()
            lastMessageSenderId = FirebaseUtill().currentUserId()
            lastMessage = message
        }

        // Set the updated chatroomModel to the Firebase chatroom reference
        chatroomId?.let {
            FirebaseUtill().getChatRoom(it).set(chatroomModel!!)
        }

        // Create a new ChatMessageModel with the current message details
        val newChatMessage = ChatModel(message, FirebaseUtill().currentUserId(), Timestamp.now())

        // Add the new ChatMessageModel to the chatroom messages collection in Firebase
        chatroomId?.let {
            FirebaseUtill().chatRoomMessage(it).add(newChatMessage)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Clear the message input field after sending the message
                       binding.inputMessenger.text.clear()

                        // Send a notification for the new message
//                        sendNotification(message)
                    }
                }
        }
    }

//    private fun sendNotification(message: String) {
//
//    }


    private fun getOrCreateChatroomModel() {
        FirebaseUtill().getChatRoom(chatroomId!!).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val result = task.result
                    if (result != null) {
                        chatroomModel = result.toObject(ChatRoomModel::class.java)
                        if (chatroomModel == null) {
                            // First time chat
                            chatroomModel = ChatRoomModel(
                                chatroomId!!,
                                listOf(FirebaseUtill().currentUserId(), otherId),
                                Timestamp.now(),
                                ""
                            )
                            FirebaseUtill().getChatRoom(chatroomId!!).set(chatroomModel!!)
                        }
                    }
                }
            }
    }

}