package com.olympos.tripbook.src.user

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.olympos.tripbook.R
import com.olympos.tripbook.config.BaseActivity
import com.olympos.tripbook.databinding.ActivityUserSigninBinding
import com.olympos.tripbook.src.home.MainActivity
import com.olympos.tripbook.utils.getJwt

class SigninActivity : BaseActivity() {
    private lateinit var binding: ActivityUserSigninBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserSigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authenticate()

        binding.signinSigninBtnIv.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        super.onClick(v)

        when(v!!.id) {
            R.id.signin_signin_btn_iv ->
                signinByKakaotalk()
        }
    }

    private fun signinByKakaotalk() {
        val callback : (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e(TAG,"로그인 실패 $error")
            } else if (token != null) {
                UserApiClient.instance.me { user, error ->
                    val kakaoID = user!!.id
                    Log.d(TAG, "Kakao Login Succeed : $kakaoID")
                }
            }
        }

        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this@SigninActivity)) {
            UserApiClient.instance.loginWithKakaoTalk(this@SigninActivity, callback = callback)
        } else {
            UserApiClient.instance.loginWithKakaoAccount(this@SigninActivity, callback = callback)
        }
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun authenticate() {

    }

    private fun sendTokens() {

    }

    companion object {
        private const val TAG = "LoginActivity"
    }

}