package com.example.appmessenger.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.appmessenger.R
import com.example.appmessenger.base.BaseFragment
import com.example.appmessenger.databinding.FragmentGetPhoneNumberBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import com.hbb20.CountryCodePicker
import java.util.concurrent.TimeUnit

/**
 * A simple [Fragment] subclass.
 * Use the [GetPhoneNumberFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GetPhoneNumberFragment : BaseFragment<FragmentGetPhoneNumberBinding>() {
    private val auth = FirebaseAuth.getInstance()
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentGetPhoneNumberBinding {
        return FragmentGetPhoneNumberBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       binding.keynational.registerCarrierNumberEditText(binding.edtphone)

        binding.btncontinue.setOnClickListener {
            val intent = Intent(requireContext(), GetCodePhoneFragment::class.java)
            val bundle = Bundle().apply {
                putString("phone",binding.keynational.fullNumberWithPlus)
            }
            val getCodePhoneFragment = GetCodePhoneFragment()
            getCodePhoneFragment.arguments = bundle
            Log.d("hihi","onViewCreated: ${binding.keynational.fullNumberWithPlus}")
            replaceFragment(getCodePhoneFragment,R.id.containerFragment,false)
        }
    }

//    private fun startPhoneNumberVerification(phoneNumber: String) {
//        val options = PhoneAuthOptions.newBuilder(auth)
//            .setPhoneNumber(phoneNumber)
//            .setTimeout(60L, TimeUnit.SECONDS)
//            .setActivity(requireActivity())
//            .setCallbacks(getCallbacks())
//            .build()
//
//        PhoneAuthProvider.verifyPhoneNumber(options)
//    }
//
//    private fun getCallbacks(): PhoneAuthProvider.OnVerificationStateChangedCallbacks {
//        return object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//            // Implement callbacks as needed
//            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
//            }
//
//            override fun onVerificationFailed(p0: FirebaseException) {
//                Toast.makeText(
//                    requireContext(),
//                    "Số điện thoại không chính xác",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//        }
//    }

}