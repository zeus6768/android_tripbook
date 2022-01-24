package com.olympos.tripbook.src.user

import android.os.Bundle
import com.olympos.tripbook.config.BaseActivity
import com.olympos.tripbook.databinding.ActivityUserSigninBinding
import com.olympos.tripbook.src.user.model.User

class SigninActivity : BaseActivity() {
    private lateinit var binding: ActivityUserSigninBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserSigninBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun getUser(): User {
        //val email: String = binding.signupEdittext.text.toString() + "@" + binding.signupEmailEdittext.text.toString()
        val email : String = binding.signinEmailEdittext.text.toString()
        val pwd: String = binding.signinPasswordEdittext.text.toString()
        return User(email, pwd, "", "")
    }
}