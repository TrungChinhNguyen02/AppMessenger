package com.example.appmessenger.ui.fragment

import android.opengl.Visibility
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
            checkTkMkUi(tk,mk)
        }
        binding.txtsignup.setOnClickListener {
            replaceFragment(SignUpFragment(), R.id.containerFragment,false)
        }

    }

    override fun LoginFail() {
        binding.loadingprogress.visibility = View.GONE
        Toast.makeText(requireActivity(), "Tài khoản hoặc mật khẩu không chính xác", Toast.LENGTH_SHORT).show()
    }

    override fun LoginSuccessful() {
        binding.loadingprogress.visibility = View.GONE
        Toast.makeText(requireActivity(), "đăng nhập thành công", Toast.LENGTH_SHORT).show()
        replaceFragment(CreateProfileFragment(), R.id.containerFragment, false)
    }
    fun checkTkMkUi(tk: String, mk: String){
        if (tk.isNotEmpty() && mk.isNotEmpty()) {
            // Nếu cả tài khoản và mật khẩu đều không trống
            // Thực hiện đăng nhập
            binding.loadingprogress.visibility = View.VISIBLE
            mLogin.LogInEmail(tk, mk)
        } else {
            // Nếu có ít nhất một trường không được nhập
            if (tk.isEmpty()) {
                binding.tk.error = "Bạn chưa nhập tài khoản"
            }
            if (mk.isEmpty()) {
                binding.mk.error = "Mật khẩu không hợp lệ"
            }
            // Ẩn ProgressBar vì có lỗi xảy ra
            binding.loadingprogress.visibility = View.INVISIBLE
        }
    }

}