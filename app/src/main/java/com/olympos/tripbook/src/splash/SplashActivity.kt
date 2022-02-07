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
import com.olympos.tripbook.utils.ApplicationClass
import com.olympos.tripbook.utils.getJwt

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
        val jwt = getJwt(this)
        // 서버에 request를 보내 로그인 알림
        // response로 isValidToken, nickname, thumbnailImage URL 수신
        // isValidToken이 false면 nickname, thumbnailImage URL는 null
        // isValidToken이 false면 SigninActivity, true면 MainActivity
        Log.d("GET JWT RESULT", jwt.toString())
        if (jwt != "") {
            startMainActivity()
        } else {
            startSigninActivity()
        }
    }

    private fun sendSigninRequest() {
        val jwt = getJwt(this)

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
}
