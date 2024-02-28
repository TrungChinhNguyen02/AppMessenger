package com.example.appmessenger.loginemail

import android.util.Log
import com.example.appmessenger.Ilogin.ISignIn
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.FirebaseDatabase

class EmailPasswordAccount (private val mILogin : ISignIn){
    private lateinit var auth: FirebaseAuth

    companion object {
        var id: String? = null
    }
    fun LogInEmail(email: String, password: String) {
        auth = Firebase.auth
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    mILogin.LoginSuccessful()
                    id = auth.currentUser!!.uid.toString()
                    Log.d("huhu", "LogInEmail: $id ")

                } else {
                    mILogin.LoginFail()

                }
            }
    }
    fun CreateAccount(email : String, password:String){
        auth = Firebase.auth
        val TAG ="CreateAccount"

        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{task ->
            if (task.isSuccessful){
                mILogin.LoginSuccessful()
            }
            else{
                mILogin.LoginFail()
            }
        }
    }
    fun saveUser( firstName : String, lastName : String, email : String){
        val mUser = User(firstName,lastName,email)
        val database = FirebaseDatabase.getInstance()
        val userRef = database.getReference("User")
        userRef.child(auth.uid.toString()).setValue(mUser)
        Log.d("TAG", "saveUser: ahiahi ")
    }
}