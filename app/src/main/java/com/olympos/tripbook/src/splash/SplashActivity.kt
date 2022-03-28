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
                ActivityCompat.finishAffinity(this@SplashActivity) // 권한 거부시 앱 종료
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
        Log.d("SplashActivity.kt", "autoSignin() Success")
        startMainActivity()
    }

    override fun autoSigninFailure(code: Int) {
        Log.e("SplashActivity.kt", "autoSigninFailure() status code $code")
        when (code) {
            1504, 1507, 1509 -> {
                val tokens = HashMap<String, String>()
                val refreshToken = getRefreshToken()
                val userIdx = getUserIdx()
                if (refreshToken != null && userIdx != 0) {
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
        TODO("Not yet implemented")
    }

    override fun signUpUserFailure(code: Int) {
        TODO("Not yet implemented")
    }

    override fun signUpProfileSuccess() {
        TODO("Not yet implemented")
    }

    override fun signUpProfileFailure(code: Int) {
        TODO("Not yet implemented")
    }

    override fun kakaoSigninSuccess() {
        Log.d("SigninActivity.kt", "kakaoSigninSuccess()")
        val accessToken = getAccessToken()
        if (accessToken != null) {
            userService.autoSignin()
        }
    }

    override fun kakaoSigninFailure(code: Int) {
        Log.e("SigninActivity.kt", "kakaoSigninFailure() status code $code")
        startSigninActivity()
    }

    override fun updateKakaoAccessTokenSuccess() {
        TODO("Not yet implemented")
    }

    override fun updateKakaoAccessTokenFailure(code: Int) {
        TODO("Not yet implemented")
    }

    override fun updateProfileSuccess() {
        Log.d("SigninActivity.kt", "updateProfileSuccess()")
        val kakaoAccessToken = getKakaoAccessToken()
        val kakaoRefreshToken = getKakaoRefreshToken()
        if (kakaoAccessToken != null && kakaoRefreshToken != null) {
            val tokens = HashMap<String, String>()
            tokens["kakaoAccessToken"] = kakaoAccessToken
            tokens["kakaoRefreshToken"] = kakaoRefreshToken
            userService.kakaoSignin(tokens)
        }
    }

    override fun updateProfileFailure(code: Int) {
        Log.e("SplashActivity.kt", "updateProfileFailure() status code $code")
    }

    override fun updateAccessTokenSuccess() {
        Log.d("SplashActivity.kt", "updateAccessTokenSuccess()")
        val accessToken = getAccessToken()
        if (accessToken != null) {
            userService.autoSignin()
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
                }
            }
            else -> startSigninActivity()
        }
    }
}
