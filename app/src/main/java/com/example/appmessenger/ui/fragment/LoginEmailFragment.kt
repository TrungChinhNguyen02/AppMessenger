package com.example.appmessenger.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.appmessenger.Ilogin.ISignIn
import com.example.appmessenger.R
import com.example.appmessenger.base.BaseFragment
import com.example.appmessenger.databinding.FragmentLoginEmailBinding
import com.example.appmessenger.loginemail.EmailPasswordAccount

class LoginEmailFragment : BaseFragment<FragmentLoginEmailBinding>(),ISignIn {
    private lateinit var mLogin: EmailPasswordAccount

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLoginEmailBinding {
        return  FragmentLoginEmailBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         mLogin = EmailPasswordAccount(this)
        binding.btnLogin.setOnClickListener {
            val tk = binding.tk.text.toString()
            val mk = binding.mk.text.toString()
            mLogin.LogInEmail(tk,mk)
        }
        binding.txtsignup.setOnClickListener {
            replaceFragment(SignUpFragment(), R.id.containerFragment,false)
        }

    }

    override fun LoginFail() {
        Toast.makeText(requireActivity(), "Tài khoản hoặc mật khẩu không chính xác", Toast.LENGTH_SHORT).show()
    }

    override fun LoginSuccessful() {
        Toast.makeText(requireActivity(), "đăng nhập thành công", Toast.LENGTH_SHORT).show()
        replaceFragment(CreateProfileFragment(), R.id.containerFragment, false)
    }

}