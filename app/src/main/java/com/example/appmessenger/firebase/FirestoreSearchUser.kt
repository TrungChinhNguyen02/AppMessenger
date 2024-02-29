package com.example.appmessenger.firebase

import com.example.appmessenger.models.UserModel
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore

object FirestoreSearchUser {
    private const val USERS_COLLECTION = "users" // Thay thế bằng tên của bảng dữ liệu người dùng trong Firestore

    fun searchUsersByName(username: String): Task<List<UserModel>> {
        val db = FirebaseFirestore.getInstance()
        val usersCollection = db.collection(USERS_COLLECTION)

        return usersCollection
            .whereGreaterThanOrEqualTo("username", username)
            .whereLessThanOrEqualTo("username", "$username\uf8ff")
            .get()
            .continueWith { task ->
                val userList = mutableListOf<UserModel>()
                val querySnapshot = task.result

                querySnapshot?.documents?.forEach { document ->
                    val userId = document.id
                    val foundUsername = document.getString("username")

                    // Add the userId and username to the list
                    if (foundUsername != null) {
                        userList.add(UserModel())
                    }
                }

                userList
            }
    }
}