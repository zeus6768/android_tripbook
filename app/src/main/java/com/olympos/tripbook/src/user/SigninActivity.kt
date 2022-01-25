package com.olympos.tripbook.src.user

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.olympos.tripbook.config.BaseActivity
import com.olympos.tripbook.databinding.ActivityUserSigninBinding
import com.olympos.tripbook.src.home.MainActivity
import com.olympos.tripbook.src.user.model.User
import retrofit2.Retrofit

class SigninActivity : BaseActivity() {
    private lateinit var binding: ActivityUserSigninBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserSigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signinSigninBtnIv.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun kakaoSignin() {
        val retrofit = Retrofit.Builder().baseUrl()
    }
}