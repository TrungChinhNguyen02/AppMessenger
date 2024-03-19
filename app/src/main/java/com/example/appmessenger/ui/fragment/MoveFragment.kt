package com.example.appmessenger.ui.fragment

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.appmessenger.R
import com.example.appmessenger.base.BaseFragment
import com.example.appmessenger.databinding.FragmentMoveBinding
import com.example.appmessenger.models.UserModel
import com.example.appmessenger.ui.activity.LoginActivity
import com.example.appmessenger.utils.FirebaseUtill
import com.google.firebase.auth.FirebaseAuth
import com.hbb20.CountryCodePicker.Language
import java.util.Locale


class MoveFragment : BaseFragment<FragmentMoveBinding>() {
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMoveBinding {
      return FragmentMoveBinding.inflate(inflater,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getInformation()
        binding.signout.setOnClickListener {
          FirebaseUtill().logout()
            startActivity(LoginActivity::class.java)
        }
        binding.selectlanguage.setOnClickListener {
            showDialog()
        }

    }

    fun getInformation(){
        getAvatarUser()
        val sharedPreferences = requireContext().getSharedPreferences("information", Context.MODE_PRIVATE)
        val userName = sharedPreferences.getString("username","")
        val userEmail = sharedPreferences.getString("useremail","")
        binding.nameuser.setText(userName)
        binding.emailUser.setText(userEmail)
    }
    fun getAvatarUser(){
        FirebaseUtill().getAvatarUser().downloadUrl
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    val userUri = task.result
                    Glide.with(requireContext()).load(userUri).into(binding.itemavatar)
                }
            }
    }
    fun showDialog() {

        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_languages)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        var selectLanguage: String? = null
        val btnCancel = dialog.findViewById<ImageView>(R.id.cancelbtn)
        val btnaccept = dialog.findViewById<TextView>(R.id.btntranslate)
        val english : LinearLayout = dialog.findViewById(R.id.english)
        val vietnames : LinearLayout = dialog.findViewById(R.id.vietnam)
        val checkvietnam: ImageView = dialog.findViewById(R.id.okvietnam)
        val checkEnglish: ImageView = dialog.findViewById(R.id.okenglish)


        english.setOnClickListener {
            selectLanguage = "en"
                checkEnglish.visibility = View.VISIBLE
            checkvietnam.visibility = View.GONE

        }
        vietnames.setOnClickListener {
            selectLanguage = "vi"
            checkvietnam.visibility = View.VISIBLE
            checkEnglish.visibility = View.GONE

        }

        btnCancel.setOnClickListener {
            dialog.hide()
        }
        btnaccept.setOnClickListener {

            val locale = Locale("en")
            Locale.setDefault(locale)
            val config = Configuration()
            config.locale = locale
            resources.updateConfiguration(config, resources.displayMetrics)
        }
        dialog.show()

    }

}