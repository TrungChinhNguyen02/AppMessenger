package com.example.appmessenger.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.appmessenger.adapters.AdapterSearchUser
import com.example.appmessenger.base.BaseFragment
import com.example.appmessenger.databinding.FragmentSearchUserBinding
import com.example.appmessenger.models.UserModel
import com.example.appmessenger.utils.FirebaseUtill
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore

class SearchUserFragment : BaseFragment<FragmentSearchUserBinding>() {
    private var adapter: AdapterSearchUser? = null
    private lateinit var recyclerView: RecyclerView
    val userModels: MutableList<UserModel> = mutableListOf()
    override fun getViewBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentSearchUserBinding {
        return FragmentSearchUserBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.searchUserRecyclerView
        binding.searchUserBtn.setOnClickListener {
            val searchItem = binding.seachUsernameInput.text.toString()
            if (searchItem.isEmpty() || searchItem.length < 3) {
                binding.seachUsernameInput.error = "Invalid Username"
                return@setOnClickListener
            } else {
                getUserInfo(searchItem)
            }
        }
    }

    private fun getUserInfo(email: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("users")
            .whereEqualTo("memail", email)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    // Lấy document đầu tiên (trong trường hợp có nhiều document)
                    val documentSnapshot = querySnapshot.documents[0]
                    // Lấy dữ liệu từ document
                    val name = documentSnapshot.getString("muserName")
                    val userEmail = documentSnapshot.getString("memail")
                    val uId = documentSnapshot.getString("muid")
                    val userModel = UserModel(userEmail!!, name!!, uId)
                    userModels.clear()
                    userModels.add(userModel)
                    Log.d("TAG", "${userModel!!.mEmail}, ${userModel!!.mUserName}, ${userModel!!.mUId}")
                    updateRecyclerView(userModels)
                } else {
                    Log.d("TAG", "Không tìm thấy thông tin người dùng")
                }
            }
            .addOnFailureListener { e ->
                // Xử lý khi có lỗi xảy ra
                Log.e("TAG", "Lỗi khi truy vấn dữ liệu", e)
            }
    }

    private fun updateRecyclerView(userModel: MutableList<UserModel>) {
        activity?.runOnUiThread {
            val adapter = AdapterSearchUser(userModels, requireContext())
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = adapter
        }
    }
}