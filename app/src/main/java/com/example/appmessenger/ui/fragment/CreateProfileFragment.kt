package com.example.appmessenger.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.appmessenger.R
import com.example.appmessenger.base.BaseFragment
import com.example.appmessenger.databinding.FragmentCreateProfileBinding
import com.example.appmessenger.models.UserModel
import com.example.appmessenger.ui.activity.HomeActivity
import com.example.appmessenger.utils.FirebaseUtill
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

/**
 * A simple [Fragment] subclass.
 * Use the [CreateProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CreateProfileFragment : BaseFragment<FragmentCreateProfileBinding>() {
    private lateinit var auth: FirebaseAuth
    private val firebaseUtil = FirebaseUtill()
    var userModel: UserModel? = null

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCreateProfileBinding {
        return FragmentCreateProfileBinding.inflate(inflater, container, false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = FirebaseAuth.getInstance()
        return super.onCreateView(inflater, container, savedInstanceState)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loading.visibility = View.VISIBLE
        addDatatoFireBaseStore()
        binding.backProfile.setOnClickListener {
            replaceFragment(LoginEmailFragment(), R.id.containerFragment, false)
        }

        binding.btnsave.setOnClickListener {
            setUsername()
            startActivity(HomeActivity::class.java)
        }
    }

    private fun setUsername() {
        val username = binding.edtUsername.text.toString()
        if (username.isEmpty() || username.length < 3) {
            binding.edtUsername.error = "Username length should be at least 3 chars"
            return
        }

        if (auth.currentUser?.uid != null) {
            addDatatoFireBaseStore()
        } else {
            val intent = Intent(requireContext(), HomeActivity::class.java)
            startActivity(intent)
        }
    }

        private fun addDatatoFireBaseStore() {
        val database: FirebaseFirestore = FirebaseFirestore.getInstance()
        val data: HashMap<String, Any> = hashMapOf()

        val userDocument = database.collection("users").document(auth.uid.toString())
        userDocument.get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    userModel = documentSnapshot.toObject(UserModel::class.java)
                    Log.d("TAG", "Existing User_Name: ${userModel!!.mUserName}")
                    binding.loading.visibility = View.GONE
                    binding.edtUsername.setText(userModel!!.mUserName)
                } else {
                    // Tài liệu không tồn tại, bạn có thể thêm mới
                    userDocument.set(data)
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