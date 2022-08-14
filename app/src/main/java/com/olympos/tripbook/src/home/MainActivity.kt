package com.olympos.tripbook.src.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.olympos.tripbook.R
import com.olympos.tripbook.config.BaseActivity
import com.olympos.tripbook.databinding.ActivityMainBinding
import com.olympos.tripbook.src.trip.GetTripCountView
import com.olympos.tripbook.src.user.MyPageActivity
import com.olympos.tripbook.src.trip.TripActivity
import com.olympos.tripbook.src.trip.TripApiController
import com.olympos.tripbook.src.user.MyPastTripActivity
import com.olympos.tripbook.src.user.controller.UserAuthApiController
import com.olympos.tripbook.src.user.view.UserAuthView
import com.olympos.tripbook.utils.*


class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener, GetTripCountView,
    UserAuthView {
    private val homeService = TripApiController()
    private val userAuthApiController = UserAuthApiController()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        homeService.setGetTripCountView(this)
        userAuthApiController.setUserView(this)

        initView()

        binding.homeLeftDrawerBtn.setOnClickListener(this)
        binding.mainContentRecordBtnTv.setOnClickListener(this)
        binding.mainLeftNavigationView.setNavigationItemSelectedListener(this)
        binding.mainDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED) //스와이프 비활성화
    }

    override fun onResume() {
        super.onResume()
        homeService.getTripCount()
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {
        homeService.getTripCount()
        binding.mainUserNameTv.text = getNickname() + "님의 추억"

        //navigation view
        val navigationView = findViewById<View>(R.id.main_left_navigation_view) as NavigationView
        val headerView = navigationView.getHeaderView(0)
        val navUserName = headerView.findViewById<View>(R.id.main_drawer_header_name_tv) as TextView
        val navUserImg = headerView.findViewById<View>(R.id.main_drawer_header_profile_iv) as ImageView
        val navUserLogout = headerView.findViewById<View>(R.id.main_drawer_header_logout_tv) as TextView
        navUserName.text = getNickname() + "님"

        Glide.with(this)
            .load(getUserImage())
            .circleCrop()
            .placeholder(R.drawable.img_home_profile)
            .error(R.drawable.img_home_profile)
            .into(navUserImg)

        navUserLogout.setOnClickListener(this)
    }

    private fun initFragment() {
        supportFragmentManager.beginTransaction().replace(R.id.main_content_fl, HomeFragment())
            .commitAllowingStateLoss()
    }

    override fun onClick(v: View?) {
        super.onClick(v)

        when (v!!.id) {
            //왼쪽 드로어 open
            R.id.home_left_drawer_btn ->
                binding.mainDrawerLayout.openDrawer(GravityCompat.START)

            //여행 기록하기
            R.id.main_content_record_btn_tv ->
                startTripActivity()
            
            //로그아웃
            R.id.main_drawer_header_logout_tv ->
                userLogout()
        }
    }

    //navigation item 별 actions
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu0 -> startMyPageActivity()
            R.id.menu1 -> startMyPastTripActivity()
            R.id.menu2 -> Toast.makeText(this, "다이어리 테마", Toast.LENGTH_SHORT).show()
            R.id.menu3 -> Toast.makeText(this, "모두의 여행기", Toast.LENGTH_SHORT).show()
            R.id.menu4 -> Toast.makeText(this, "새로운 소식", Toast.LENGTH_SHORT).show()
            R.id.menu5 -> Toast.makeText(this, "이용방법", Toast.LENGTH_SHORT).show()
            R.id.menu6 -> Toast.makeText(this, "설정", Toast.LENGTH_SHORT).show()
            R.id.menu7 -> Toast.makeText(this, "버전정보", Toast.LENGTH_SHORT).show()
        }
        return false
    }

    private fun startSplashActivity() {
        val intent = Intent(this, SplashActivity::class.java)
        startActivity(intent)
    }

    private fun startMyPageActivity() {
        val intent = Intent(this, MyPageActivity::class.java)
        startActivity(intent)
    }

    private fun startMyPastTripActivity() {
        val intent = Intent(this, MyPastTripActivity::class.java)
        startActivity(intent)
    }

    private fun startTripActivity() {
        val intent = Intent(this, TripActivity::class.java)
        startActivity(intent)
    }

    private fun userLogout() {
        logout()
        startSplashActivity()
    }

    override fun onGetTripCountSuccess(result: Int) {
        Log.d("MainActivity", "getTripCountSuccess()")
        binding.mainUserTripCountTv.text = result.toString()

        if(binding.mainUserTripCountTv.text == "0") {
            initFragment()
            showImgDialog(
                "트립북을 시작해보세요!",
                "상단의 ‘여행 기록하러 가기’\n" +
                    "버튼을 눌러\n" +
                    "여행 발자국을 남겨보세요.", "확인", R.drawable.img_home_notice
            )
        }
    }

    override fun onGetTripCountFailure(code: Int, message: String) {
        Log.e("MainActivity", "getTripCountFailure() status code $code")
        when(code) {
            400 -> Toast.makeText(this, "네트워크 상태를 확인해주세요.", Toast.LENGTH_SHORT).show()
            1500, 1504, 1507, 1509 -> userAuthApiController.autoSignIn()
            2105 -> Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun autoSignInSuccess() {
        Log.d(
            "MainActivity",
            " \nautoSigninSuccess()" +
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
        } else {
            Toast.makeText(this, "로그아웃되었습니다. 재로그인 해주세요.", Toast.LENGTH_SHORT).show()
            userLogout()
        }
    }

    override fun autoSignInFailure(code: Int) {
        Log.e(
            "MainActivity",
            "autoSigninFailure() status code $code")
        when (code) {
            1500, 1504, 1507, 1509 -> {
                val refreshToken = getRefreshToken()
                val userIdx = getUserIdx()
                if (refreshToken != null && userIdx != 0) {
                    val tokens = HashMap<String, String>()
                    tokens["refreshToken"] = refreshToken
                    userAuthApiController.updateAccessToken(tokens, userIdx)
                } else {
                    Toast.makeText(this, "로그아웃되었습니다. 재로그인 해주세요.", Toast.LENGTH_SHORT).show()
                    userLogout()
                }
            }
            else -> {
                Toast.makeText(this, "로그아웃되었습니다. 재로그인 해주세요.", Toast.LENGTH_SHORT).show()
                userLogout()
            }
        }
    }

    override fun signUpUserSuccess() {
        Log.d("MainActivity", "signUpUserSuccess()")
        val kakaoAccessToken = getKakaoAccessToken()
        if (kakaoAccessToken != null) {
            val token = HashMap<String, String>()
            token["kakaoAccessToken"] = kakaoAccessToken
            userAuthApiController.signUpProfile(token)
        } else {
            Toast.makeText(this, "로그아웃되었습니다. 재로그인 해주세요.", Toast.LENGTH_SHORT).show()
            userLogout()
        }
    }

    override fun signUpUserFailure(code: Int) {
        Log.e("MainActivity", "signUpUserFailure() status code $code")
        Toast.makeText(this, "로그아웃되었습니다. 재로그인 해주세요.", Toast.LENGTH_SHORT).show()
        userLogout()
    }

    override fun signUpProfileSuccess() {
        Log.d("MainActivity", "signUpProfileSuccess()")
        val accessToken = getAccessToken()
        val refreshToken = getRefreshToken()
        if (accessToken != null && refreshToken != null) {
            val tokens = HashMap<String, String>()
            tokens["accessToken"] = accessToken
            tokens["refreshToken"] = refreshToken
            userAuthApiController.kakaoSignin(tokens)
        } else {
            Toast.makeText(this, "로그아웃되었습니다. 재로그인 해주세요.", Toast.LENGTH_SHORT).show()
            userLogout()
        }
    }

    override fun signUpProfileFailure(code: Int) {
        Log.e("MainActivity", "signUpProfileFailure() status code $code")
        Toast.makeText(this, "로그아웃되었습니다. 재로그인 해주세요.", Toast.LENGTH_SHORT).show()
        userLogout()
    }

    override fun kakaoSignInSuccess() {
        val kat = getKakaoAccessToken()
        val krt = getKakaoRefreshToken()
        Log.d("MainActivity", "kakaoSigninSuccess()")
        Log.d("MainActivity", " \nKAT: $kat \nKRT: $krt")
        val accessToken = getAccessToken()
        if (accessToken != null) {
            userAuthApiController.autoSignIn()
        } else {
            Toast.makeText(this, "로그아웃되었습니다. 재로그인 해주세요.", Toast.LENGTH_SHORT).show()
            userLogout()
        }
    }

    override fun kakaoSignInFailure(code: Int) {
        Log.e("MainActivity", "kakaoSigninFailure() status code $code")
        when (code) {
            2052 -> {
                val kakaoAccessToken = getKakaoAccessToken()
                if (kakaoAccessToken != null) {
                    val token = HashMap<String, String>()
                    token["kakaoAccessToken"] = kakaoAccessToken
                    userAuthApiController.signUpUser(token)
                } else {
                    Toast.makeText(this, "로그아웃되었습니다. 재로그인 해주세요.", Toast.LENGTH_SHORT).show()
                    userLogout()
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
                    Toast.makeText(this, "로그아웃되었습니다. 재로그인 해주세요.", Toast.LENGTH_SHORT).show()
                    userLogout()
                }
            }
            else -> {
                Toast.makeText(this, "로그아웃되었습니다. 재로그인 해주세요.", Toast.LENGTH_SHORT).show()
                userLogout()
            }
        }
    }

    override fun updateKakaoAccessTokenSuccess() {
        Log.d("MainActivity", "updateKakaoAccessTokenSuccess()")
        val kakaoAccessToken = getKakaoAccessToken()
        val kakaoRefreshToken = getKakaoRefreshToken()
        if (kakaoAccessToken != null && kakaoRefreshToken != null) {
            val tokens = HashMap<String, String>()
            tokens["kakaoAccessToken"] = kakaoAccessToken
            tokens["kakaoRefreshToken"] = kakaoRefreshToken
            userAuthApiController.kakaoSignin(tokens)
        } else {
            Toast.makeText(this, "로그아웃되었습니다. 재로그인 해주세요.", Toast.LENGTH_SHORT).show()
            userLogout()
        }
    }

    override fun updateKakaoAccessTokenFailure(code: Int) {
        Log.e("MainActivity", "updateKakaoAccessTokenFailure() status code $code")
        Toast.makeText(this, "로그아웃되었습니다. 재로그인 해주세요.", Toast.LENGTH_SHORT).show()
        userLogout()
    }

    override fun updateProfileSuccess() {
        Log.d("MainActivity", "updateProfileSuccess()")
        userAuthApiController.getProfile(getUserIdx())
    }

    override fun updateProfileFailure(code: Int) {
        Log.e("MainActivity", "updateProfileFailure() status code $code")
    }

    override fun getProfileSuccess() {
        val nickname = getNickname()
        val userImg = getUserImage()
        Log.d("MainActivity", "getProfileSuccess()")
        Log.d("MainActivity", "nickname: $nickname, userImg: $userImg")
    }

    override fun getProfileFailure(code: Int) {
        Log.e("MainActivity", "getProfileFailure() status code $code")
        Toast.makeText(this, "프로필 업데이트에 실패했습니다.", Toast.LENGTH_SHORT).show()
    }

    override fun updateAccessTokenSuccess() {
        Log.d("MainActivity", "updateAccessTokenSuccess()")
        val accessToken = getAccessToken()
        if (accessToken != null) {
            userAuthApiController.autoSignIn()
        } else {
            Toast.makeText(this, "로그아웃되었습니다. 재로그인 해주세요.", Toast.LENGTH_SHORT).show()
            userLogout()
        }
    }

    override fun updateAccessTokenFailure(code: Int) {
        Log.e("MainActivity", "updateAccessTokenFailure() status code $code")
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
                    Toast.makeText(this, "로그아웃되었습니다. 재로그인 해주세요.", Toast.LENGTH_SHORT).show()
                    userLogout()
                }
            }
            else -> {
                Toast.makeText(this, "로그아웃되었습니다. 재로그인 해주세요.", Toast.LENGTH_SHORT).show()
                userLogout()
            }
        }
    }
}