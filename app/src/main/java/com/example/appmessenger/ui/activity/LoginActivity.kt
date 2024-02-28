package com.example.appmessenger.ui.activity

import android.os.Handler
import android.os.Looper
import com.example.appmessenger.ui.fragment.LoginEmailFragment
import com.example.appmessenger.R
import com.example.appmessenger.base.BaseActivity
import com.example.appmessenger.databinding.ActivityLoginBinding
import com.example.appmessenger.ui.fragment.SplashFragment
import com.example.appmessenger.utils.FirebaseUtill

class LoginActivity : BaseActivity<ActivityLoginBinding>() {
    private val TAG = "LoginActivity"
    private val handler = Handler(Looper.getMainLooper())
    private val firebaseUtil = FirebaseUtill()

    override fun getViewBinding(): ActivityLoginBinding {
        return ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun createView() {

        replaceFragment(SplashFragment(), R.id.containerFragment, false)
        handler.postDelayed({
//            if (firebaseUtil.isLoggedIn()){
//              startActivity(HomeActivity::class.java)
//            }else{
                replaceFragment(LoginEmailFragment(),R.id.containerFragment,false)
//            }
//            finish()
        },5000)
    }

}