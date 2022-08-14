package com.olympos.tripbook.src.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.olympos.tripbook.databinding.ActivitySplashBinding
import com.olympos.tripbook.src.user.SignInActivity
import com.olympos.tripbook.src.user.controller.UserAuthApiController
import com.olympos.tripbook.src.user.view.UserAuthView
import com.olympos.tripbook.utils.*

class SplashActivity : AppCompatActivity(), UserAuthView {

    private val userAuthApiController = UserAuthApiController()

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)

        setContentView(binding.root)

        userAuthApiController.setUserView(this)

        setPermission()
    }

    private fun setPermission() {
        val permissionListener = object : PermissionListener {
            override fun onPermissionGranted() {
                selectActivity()
            }
            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                Toast.makeText(this@SplashActivity, "권한 허용 후 다시 시도해주세요", Toast.LENGTH_SHORT).show()
                ActivityCompat.finishAffinity(this@SplashActivity)
            }
        }

        TedPermission.with(this)
            .setPermissionListener(permissionListener)
            .setRationaleMessage("사진 액세스 권한 허용 후 트립북을 이용하실 수 있습니다.")
            .setDeniedMessage("권한 요청 거부")
            .setPermissions(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            .check()
    }

    private fun selectActivity() {
        Log.d("SplashActivity", " \nselectActivity()" +
                "\nuserIdx: " + getUserIdx() +
                "\nAT: " + getAccessToken() +
                "\nRT: " + getRefreshToken() +
                "\nKAT: " + getKakaoAccessToken() +
                "\nKRT: " + getKakaoRefreshToken()
        )
        val accessToken = getAccessToken()
        if (accessToken != null) {
            userAuthApiController.autoSignin()
        } else {
            startSigninActivity()
        }
    }

    private fun startSigninActivity() {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
        },1500)
    }

    private fun startMainActivity() {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        },1500)
    }

    override fun autoSignInSuccess() {
        Log.d("SplashActivity", " \nautoSigninSuccess()" +
                "\nuserIdx: " + getUserIdx() +
                "\nAT: " + getAccessToken() +
                "\nRT: " + getRefreshToken() +
                "\nKAT: " + getKakaoAccessToken() +
                "\nKRT: " + getKakaoRefreshToken()
        )
        val kakaoAccessToken = getKakaoAccessToken()
        if (kakaoAccessToken != null) {
            val token = HashMap<String, String>()
            token["kakaoAccessToken"] = kakaoAccessToken
            userAuthApiController.updateProfile(token, getUserIdx())
            startMainActivity()
        } else {
            startSigninActivity()
        }
    }

    override fun autoSignInFailure(code: Int) {
        Log.e("SplashActivity", "autoSigninFailure() status code $code")
        when (code) {
            1504, 1507, 1509 -> {
                val refreshToken = getRefreshToken()
                val userIdx = getUserIdx()
                if (refreshToken != null && userIdx != 0) {
                    val tokens = HashMap<String, String>()
                    tokens["refreshToken"] = refreshToken
                    userAuthApiController.updateAccessToken(tokens, userIdx)
                } else {
                    startSigninActivity()
                }
            }
            3007 -> {
                startSigninActivity()
                Toast.makeText(this, "로그아웃되었습니다. 재로그인 해주세요.", Toast.LENGTH_SHORT).show()
            }
            else -> { // Destroy app on unexpected error
                Log.e("SplashActivity", "autoSigninFailure() Unexpected status code $code")
                Toast.makeText(this@SplashActivity, "로그인 에러", Toast.LENGTH_SHORT).show()
                ActivityCompat.finishAffinity(this@SplashActivity)
            }
        }
    }

    override fun signUpUserSuccess() {
        Log.d("SplashActivity", "signUpUserSuccess()")
        val kakaoAccessToken = getKakaoAccessToken()
        if (kakaoAccessToken != null) {
            val token = HashMap<String, String>()
            token["kakaoAccessToken"] = kakaoAccessToken
            userAuthApiController.signUpProfile(token)
        } else {
            startSigninActivity()
        }
    }

    override fun signUpUserFailure(code: Int) {
        Log.e("SplashActivity", "signUpUserFailure() status code $code")
        startSigninActivity()
    }

    override fun signUpProfileSuccess() {
        Log.d("SplashActivity", "signUpProfileSuccess()")
        val accessToken = getAccessToken()
        val refreshToken = getRefreshToken()
        if (accessToken != null && refreshToken != null) {
            val tokens = HashMap<String, String>()
            tokens["accessToken"] = accessToken
            tokens["refreshToken"] = refreshToken
            userAuthApiController.kakaoSignin(tokens)
        } else {
            startSigninActivity()
        }
    }

    override fun signUpProfileFailure(code: Int) {
        Log.e("SplashActivity", "signUpProfileFailure() status code $code")
        startSigninActivity()
    }

    override fun kakaoSignInSuccess() {
        val kat = getKakaoAccessToken()
        val krt = getKakaoRefreshToken()
        Log.d("SplashActivity", "kakaoSigninSuccess()")
        Log.d("SplashActivity", " \nKAT: $kat \nKRT: $krt")
        val accessToken = getAccessToken()
        if (accessToken != null) {
            userAuthApiController.autoSignin()
        } else {
            startSigninActivity()
        }
    }

    override fun kakaoSignInFailure(code: Int) {
        Log.e("SplashActivity", "kakaoSigninFailure() status code $code")
        when (code) {
            2052 -> {
                val kakaoAccessToken = getKakaoAccessToken()
                if (kakaoAccessToken != null) {
                    val token = HashMap<String, String>()
                    token["kakaoAccessToken"] = kakaoAccessToken
                    userAuthApiController.signUpUser(token)
                } else {
                    startSigninActivity()
                }
            }
            2057 -> {
                val userIdx = getUserIdx()
                val kakaoRefreshToken = getKakaoRefreshToken()
                if (kakaoRefreshToken != null && userIdx != 0) {
                    val token = HashMap<String, String>()
                    token["kakaoRefreshToken"] = kakaoRefreshToken
                    userAuthApiController.updateKakaoAccessToken(token, userIdx)
                } else {
                    startSigninActivity()
                }
            }
            else -> startSigninActivity()
        }
    }

    override fun updateKakaoAccessTokenSuccess() {
        Log.d("SplashActivity", "updateKakaoAccessTokenSuccess()")
        val kakaoAccessToken = getKakaoAccessToken()
        val kakaoRefreshToken = getKakaoRefreshToken()
        if (kakaoAccessToken != null && kakaoRefreshToken != null) {
            val tokens = HashMap<String, String>()
            tokens["kakaoAccessToken"] = kakaoAccessToken
            tokens["kakaoRefreshToken"] = kakaoRefreshToken
            userAuthApiController.kakaoSignin(tokens)
        } else {
            startSigninActivity()
        }
    }

    override fun updateKakaoAccessTokenFailure(code: Int) {
        Log.e("SplashActivity", "updateKakaoAccessTokenFailure() status code $code")
        startSigninActivity()
    }

    override fun updateProfileSuccess() {
        Log.d("SplashActivity", "updateProfileSuccess()")
        userAuthApiController.getProfile(getUserIdx())
    }

    override fun updateProfileFailure(code: Int) {
        Log.e("SplashActivity", "updateProfileFailure() status code $code")
    }

    override fun getProfileSuccess() {
        val nickname = getNickname()
        val userImg = getUserImage()
        Log.d("SplashActivity", "getProfileSuccess()")
        Log.d("SplashActivity", "nickname: $nickname, userImg: $userImg")
    }

    override fun getProfileFailure(code: Int) {
        Log.e("SplashActivity", "getProfileFailure() status code $code")
        Toast.makeText(this, "프로필 업데이트에 실패했습니다.", Toast.LENGTH_SHORT).show()
        startSigninActivity()
    }

    override fun updateAccessTokenSuccess() {
        Log.d("SplashActivity", "updateAccessTokenSuccess()")
        val accessToken = getAccessToken()
        if (accessToken != null) {
            userAuthApiController.autoSignin()
        } else {
            startSigninActivity()
        }
    }

    override fun updateAccessTokenFailure(code: Int) {
        Log.e("SplashActivity", "updateAccessTokenFailure() status code $code")
        when (code) {
            1505, 1509 -> {
                val kakaoAccessToken = getKakaoAccessToken()
                val kakaoRefreshToken = getKakaoRefreshToken()
                if (kakaoAccessToken != null && kakaoRefreshToken != null) {
                    val tokens = HashMap<String, String>()
                    tokens["kakaoAccessToken"] = kakaoAccessToken
                    tokens["kakaoRefreshToken"] = kakaoRefreshToken
                    userAuthApiController.kakaoSignin(tokens)
                } else {
                    startSigninActivity()
                }
            }
            else -> startSigninActivity()
        }
    }
}
