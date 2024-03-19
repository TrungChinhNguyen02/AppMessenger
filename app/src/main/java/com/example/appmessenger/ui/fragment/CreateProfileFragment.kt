package com.example.appmessenger.ui.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.appmessenger.R
import com.example.appmessenger.base.BaseFragment
import com.example.appmessenger.databinding.FragmentCreateProfileBinding
import com.example.appmessenger.models.UserModel
import com.example.appmessenger.ui.activity.HomeActivity
import com.example.appmessenger.utils.FirebaseUtill
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [CreateProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class CreateProfileFragment : BaseFragment<FragmentCreateProfileBinding>() {
    private lateinit var auth: FirebaseAuth
    var userModel: UserModel? = null
    private lateinit var pickImageLauncher: ActivityResultLauncher<Intent>
    var uri: Uri? = null


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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeImagePicker()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loading.visibility = View.VISIBLE
        addDatatoFireBaseStore()
        binding.backProfile.setOnClickListener {
            replaceFragment(LoginEmailFragment(), R.id.containerFragment, false)
        }
        setUsername()
        binding.btnsave.setOnClickListener {
            binding.loading.visibility = View.VISIBLE

            CoroutineScope(Dispatchers.Main).launch{
                updateFrofile()

                startActivity(HomeActivity::class.java)
            }

        }
        binding.bgavatar.setOnClickListener {

            openGallery()
        }

    }

    private fun setUsername() {
        val username = binding.edtUsername.text.toString()
        if (username.isEmpty() || username.length < 3) {
            binding.edtUsername.error = "Username length should be at least 3 chars"
            return
        } else if (auth.currentUser?.uid == null) {
            addDatatoFireBaseStore()
        } else {
            val intent = Intent(requireContext(), HomeActivity::class.java)
            startActivity(intent)
        }
    }

    private fun addDatatoFireBaseStore() {
        getAvatarUser()
        val sharedPreferences =
            requireContext().getSharedPreferences("information", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val database: FirebaseFirestore = FirebaseFirestore.getInstance()
        val data: HashMap<String, Any> = hashMapOf()

        val userDocument = database.collection("users").document(auth.uid.toString())
        userDocument.get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    userModel = documentSnapshot.toObject(UserModel::class.java)
                    Log.d("TAG", "Existing User_Name: ${userModel!!.mUserName}")
                    binding.loading.visibility = View.INVISIBLE
                    binding.edtUsername.setText(userModel!!.mUserName)
                    editor.putString("username", userModel!!.mUserName.toString())
                    editor.putString("useremail", userModel!!.mEmail.toString())
                    editor.apply()
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

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickImageLauncher.launch(intent)
    }

    private fun initializeImagePicker() {
        pickImageLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    // Get the URI of the selected image from the gallery
                    uri = result.data?.data
                    Glide.with(requireContext()).load(uri)
                        .into(binding.bgavatar)
                }

            }
    }

    fun updateFrofile() {
        val newUserName = binding.edtUsername.text.toString().trim()
        if (newUserName.isEmpty() || newUserName.length < 3) {
            binding.edtUsername.error = "Username length should be at least 3 chars"
            return
        } else {
            userModel!!.mUserName = newUserName
        }

        if (uri != null) {
            FirebaseUtill().getAvatarUser().putFile(uri!!)
                .addOnCompleteListener { task ->
                    FirebaseUtill().currentUserDetails()!!.set(userModel!!).addOnCompleteListener { t->
                        Toast.makeText(requireActivity(), "Update Successful", Toast.LENGTH_SHORT).show()
                    }
                }
        }
        FirebaseUtill().currentUserDetails()!!.set(userModel!!).addOnCompleteListener { t->
            Toast.makeText(requireActivity(), "Update Successful", Toast.LENGTH_SHORT).show()
        }
    }
    fun getAvatarUser(){
        FirebaseUtill().getAvatarUser().downloadUrl
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    val userUri = task.result
                    Glide.with(requireContext()).load(userUri).into(binding.bgavatar)
                }
            }
    }

}