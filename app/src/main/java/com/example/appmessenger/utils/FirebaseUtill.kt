package com.example.appmessenger.utils

import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.text.SimpleDateFormat

class FirebaseUtill {
    fun currentUserId(): String? {
        return FirebaseAuth.getInstance().uid
    }

    fun currentUserEmail(): String? {
        return FirebaseAuth.getInstance().currentUser?.email
    }

    fun isLoggedIn(): Boolean {
        return currentUserId() != null
    }

    fun currentUserDetails(): DocumentReference? {
        return FirebaseFirestore.getInstance().collection("users").document(currentUserId()!!)
    }

    fun allUserCollectionReference(): CollectionReference? {
        return FirebaseFirestore.getInstance().collection("users")
    }

    fun getChatRoom(chatId: String): DocumentReference {
        return FirebaseFirestore.getInstance().collection("chatrooms").document(chatId)
    }

    fun allChatroomCollectionReference(): CollectionReference {
        return FirebaseFirestore.getInstance().collection("chatrooms")
    }


    fun chatRoomMessage(chatId: String): CollectionReference {
        return getChatRoom(chatId).collection("chats")
    }

    fun getChatroomId(userId1: String, userId2: String): String {
        return if (userId1.hashCode() < userId2.hashCode()) {
            userId1 + "_" + userId2
        } else {
            userId2 + "_" + userId1
        }
    }

    fun getOtherUserFromChatroom(userIds: List<String?>): DocumentReference {
        if (userIds[0] == FirebaseUtill().currentUserId()) {
            return allUserCollectionReference()!!.document(userIds[1]!!)
        } else {
            return allUserCollectionReference()!!.document(userIds[0]!!)
        }
    }

    fun timestampToString(timestamp: Timestamp?): String {
        return SimpleDateFormat("HH:mm").format(timestamp?.toDate())
    }

    fun getAvatarUser(): StorageReference {
        return FirebaseStorage.getInstance().reference
            .child("profile_pic")
            .child(FirebaseUtill().currentUserId()!!)
    }
    fun getAvatarUserOther(otherId: String): StorageReference{
        return FirebaseStorage.getInstance().reference
            .child("profile_pic")
            .child(otherId)
    }
    fun logout(){
        return FirebaseAuth.getInstance().signOut()
    }

}