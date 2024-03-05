package com.example.appmessenger.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.appmessenger.Ilogin.ISignIn
import com.example.appmessenger.R
import com.example.appmessenger.base.BaseFragment
import com.example.appmessenger.databinding.FragmentSignInBinding
import com.example.appmessenger.loginemail.EmailPasswordAccount
import com.example.appmessenger.models.UserModel
import com.example.appmessenger.utils.FirebaseUtill
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignUpFragment : BaseFragment<FragmentSignInBinding>(), ISignIn {
    private lateinit var mLogup: EmailPasswordAccount
    val firebaseUtil = FirebaseUtill()
    private lateinit var auth: FirebaseAuth
    var userModel: UserModel? = null

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSignInBinding {
        return FragmentSignInBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mLogup = EmailPasswordAccount(this)
        auth = FirebaseAuth.getInstance()

        binding.btnSignUp.setOnClickListener {
            mLogup.CreateAccount(binding.tk.text.toString(), binding.mk.text.toString())
        }
    }

    override fun LoginFail() {
        Toast.makeText(requireActivity(), "Đăng kí thất bại", Toast.LENGTH_SHORT).show()
    }

    override fun LoginSuccessful() {
        Toast.makeText(requireActivity(), "Đăng kí thành công", Toast.LENGTH_SHORT).show()

        val bundle = Bundle().apply {
            putString(
                "username",
                binding.firstname.text.toString() + " " + binding.lastname.text.toString()
            )
        }
        val loginEmailFragment = LoginEmailFragment()
        loginEmailFragment.arguments = bundle
        replaceFragment(loginEmailFragment, R.id.containerFragment, false)
        addDatatoFireBaseStore()
    }

    private fun addDatatoFireBaseStore() {
        val database: FirebaseFirestore = FirebaseFirestore.getInstance()
        userModel = UserModel(
            firebaseUtil.currentUserEmail().toString(),
            binding.firstname.text.toString() + " " + binding.lastname.text.toString(),
//            Timestamp.now(),
            firebaseUtil.currentUserId().toString()
        )
        val userDocument = database.collection("users").document(auth.uid.toString())
        userDocument.get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    // Tài liệu đã tồn tại, bạn có thể lấy giá trị từ documentSnapshot
                    Log.d("TAG", "Existing User_Name: ${documentSnapshot.data}")
                } else {
                    // Tài liệu không tồn tại, bạn có thể thêm mới
                    userDocument.set(userModel!!)
                        .addOnSuccessListener {
                            Log.d("TAG", "New User added successfully")
                        }
                        .addOnFailureListener { exception ->
                            Log.e("TAG", "Error adding new user", exception)
                        }
                }
            }
            .addOnFailureListener { exception ->
                Log.e("TAG", "Error getting document", exception)
            }
    }
}