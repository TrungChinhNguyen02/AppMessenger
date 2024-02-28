package com.example.appmessenger.utils

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

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

    fun allUserCollectionReference(): CollectionReference {
        return FirebaseFirestore.getInstance().collection("users")
    }
    fun getOtherUserFromChatroom(userIds: List<String>): DocumentReference {
        return if (userIds[0] == FirebaseUtill().currentUserId()) {
            allUserCollectionReference().document(userIds[1])
        } else {
            allUserCollectionReference().document(userIds[0])
        }
    }
    fun getCurrentProfilePicStorageRef(): StorageReference {
        return FirebaseStorage.getInstance().reference
            .child("profile_pic")
            .child(FirebaseUtill().currentUserId() ?: "")
    }
}