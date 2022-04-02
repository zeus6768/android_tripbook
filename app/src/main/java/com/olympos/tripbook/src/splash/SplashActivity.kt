package com.olympos.tripbook.src.splash

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
import com.olympos.tripbook.src.home.MainActivity
import com.olympos.tripbook.src.user.SigninActivity
import com.olympos.tripbook.src.user.model.UserService
import com.olympos.tripbook.src.user.model.UserView
import com.olympos.tripbook.utils.*

class SplashActivity : AppCompatActivity(), UserView {
    private val userService = UserService()
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userService.setUserView(this)

        settingPermission()
    }

    private fun settingPermission() {
        var permissionListener = object : PermissionListener {
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
        Log.d("SplashActivity.kt", "selectActivity()")
        val accessToken = getAccessToken()
        if (accessToken != null) {
            userService.autoSignin()
        } else {
            startSigninActivity()
        }
    }

    private fun startSigninActivity() {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, SigninActivity::class.java)
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


    override fun autoSigninSuccess() {
        Log.d("SplashActivity.kt", "autoSigninSuccess()")
        Log.d("SplashActivity.kt", " \nAT: " + getAccessToken())

        val kakaoAccessToken = getKakaoAccessToken()
        if (kakaoAccessToken != null) {
            val token = HashMap<String, String>()
            token["kakaoAccessToken"] = kakaoAccessToken
            userService.updateProfile(token, getUserIdx())
            startMainActivity()
        } else {
            startSigninActivity()
        }
    }

    override fun autoSigninFailure(code: Int) {
        Log.e("SplashActivity.kt", "autoSigninFailure() status code $code")
        when (code) {
            1504, 1507, 1509 -> {
                val refreshToken = getRefreshToken()
                val userIdx = getUserIdx()
                if (refreshToken != null && userIdx != 0) {
                    val tokens = HashMap<String, String>()
                    tokens["refreshToken"] = refreshToken
                    userService.updateAccessToken(tokens, userIdx)
                } else {
                    startSigninActivity()
                }
            }
            3007 -> startSigninActivity()
            else -> {
                Log.e("SplashActivity.kt", "autoSigninFailure() Unexpected status code $code")
                Toast.makeText(this@SplashActivity, "로그인 에러", Toast.LENGTH_SHORT).show()
                ActivityCompat.finishAffinity(this@SplashActivity)
            }
        }
    }

    override fun signUpUserSuccess() {
        Log.d("SplashActivity.kt", "signUpUserSuccess()")
        val kakaoAccessToken = getKakaoAccessToken()
        if (kakaoAccessToken != null) {
            val token = HashMap<String, String>()
            token["kakaoAccessToken"] = kakaoAccessToken
            userService.signUpProfile(token)
        } else {
            startSigninActivity()
        }
    }

    override fun signUpUserFailure(code: Int) {
        Log.e("SplashActivity.kt", "signUpUserFailure() status code $code")
        startSigninActivity()
    }

    override fun signUpProfileSuccess() {
        Log.d("SplashActivity.kt", "signUpProfileSuccess()")
        val accessToken = getAccessToken()
        val refreshToken = getRefreshToken()
        if (accessToken != null && refreshToken != null) {
            val tokens = HashMap<String, String>()
            tokens["accessToken"] = accessToken
            tokens["refreshToken"] = refreshToken
            userService.kakaoSignin(tokens)
        } else {
            startSigninActivity()
        }
    }

    override fun signUpProfileFailure(code: Int) {
        Log.e("SplashActivity.kt", "signUpProfileFailure() status code $code")
        startSigninActivity()
    }

    override fun kakaoSigninSuccess() {
        val kat = getKakaoAccessToken()
        val krt = getKakaoRefreshToken()
        Log.d("SplashActivity.kt", "kakaoSigninSuccess()")
        Log.d("SplashActivity.kt", " \nKAT: $kat \nKRT: $krt")
        val accessToken = getAccessToken()
        if (accessToken != null) {
            userService.autoSignin()
        } else {
            startSigninActivity()
        }
    }

    override fun kakaoSigninFailure(code: Int) {
        Log.e("SplashActivity.kt", "kakaoSigninFailure() status code $code")
        when (code) {
            2052 -> {
                val kakaoAccessToken = getKakaoAccessToken()
                if (kakaoAccessToken != null) {
                    val token = HashMap<String, String>()
                    token["kakaoAccessToken"] = kakaoAccessToken
                    userService.signUpUser(token)
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
                    userService.updateKakaoAccessToken(token, userIdx)
                } else {
                    startSigninActivity()
                }
            }
            else -> startSigninActivity()
        }
    }

    override fun updateKakaoAccessTokenSuccess() {
        Log.d("SplashActivity.kt", "updateKakaoAccessTokenSuccess()")
        val kakaoAccessToken = getKakaoAccessToken()
        val kakaoRefreshToken = getKakaoRefreshToken()
        if (kakaoAccessToken != null && kakaoRefreshToken != null) {
            val tokens = HashMap<String, String>()
            tokens["kakaoAccessToken"] = kakaoAccessToken
            tokens["kakaoRefreshToken"] = kakaoRefreshToken
            userService.kakaoSignin(tokens)
        } else {
            startSigninActivity()
        }
    }

    override fun updateKakaoAccessTokenFailure(code: Int) {
        Log.e("SplashActivity.kt", "updateKakaoAccessTokenFailure() status code $code")
        startSigninActivity()
    }

    override fun updateProfileSuccess() {
        Log.d("SplashActivity.kt", "updateProfileSuccess()")
        userService.getProfile(getUserIdx())
    }

    override fun updateProfileFailure(code: Int) {
        Log.e("SplashActivity.kt", "updateProfileFailure() status code $code")
    }

    override fun getProfileSuccess() {
        val nickname = getNickname()
        val userImg = getUserImage()
        Log.d("SplashActivity.kt", "getProfileSuccess()")
        Log.d("SplashActivity.kt", "nickname: $nickname, userImg: $userImg")
    }

    override fun getProfileFailure(code: Int) {
        Log.e("SplashActivity.kt", "getProfileFailure() status code $code")
        Toast.makeText(this, "프로필 업데이트에 실패했습니다.", Toast.LENGTH_SHORT).show()
    }

    override fun updateAccessTokenSuccess() {
        Log.d("SplashActivity.kt", "updateAccessTokenSuccess()")
        val accessToken = getAccessToken()
        if (accessToken != null) {
            userService.autoSignin()
        } else {
            startSigninActivity()
        }
    }

    override fun updateAccessTokenFailure(code: Int) {
        Log.e("SplashActivity.kt", "updateAccessTokenFailure() status code $code")
        when (code) {
            1505 -> {
                val kakaoAccessToken = getKakaoAccessToken()
                val kakaoRefreshToken = getKakaoRefreshToken()
                if (kakaoAccessToken != null && kakaoRefreshToken != null) {
                    val tokens = HashMap<String, String>()
                    tokens["kakaoAccessToken"] = kakaoAccessToken
                    tokens["kakaoRefreshToken"] = kakaoRefreshToken
                    userService.kakaoSignin(tokens)
                } else {
                    startSigninActivity()
                }
            }
            else -> startSigninActivity()
        }
    }
}
