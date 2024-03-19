package com.example.appmessenger.ui.activity

import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.example.appmessenger.R
import com.example.appmessenger.base.BaseActivity
import com.example.appmessenger.databinding.ActivityHomeBinding
import com.example.appmessenger.ui.fragment.ContactsFragment
import com.example.appmessenger.ui.fragment.MessengerFragment
import com.example.appmessenger.ui.fragment.MoveFragment

class HomeActivity : BaseActivity<ActivityHomeBinding>() {
    override fun getViewBinding(): ActivityHomeBinding {
        return ActivityHomeBinding.inflate(layoutInflater)
    }

    override fun createView() {
        replaceFragment(ContactsFragment(), R.id.layout_fragment, false)
        binding.bottomNavi.show(1, true)
        bottomView()
    }

    fun bottomView() {
        binding.bottomNavi.apply {
            add(MeowBottomNavigation.Model(1, R.drawable.contacts))
            add(MeowBottomNavigation.Model(2, R.drawable.message))
            add(MeowBottomNavigation.Model(3, R.drawable.profile))
        }
        binding.bottomNavi.setOnClickMenuListener { item ->
            when (item.id) {

                1 -> replaceFragment(ContactsFragment(), R.id.layout_fragment, false)

                2 -> replaceFragment(MessengerFragment(), R.id.layout_fragment, false)

                3 -> replaceFragment(MoveFragment(), R.id.layout_fragment, false)

            }

        }
    }

}