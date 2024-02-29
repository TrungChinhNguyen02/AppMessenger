package com.example.appmessenger.base

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.appmessenger.R

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    protected lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        setContentView(binding.root)
        createView()
    }

    protected abstract fun getViewBinding(): VB

    protected abstract fun createView()

    open fun addFragment(
        fragment: Fragment,
        viewId: Int = android.R.id.content,
        addToBackStack: Boolean = false
    ) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(
            R.anim.slide_in,
            R.anim.fade_out,
            R.anim.fade_in,
            R.anim.slide_out
        )
        transaction.add(viewId, fragment)
        if (addToBackStack) {
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }

    open fun replaceFragment(
        fragment: Fragment,
        viewId: Int = android.R.id.content,
        addToBackStack: Boolean = true
    ) {
        val transaction = supportFragmentManager.beginTransaction()
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

    fun startActivity(destinationActivity: Class<*>) {
        startActivity(Intent(this, destinationActivity))
    }

    // Chức năng chuyển sang một Activity khác với dữ liệu
    fun startActivityWithExtras(destinationActivity: Class<*>, extras: Bundle) {
        val intent = Intent(this, destinationActivity)
        intent.putExtras(extras)
        startActivity(intent)
    }
}