package com.olympos.tripbook.src.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.olympos.tripbook.databinding.ActivitySplashBinding
import com.olympos.tripbook.src.user.SigninActivity

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
                startSigninActivity()
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

    private fun startSigninActivity() {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        },1500) //1.5초 delay

        finish()
    }
}