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
import com.olympos.tripbook.databinding.ActivityUserSignInBinding
import com.olympos.tripbook.src.home.MainActivity
import com.olympos.tripbook.src.user.controller.UserAuthApiController
import com.olympos.tripbook.src.user.view.UserAuthView
import com.olympos.tripbook.utils.*
import com.olympos.tripbook.utils.ApplicationClass.Companion.TAG


class SignInActivity : BaseActivity(), UserAuthView {

    private val userAuthApiController = UserAuthApiController()

    private lateinit var binding: ActivityUserSignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityUserSignInBinding.inflate(layoutInflater)

        binding.signinSigninBtnIv.setOnClickListener(this)

        setContentView(binding.root)

        userAuthApiController.setUserView(this)

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.signin_signin_btn_iv -> signInByKakaoTalk()
        }
    }

    private fun signInByKakaoTalk() {
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e("SignInActivity", error.toString())
            } else if (token != null) {
                Log.d("SignInActivity", "SignInByKakaoTalk()")
                UserApiClient.instance.me { user, _ ->
                    if (user!!.kakaoAccount?.emailNeedsAgreement == true) {
                        requireEmailNeedsAgreement()
                    } else {
                        saveNickname(user.kakaoAccount!!.profile!!.nickname!!)
                        saveKakaoAccessToken(token.accessToken)
                        saveKakaoRefreshToken(token.refreshToken)

                        val tokens = HashMap<String, String>()
                        tokens["kakaoAccessToken"] = token.accessToken
                        tokens["kakaoRefreshToken"] = token.refreshToken
                        userAuthApiController.kakaoSignIn(tokens)
                    }
                }
            }
        }
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this@SignInActivity)) {
            UserApiClient.instance.loginWithKakaoTalk(this@SignInActivity, callback = callback)
        } else {
            UserApiClient.instance.loginWithKakaoAccount(this@SignInActivity, callback = callback)
        }
    }

    @Suppress("NAME_SHADOWING")
    private fun requireEmailNeedsAgreement() {

        val scopes = mutableListOf<String>()

        scopes.add("account_email")
        Log.d(TAG, "사용자에게 추가 동의를 받아야 합니다.")

        UserApiClient.instance.loginWithNewScopes(this@SignInActivity, scopes) { token, error ->
            if (error != null) {
                Log.e(TAG, "사용자 추가 동의 실패", error)
                Toast.makeText(this, "이메일 사용에 동의해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                Log.d(TAG, "allowed scopes: ${token!!.scopes}")
                UserApiClient.instance.me { user, error ->
                    if (error != null) {
                        Log.e(TAG, "사용자 정보 요청 실패", error)
                    } else if (user != null) {
                        saveNickname(user.kakaoAccount!!.profile!!.nickname!!)
                        saveKakaoAccessToken(token.accessToken)
                        saveKakaoRefreshToken(token.refreshToken)

                        val tokens = HashMap<String, String>()
                        tokens["kakaoAccessToken"] = token.accessToken
                        tokens["kakaoRefreshToken"] = token.refreshToken
                        userAuthApiController.kakaoSignIn(tokens)
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

    override fun autoSignInSuccess() {

        Log.d("SignInActivity", " \nautoSignInSuccess()" +
                "\nuserIdx: " + getUserIdx() +
                "\nAT: " + getAccessToken() +
                "\nRT: " + getRefreshToken() +
                "\nKAT: " + getKakaoAccessToken() +
                "\nKRT: " + getKakaoRefreshToken()
        )
        startMainActivity()

    }

    override fun autoSignInFailure(code: Int) {

        Log.e("SignInActivity", "autoSignInFailure() status code: $code")
        when (code) {
            1504, 1507, 1509 -> {
                val refreshToken = getRefreshToken()
                val userIdx = getUserIdx()
                if (refreshToken != null && userIdx != 0) {
                    val tokens = HashMap<String, String>()
                    tokens["refreshToken"] = refreshToken
                    userAuthApiController.updateAccessToken(tokens, userIdx)
                }
            }
        }

    }

    override fun signUpUserSuccess() {

        Log.d("SignInActivity", "signUpUserSuccess()")
        val kakaoAccessToken = getKakaoAccessToken()
        if (kakaoAccessToken != null) {
            val token = HashMap<String, String>()
            token["kakaoAccessToken"] = kakaoAccessToken
            userAuthApiController.signUpProfile(token)
        }

    }

    override fun signUpUserFailure(code: Int) {
        Log.e("SignInActivity", "signUpUserFailure() status code $code")
    }

    override fun signUpProfileSuccess() {

        Log.d("SignInActivity", "signUpProfileSuccess()")
        val kakaoAccessToken = getKakaoAccessToken()
        val kakaoRefreshToken = getKakaoRefreshToken()
        Log.e("SignInActivity", " \nKAT: $kakaoAccessToken \nKRT: $kakaoRefreshToken")
        if (kakaoAccessToken != null && kakaoRefreshToken != null) {
            val tokens = HashMap<String, String>()
            tokens["accessToken"] = kakaoAccessToken
            tokens["refreshToken"] = kakaoRefreshToken
            userAuthApiController.kakaoSignIn(tokens)
        }

    }

    override fun signUpProfileFailure(code: Int) {
        Log.e("SignInActivity", "signUpProfileFailure() status code $code")
    }

    override fun kakaoSignInSuccess() {

        val kat = getKakaoAccessToken()
        val krt = getKakaoRefreshToken()
        Log.d("SignInActivity", "kakaoSignInSuccess()")
        Log.d("SignInActivity", " \nKAT: $kat \nKRT: $krt")

        val accessToken = getAccessToken()

        if (accessToken != null) {
            userAuthApiController.autoSignIn()
        }

    }

    override fun kakaoSignInFailure(code: Int) {

        Log.e("SignInActivity", "kakaoSignInFailure() status code $code")
        when (code) {

            2050 -> {
                val kakaoAccessToken = getKakaoAccessToken()
                val kakaoRefreshToken = getKakaoRefreshToken()
                if (kakaoAccessToken != null && kakaoRefreshToken != null) {
                    val token = HashMap<String, String>()
                    token["kakaoAccessToken"] = kakaoAccessToken
                    token["kakaoRefreshToken"] = kakaoRefreshToken
                    userAuthApiController.kakaoSignIn(token)
                }
            }

            2052 -> {
                val kakaoAccessToken = getKakaoAccessToken()
                if (kakaoAccessToken != null) {
                    val token = HashMap<String, String>()
                    token["kakaoAccessToken"] = kakaoAccessToken
                    userAuthApiController.signUpUser(token)
                }
            }

            2057 -> {
                val userIdx = getUserIdx()
                val kakaoRefreshToken = getKakaoRefreshToken()
                if (kakaoRefreshToken != null) {
                    val token = HashMap<String, String>()
                    token["kakaoRefreshToken"] = kakaoRefreshToken
                    userAuthApiController.updateKakaoAccessToken(token, userIdx)
                }
            }
        }

    }

    override fun updateKakaoAccessTokenSuccess() {

        Log.d("SignInActivity", "updateKakaoAccessTokenSuccess()")
        val kakaoAccessToken = getKakaoAccessToken()
        val kakaoRefreshToken = getKakaoRefreshToken()
        if (kakaoAccessToken != null && kakaoRefreshToken != null) {
            val tokens = HashMap<String, String>()
            tokens["kakaoAccessToken"] = kakaoAccessToken
            tokens["kakaoRefreshToken"] = kakaoRefreshToken
            userAuthApiController.kakaoSignIn(tokens)
        }

    }

    override fun updateKakaoAccessTokenFailure(code: Int) {
        Log.e("SignInActivity", "updateKakaoAccessTokenFailure() status code $code")
    }

    override fun updateProfileSuccess() {
        Log.d("SignInActivity", "updateProfileSuccess()")
        userAuthApiController.getProfile(getUserIdx())
    }

    override fun updateProfileFailure(code: Int) {
        Log.e("SignInActivity", "updateProfileFailure() status code $code")
    }

    override fun getProfileSuccess() {

        val nickname = getNickname()
        val userImg = getUserImage()
        Log.d("SignInActivity", "getProfileSuccess()")
        Log.d("SignInActivity", "nickname: $nickname, userImg: $userImg")

    }

    override fun getProfileFailure(code: Int) {
        Log.e("SignInActivity", "getProfileFailure() status code $code")
        Toast.makeText(this, "프로필 업데이트 실패", Toast.LENGTH_SHORT).show()
    }

    override fun updateAccessTokenSuccess() {

        Log.d("SignInActivity", "updateAccessTokenSuccess()")
        val accessToken = getAccessToken()
        if (accessToken != null) {
            userAuthApiController.autoSignIn()
        }

    }

    override fun updateAccessTokenFailure(code: Int) {

        Log.e("SignInActivity", "updateAccessTokenFailure() status code $code")
        when (code) {
            1505, 1509 -> {
                val kakaoAccessToken = getKakaoAccessToken()
                val kakaoRefreshToken = getKakaoRefreshToken()
                if (kakaoAccessToken != null && kakaoRefreshToken != null) {
                    val tokens = HashMap<String, String>()
                    tokens["kakaoAccessToken"] = kakaoAccessToken
                    tokens["kakaoRefreshToken"] = kakaoRefreshToken
                    userAuthApiController.kakaoSignIn(tokens)
                }
            }
        }
    }

}