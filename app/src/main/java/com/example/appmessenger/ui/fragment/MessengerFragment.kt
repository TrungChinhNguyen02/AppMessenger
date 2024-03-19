package com.example.appmessenger.ui.fragment

import android.os.Bundle
import android.os.Process
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appmessenger.R
import com.example.appmessenger.adapters.RecentChatUserAdapter
import com.example.appmessenger.base.BaseFragment
import com.example.appmessenger.databinding.FragmentMessengerBinding
import com.example.appmessenger.models.ChatRoomModel
import com.example.appmessenger.utils.FirebaseUtill
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MessengerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MessengerFragment : BaseFragment<FragmentMessengerBinding>() {

    private lateinit var recyclerView: RecyclerView
    private var adapter: RecentChatUserAdapter? = null
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMessengerBinding {
        return FragmentMessengerBinding.inflate(inflater,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.rcvListchat
        setupRecyclerView()
        binding.addUserChat.setOnClickListener {
            replaceFragment(SearchUserFragment(),R.id.layout_fragment,false)
        }
        backApp()
    }
    fun setupRecyclerView() {
        val query: Query = FirebaseUtill().allChatroomCollectionReference()
            .whereArrayContains("userIds", FirebaseUtill().currentUserId()!!)
            .orderBy("lastMessageTimestamp", Query.Direction.DESCENDING)

        val options: FirestoreRecyclerOptions<ChatRoomModel> =
            FirestoreRecyclerOptions.Builder<ChatRoomModel>()
                .setQuery(query, ChatRoomModel::class.java).build()

        adapter = context?.let { RecentChatUserAdapter(options, it) }
        recyclerView.setLayoutManager(LinearLayoutManager(context))
        recyclerView.setAdapter(adapter)
        adapter!!.startListening()

    }
    fun backApp(){
       requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){

           System.exit(0)
       }
    }

    override fun onStart() {
        super.onStart()
        if (adapter != null) adapter!!.startListening()
    }

    override fun onStop() {
        super.onStop()
        if (adapter != null) adapter!!.stopListening()
    }

}