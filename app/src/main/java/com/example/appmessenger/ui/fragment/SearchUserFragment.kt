package com.example.appmessenger.ui.fragment

import android.app.DownloadManager.Query
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appmessenger.FirestoreSearchUser
import com.example.appmessenger.R
import com.example.appmessenger.adapters.AdapterSearchUser
import com.example.appmessenger.base.BaseFragment
import com.example.appmessenger.databinding.FragmentGetCodePhoneBinding
import com.example.appmessenger.databinding.FragmentSearchUserBinding
import com.example.appmessenger.databinding.SearchUserBinding
import com.example.appmessenger.models.UserModel
import com.example.appmessenger.ui.activity.ChatActivity
import com.example.appmessenger.utils.FirebaseUtill
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.gms.tasks.OnCompleteListener

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
        binding.searchUserBtn.setOnClickListener {
            val searchItem = binding.seachUsernameInput.text.toString()
            if (searchItem.isEmpty() || searchItem.length < 3) {
                binding.seachUsernameInput.error = "Invalid Username"
                return@setOnClickListener
            } else {
//                setupSearchRecyclerView(searchItem)
//                val intent = Intent(requireActivity(), ChatActivity::class.java)
//                startActivity(intent)
                FirestoreSearchUser.searchUsersByName(searchItem)
                    .addOnCompleteListener(OnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val userList = task.result

                            // Xử lý danh sách người dùng tìm kiếm được ở đây
                            for (userData in userList!!) {
                                Log.d(
                                    "UserFound",
                                    "UserId: ${userData.mUId}, Username: ${userData.mUserName}"
                                )
                            }
                        } else {
                            // Xử lý khi có lỗi xảy ra
                            Log.e(
                                "SearchError",
                                "Error searching users: ${task.exception?.message}"
                            )
                        }
                    })
            }
        }
    }

    private fun setupSearchRecyclerView(searchTeam: String) {
        val query = FirebaseUtill().allUserCollectionReference()
            .whereGreaterThanOrEqualTo("username", searchTeam)
            .whereLessThanOrEqualTo("username", "$searchTeam\uf8ff")
        val options =
            FirestoreRecyclerOptions.Builder<UserModel>().setQuery(query, UserModel::class.java)
                .build()
        Log.d("TAG", "setupSearchRecyclerView: ${query.get()}")
//        adapter = AdapterSearchUser(options, requireContext())
//        recyclerView.layoutManager = LinearLayoutManager(requireContext())
//        recyclerView.adapter = adapter
//        adapter?.startListening()
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