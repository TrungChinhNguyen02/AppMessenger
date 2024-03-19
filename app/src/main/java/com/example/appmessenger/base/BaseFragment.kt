package com.example.appmessenger.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.viewbinding.ViewBinding
import com.example.appmessenger.R

abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    private var _binding: VB? = null
    protected val binding: VB
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = getViewBinding(inflater, container)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    protected fun replaceFragment(fragment: Fragment,viewId: Int = android.R.id.content, addToBackStack: Boolean) {
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(viewId, fragment)
        transaction.setCustomAnimations(
            R.anim.slide_in,
            R.anim.fade_out,
            R.anim.fade_in,
            R.anim.slide_out
        )
        if (addToBackStack) {
            transaction.addToBackStack(fragment.javaClass.simpleName)
        }
        transaction.commit()
    }

    protected fun addFragment(fragment: Fragment, addToBackStack: Boolean) {
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.add(getFragmentContainerId(), fragment)

        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null)
        }

        fragmentTransaction.commit()
    }

    protected fun getFragmentContainerId(): Int {
        // Đổi đây thành ID của layout chứa Fragment trong Activity của bạn
        return android.R.id.content
    }
    protected fun startActivity(destinationActivity: Class<*>) {
        val intent = Intent(requireActivity(), destinationActivity)
        startActivity(intent)
    }
}