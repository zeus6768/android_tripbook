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
import com.olympos.tripbook.src.user.model.UserService
import com.olympos.tripbook.utils.ApplicationClass.Companion.TAG
import com.olympos.tripbook.utils.getJwt
import com.olympos.tripbook.utils.saveJwt

class SigninActivity : BaseActivity() {
    private lateinit var binding: ActivityUserSigninBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserSigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signinSigninBtnIv.setOnClickListener(this)

        val userService = UserService()
        userService.getApiTest()
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
                Log.d(TAG, "로그인 성공")
                // UserApiClient가 아닌 Tripbook Server에서 값을 받아와야 함
                UserApiClient.instance.me { user, _ ->
                    val kakaoID = user!!.id
                    Log.d("카카오 user email", user.kakaoAccount?.profile.toString())
                    Log.d("카카오 user nickname", user.kakaoAccount?.profile?.nickname.toString())
                    kakaoID?.let { saveJwt(this, it.toString()) }
                    val userid = getJwt(this)
                    Log.d("JWT 저장", userid.toString())
//                    saveAccessToken(this, token.accessToken)
//                    saveRefreshToken(this, token.refreshToken)
                    sendAccessToken()
                    sendRefreshToken()
                    startMainActivity()
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



    private fun sendAccessToken() {

    }

    private fun sendRefreshToken() {

    }

}