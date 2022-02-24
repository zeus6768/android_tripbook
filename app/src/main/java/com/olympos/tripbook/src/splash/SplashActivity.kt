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
import com.olympos.tripbook.utils.getAccessToken
import com.olympos.tripbook.utils.getKakaoAccessToken
import com.olympos.tripbook.utils.getRefreshToken
import com.olympos.tripbook.utils.saveUserIdx
import java.util.function.ToDoubleBiFunction

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
            userService.autoSignin(accessToken)
        } else {
            startMainActivity()
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
        startMainActivity()
    }

    override fun autoSigninFailure(code: Int) {
        when (code) {
            1504, 1507, 1509 -> {
                val refreshToken = getRefreshToken()
                if (refreshToken != null) {
                    userService.updateAccessToken(refreshToken)
                } else {
                    startSigninActivity()
                }
            }
            2052 -> {
                val kakaoAccessToken = getKakaoAccessToken()
                if (kakaoAccessToken != null) {
                    userService.signUpUser(kakaoAccessToken)
                } else {
                    startSigninActivity()
                }
            }
            3007 -> startSigninActivity()
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
        TODO("Not yet implemented")
    }

    override fun kakaoSigninFailure(code: Int) {
        TODO("Not yet implemented")
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
        startSigninActivity()
    }
}
