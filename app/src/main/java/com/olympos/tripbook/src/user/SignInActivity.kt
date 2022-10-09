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

                        val profile = HashMap<String, String?>()
                        profile["nickname"] = user.kakaoAccount!!.profile!!.nickname!!
                        profile["profileImageUrl"] = user.kakaoAccount!!.profile!!.profileImageUrl!!

                        val kakaoTokens = HashMap<String, String?>()
                        kakaoTokens["kakaoAccessToken"] = token.accessToken
                        kakaoTokens["kakaoRefreshToken"] = token.refreshToken

                        userAuthApiController.kakaoSignIn(profile, kakaoTokens)
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

                        val profile = HashMap<String, String?>()
                        profile["nickname"] = user.kakaoAccount!!.profile!!.nickname!!
                        profile["profileImageUrl"] = user.kakaoAccount!!.profile!!.profileImageUrl!!

                        val tokens = HashMap<String, String?>()
                        tokens["kakaoAccessToken"] = token.accessToken
                        tokens["kakaoRefreshToken"] = token.refreshToken

                        userAuthApiController.kakaoSignIn(profile, tokens)

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

        Log.d("SignInActivity", " autoSignInSuccess()" +
                "\nuserIdx: " + getUserIdx() +
                "\nprofileImage: " + getUserImage() +
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
                val tokens = HashMap<String, String?>()
                tokens["refreshToken"] = getRefreshToken()
                userAuthApiController.updateAccessToken(tokens, getUserIdx())
            }
        }

    }

    override fun signUpUserSuccess() {

        Log.d("SignInActivity", "signUpUserSuccess()")

        val token = HashMap<String, String?>()
        token["kakaoAccessToken"] = getKakaoAccessToken()
        userAuthApiController.signUpProfile(token)

    }

    override fun signUpUserFailure(code: Int) {
        Log.e("SignInActivity", "signUpUserFailure() status code $code")
    }

    override fun signUpProfileSuccess() {

        Log.d("SignInActivity", "signUpProfileSuccess()")
        Log.e("SignInActivity", " \nKAT: " + getKakaoAccessToken() + "\nKRT: " + getKakaoRefreshToken())

        val profile = HashMap<String, String?>()
        profile["nickname"] = getNickname()
        profile["profileImageUrl"] = getUserImage()

        val tokens = HashMap<String, String?>()
        tokens["kakaoAccessToken"] = getKakaoAccessToken()
        tokens["kakaoRefreshToken"] = getKakaoRefreshToken()
        userAuthApiController.kakaoSignIn(profile, tokens)

    }

    override fun signUpProfileFailure(code: Int) {
        Log.e("SignInActivity", "signUpProfileFailure() status code $code")
    }

    override fun kakaoSignInSuccess() {
        Log.d("SignInActivity", "kakaoSignInSuccess()")
        userAuthApiController.autoSignIn()
    }

    override fun kakaoSignInFailure(code: Int) {

        Log.e("SignInActivity", "kakaoSignInFailure() status code $code")
        when (code) {

            2050 -> {

                val profile = HashMap<String, String?>()
                profile["nickname"] = getNickname()
                profile["profileImageUrl"] = getUserImage()

                val tokens = HashMap<String, String?>()
                tokens["kakaoAccessToken"] = getKakaoAccessToken()
                tokens["kakaoRefreshToken"] = getKakaoRefreshToken()

                userAuthApiController.kakaoSignIn(profile, tokens)

            }

            2052 -> {
                val token = HashMap<String, String?>()
                token["kakaoAccessToken"] = getKakaoAccessToken()
                userAuthApiController.signUpUser(token)
            }

            2057 -> {
                val token = HashMap<String, String?>()
                token["kakaoRefreshToken"] = getKakaoRefreshToken()
                userAuthApiController.updateKakaoAccessToken(token, getUserIdx())
            }
        }

    }

    override fun updateKakaoAccessTokenSuccess() {

        Log.d("SignInActivity", "updateKakaoAccessTokenSuccess()")

        val profile = HashMap<String, String?>()
        profile["nickname"] = getNickname()
        profile["profileImageUrl"] = getUserImage()

        val tokens = HashMap<String, String?>()
        tokens["kakaoAccessToken"] = getKakaoAccessToken()
        tokens["kakaoRefreshToken"] = getKakaoRefreshToken()
        userAuthApiController.kakaoSignIn(profile, tokens)

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
        Log.d("SignInActivity", "getProfileSuccess()")
        Log.d("SignInActivity", "nickname: " + getNickname() + "userImg: " + getUserImage())
    }

    override fun getProfileFailure(code: Int) {
        Log.e("SignInActivity", "getProfileFailure() status code $code")
        Toast.makeText(this, "프로필 업데이트 실패", Toast.LENGTH_SHORT).show()
    }

    override fun updateAccessTokenSuccess() {
        Log.d("SignInActivity", "updateAccessTokenSuccess()")
        userAuthApiController.autoSignIn()
    }

    override fun updateAccessTokenFailure(code: Int) {

        Log.e("SignInActivity", "updateAccessTokenFailure() status code $code")

        when (code) {

            1505, 1509 -> {

                val profile = HashMap<String, String?>()
                profile["nickname"] = getNickname()
                profile["profileImageUrl"] = getUserImage()

                val tokens = HashMap<String, String?>()
                tokens["kakaoAccessToken"] = getKakaoAccessToken()
                tokens["kakaoRefreshToken"] = getKakaoRefreshToken()

                userAuthApiController.kakaoSignIn(profile, tokens)

            }
        }

    }
}