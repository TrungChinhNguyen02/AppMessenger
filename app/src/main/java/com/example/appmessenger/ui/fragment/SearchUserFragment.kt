package com.example.appmessenger.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appmessenger.firebase.FirestoreSearchUser
import com.example.appmessenger.adapters.AdapterSearchUser
import com.example.appmessenger.base.BaseFragment
import com.example.appmessenger.databinding.FragmentSearchUserBinding
import com.example.appmessenger.models.UserModel
import com.example.appmessenger.utils.FirebaseUtill
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.FirebaseFirestore

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchUserFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchUserFragment : BaseFragment<FragmentSearchUserBinding>() {
    private var adapter: AdapterSearchUser? = null
    private lateinit var recyclerView: RecyclerView

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
//                getUserInfo(searchItem)
                setupSearchRecyclerView(searchItem)
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
                    Log.d("TAG", "$userEmail")

                    // Hiển thị thông tin người dùng trong TextViews (ví dụ)
                } else {
                    // Nếu không có kết quả
                    Log.d("TAG", "Không tìm thấy thông tin người dùng")
                }
            }
            .addOnFailureListener { e ->
                // Xử lý khi có lỗi xảy ra
                Log.e("TAG", "Lỗi khi truy vấn dữ liệu", e)
            }
    }
    private fun setupSearchRecyclerView(searchTeam: String) {
        val query = FirebaseUtill().allUserCollectionReference()
            .whereEqualTo("memail", searchTeam)
        val options =
            FirestoreRecyclerOptions.Builder<UserModel>().setQuery(query, UserModel::class.java)
                .build()

        adapter = AdapterSearchUser(options, requireContext())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        adapter?.startListening()
    }

    override fun onStart() {
        super.onStart()
        adapter?.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter?.stopListening()
    }

    override fun onResume() {
        super.onResume()
        adapter?.startListening()
    }
}