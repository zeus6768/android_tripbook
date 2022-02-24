package com.olympos.tripbook.src.user

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.olympos.tripbook.R
import com.olympos.tripbook.config.BaseActivity
import com.olympos.tripbook.databinding.ActivityUserSigninBinding
import com.olympos.tripbook.src.home.MainActivity
import com.olympos.tripbook.src.user.model.UserService
import com.olympos.tripbook.src.user.model.UserView
import com.olympos.tripbook.utils.*
import com.olympos.tripbook.utils.ApplicationClass.Companion.TAG


class SigninActivity : BaseActivity(), UserView {
    val userService = UserService()

    private lateinit var binding: ActivityUserSigninBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserSigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signinSigninBtnIv.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        super.onClick(v)

        when (v!!.id) {
            R.id.signin_signin_btn_iv ->
                signinByKakaotalk()
        }
    }

    private fun signinByKakaotalk() {
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e("카카오 로그인 실패", error.toString())
            } else if (token != null) {
                Log.d("카카오 로그인 성공", ".")
                UserApiClient.instance.me { user, _ ->
                    if (user!!.kakaoAccount?.emailNeedsAgreement == true) {
                        requireEmailNeedsAgreement()
                    } else {
                        saveKakaoAccessToken(token.accessToken)
                        saveKakaoRefreshToken(token.refreshToken)

                        val tokens = HashMap<String, String>()
                        tokens["kakaoAccessToken"] = token.accessToken
                        tokens["kakaoRefreshToken"] = token.refreshToken
                        userService.kakaoSignin(tokens)

                        Log.d(
                            "check SPFs",
                            getUserIdx().toString() +
                                    "\nAccessToken: " + getAccessToken() +
                                    "\nRefreshToken: " + getRefreshToken()
                        )
                    }
                }
            }
        }
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this@SigninActivity)) {
            UserApiClient.instance.loginWithKakaoTalk(this@SigninActivity, callback = callback)
        } else {
            UserApiClient.instance.loginWithKakaoAccount(this@SigninActivity, callback = callback)
        }
    }

    private fun requireEmailNeedsAgreement() {
        var scopes = mutableListOf<String>()

        scopes.add("account_email")
        Log.d(TAG, "사용자에게 추가 동의를 받아야 합니다.")

        UserApiClient.instance.loginWithNewScopes(this@SigninActivity, scopes) { token, error ->
            if (error != null) {
                Log.e(TAG, "사용자 추가 동의 실패", error)
                Toast.makeText(this, "이메일 사용에 동의해주세요.", Toast.LENGTH_SHORT)
            } else {
                Log.d(TAG, "allowed scopes: ${token!!.scopes}")
                UserApiClient.instance.me { user, error ->
                    if (error != null) {
                        Log.e(TAG, "사용자 정보 요청 실패", error)
                    } else if (user != null) {
                        saveKakaoAccessToken(token.accessToken)
                        saveKakaoRefreshToken(token.refreshToken)

                        val tokens = HashMap<String, String>()
                        tokens["kakaoAccessToken"] = token.accessToken
                        tokens["kakaoRefreshToken"] = token.refreshToken
                        userService.kakaoSignin(tokens)

                        Log.i(TAG, "사용자 정보 요청 성공")
                    }
                }
            }
        }
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun autoSigninSuccess() {
        startMainActivity()
    }

    override fun autoSigninFailure(code: Int) {
        when (code) {
            1504, 1507, 1509 -> {
                val refreshToken = getRefreshToken()
                if (refreshToken != null) {
                    userService.updateAccessToken(refreshToken)
                }
            }
            2052 -> {
                val kakaoAccessToken = getKakaoAccessToken()
                if (kakaoAccessToken != null) {
                    userService.signUpUser(kakaoAccessToken)
                }
            }
        }
    }

    override fun signUpUserSuccess() {
        val kakaoAccessToken = getKakaoAccessToken()
        if (kakaoAccessToken != null) {
            userService.signUpProfile(kakaoAccessToken)
        }
    }

    override fun signUpUserFailure(code: Int) {
        TODO("Not yet implemented")
    }

    override fun signUpProfileSuccess() {
        val accessToken = getAccessToken()
        if (accessToken != null) {
            userService.autoSignin(accessToken)
        }
    }

    override fun signUpProfileFailure(code: Int) {
        TODO("Not yet implemented")
    }

    override fun kakaoSigninSuccess() {
        val accessToken =  getAccessToken()
        if (accessToken != null) {
            userService.autoSignin(accessToken)
        }
    }

    override fun kakaoSigninFailure(code: Int) {
        when (code) {
            2052 -> {
                val kakaoAccessToken = getKakaoAccessToken()
                if (kakaoAccessToken != null) {
                    userService.signUpUser(kakaoAccessToken)
                }
            }
        }
    }

    override fun updateKakaoAccessTokenSuccess() {
        TODO("실행조건, 목적지 명세 필요")
    }

    override fun updateKakaoAccessTokenFailure(code: Int) {
        TODO("실행조건, 목적지 명세 필요")
    }

    override fun updateProfileSuccess() {
        TODO("실행조건, 목적지 명세 필요")
    }

    override fun updateProfileFailure(code: Int) {
        TODO("실행조건, 목적지 명세 필요")
    }

    override fun updateAccessTokenSuccess() {
        val accessToken = getAccessToken()
        if (accessToken != null) {
            userService.autoSignin(accessToken)
        }
    }

    override fun updateAccessTokenFailure(code: Int) {
        TODO("SigninActivity 유지")
    }
}