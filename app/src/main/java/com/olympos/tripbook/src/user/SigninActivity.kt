package com.olympos.tripbook.src.user

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.user.model.User
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
                Log.e("카카오 로그인 실패", error.toString())
            } else if (token != null) {
                Log.d("카카오 로그인 성공", ".")
                UserApiClient.instance.me { user, _ ->
                    Log.d("카카오 user", user.toString())
                    Log.d("카카오 user", user?.kakaoAccount.toString())
                    Log.d("카카오 user email", user?.kakaoAccount?.profile.toString())
                    Log.d("카카오 user nickname", user?.kakaoAccount?.profile?.nickname.toString())
                    startMainActivity()
                    finish()
                }
            }
        }

        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this@SigninActivity)) {
            UserApiClient.instance.loginWithKakaoTalk(this@SigninActivity, callback = callback)
        } else {
            UserApiClient.instance.loginWithKakaoAccount(this@SigninActivity, callback = callback)
        }
    }

    private fun requireEmailNeedsAgreement(user: User): Boolean {
        var result = false
        var scopes = mutableListOf<String>()
        if (user.kakaoAccount?.emailNeedsAgreement == true) {
            Log.d(TAG, "사용자에게 추가 동의를 받아야 합니다.")
            scopes.add("account_email")
        }
        UserApiClient.instance.loginWithNewScopes(this@SigninActivity, scopes) { token, error ->
            if (error != null) {
                Log.e(TAG, "사용자 추가 동의 실패", error)
            } else {
                Log.d(TAG, "allowed scopes: ${token!!.scopes}")

                UserApiClient.instance.me { user, error ->
                    if (error != null) {
                        Log.e(TAG, "사용자 정보 요청 실패", error)
                    } else if (user != null) {
                        Log.i(TAG, "사용자 정보 요청 성공")
                        var result = true
                    }
                }
            }
        }
        return result
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}