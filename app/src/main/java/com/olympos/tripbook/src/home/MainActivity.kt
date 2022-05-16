package com.olympos.tripbook.src.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.olympos.tripbook.R
import com.olympos.tripbook.config.BaseActivity
import com.olympos.tripbook.databinding.ActivityMainBinding
import com.olympos.tripbook.src.home.model.HomeGetProcess
import com.olympos.tripbook.src.home.model.HomeService
import com.olympos.tripbook.src.splash.SplashActivity
import com.olympos.tripbook.src.trip.TripActivity
import com.olympos.tripbook.src.tripcourse_view.TripcourseViewFragment
import com.olympos.tripbook.src.user.SigninActivity
import com.olympos.tripbook.src.user.model.UserService
import com.olympos.tripbook.src.user.model.UserView
import com.olympos.tripbook.utils.*


class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener, HomeGetProcess, UserView {
    private val userService = UserService()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
//        initViewpager()

        //click 리스너
        binding.homeLeftDrawerBtn.setOnClickListener(this)
        binding.mainContentRecordBtnTv.setOnClickListener(this)
        binding.mainLeftNavigationView.setNavigationItemSelectedListener(this)

        binding.mainDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED) //스와이프 비활성화

    }

    override fun onResume() {
        super.onResume()
        getTripCount()
    }

    private fun initView() {
        getTripCount()
        binding.mainUserNameTv.text = getNickname() + "님의 추억"

        //navigation view
        val navigationView = findViewById<View>(R.id.main_left_navigation_view) as NavigationView
        val headerView = navigationView.getHeaderView(0)
        val navUserName = headerView.findViewById<View>(R.id.main_drawer_header_name_tv) as TextView
        val navUserImg = headerView.findViewById<View>(R.id.main_drawer_header_profile_iv) as ImageView
        val navUserLogout = headerView.findViewById<View>(R.id.main_drawer_header_logout_tv) as TextView
        navUserName.text = getNickname() + "님"
        Glide.with(this).load(getUserImage()).circleCrop().into(navUserImg)

        navUserLogout.setOnClickListener(this)
    }

    //
    private fun initFragment() {
        supportFragmentManager.beginTransaction().replace(R.id.main_content_fl, HomeFragment())
            .commitAllowingStateLoss()
    }

    private fun showRecentTripcourse(recentTripIdx : Int) {
        val tripcourseViewFragment = TripcourseViewFragment()
        val bundle = Bundle()
        bundle.putInt("tripIdx", recentTripIdx)
        tripcourseViewFragment.arguments = bundle

        supportFragmentManager.beginTransaction().replace(R.id.main_content_fl, tripcourseViewFragment)
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
            R.id.menu1 -> Toast.makeText(this, "나의 지난 여행", Toast.LENGTH_SHORT).show()
            R.id.menu2 -> Toast.makeText(this, "다이어리 테마", Toast.LENGTH_SHORT).show()
            R.id.menu3 -> Toast.makeText(this, "모두의 여행기", Toast.LENGTH_SHORT).show()
            R.id.menu4 -> Toast.makeText(this, "새로운 소식", Toast.LENGTH_SHORT).show()
            R.id.menu5 -> Toast.makeText(this, "이용방법", Toast.LENGTH_SHORT).show()
            R.id.menu6 -> Toast.makeText(this, "설정", Toast.LENGTH_SHORT).show()
            R.id.menu7 -> Toast.makeText(this, "버전정보", Toast.LENGTH_SHORT).show()
        }
        return false
    }

    private fun startTripActivity() {
        val intent = Intent(this, TripActivity::class.java)
//        val intent = Intent(this, TripcourseActivity::class.java)
        startActivity(intent)
    }

    private fun startSplashActivity() {
        val intent = Intent(this, SplashActivity::class.java)
        startActivity(intent)
    }

    override fun onOKClicked() {
        super.onOKClicked()
        //반짝이는 효과
//        binding.mainContentRecordBtnLl.setBackgroundResource(R.drawable.bg_home_gradation)
    }

    private fun getTripCount() {
        val homeService = HomeService()
        homeService.setProcess(this)

        homeService.getTripCount()
    }

    private fun userLogout() {
        logout()
        startSplashActivity()
    }

    override fun getTripCountLoading() {
        //todo
    }

    override fun getTripCountSuccess(result: Int) {
        binding.mainUserTripCountTv.text = result.toString()

        //기록이 0일 때
        if(binding.mainUserTripCountTv.text == "0") {
            initFragment()
            showImgDialog("트립북을 시작해보세요!", "상단의 ‘여행 기록하러 가기’\n" +
                    "버튼을 눌러\n" +
                    "여행 발자국을 남겨보세요.", "확인", R.drawable.img_home_notice)
        }
        else {
            val recentTripIdx = getTripIdx()
            showRecentTripcourse(recentTripIdx)
        }
    }

    override fun getTripCountFailure(code: Int, message: String) {
        when(code) {
            400 -> Toast.makeText(this, "네트워크 상태를 확인해주세요.", Toast.LENGTH_SHORT).show()
            1500, 1509 -> userService.autoSignin()
            2105 -> Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun startSigninActivity() {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, SigninActivity::class.java)
            startActivity(intent)
            finish()
        },1500)
    }

    override fun autoSigninSuccess() {
        Log.d("MainActivity.kt", " \nautoSigninSuccess()" +
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
            userService.updateProfile(token, getUserIdx())
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
            3007 -> {
                startSigninActivity()
                Toast.makeText(this, "로그아웃되었습니다. 재로그인 해주세요.", Toast.LENGTH_SHORT).show()
            }
            else -> { // 예상하지 못한 에러, destroy
                Log.e("SplashActivity.kt", "autoSigninFailure() Unexpected status code $code")
                Toast.makeText(this@MainActivity, "로그인 에러", Toast.LENGTH_SHORT).show()
                ActivityCompat.finishAffinity(this@MainActivity)
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
        TODO("Not yet implemented")
    }

    override fun kakaoSigninFailure(code: Int) {
        TODO("Not yet implemented")
    }

    override fun updateKakaoAccessTokenSuccess() {
        TODO("Not yet implemented")
    }

    override fun updateKakaoAccessTokenFailure(code: Int) {
        TODO("Not yet implemented")
    }

    override fun updateProfileSuccess() {
        TODO("Not yet implemented")
    }

    override fun updateProfileFailure(code: Int) {
        TODO("Not yet implemented")
    }

    override fun getProfileSuccess() {
        TODO("Not yet implemented")
    }

    override fun getProfileFailure(code: Int) {
        TODO("Not yet implemented")
    }

    override fun updateAccessTokenSuccess() {
        TODO("Not yet implemented")
    }

    override fun updateAccessTokenFailure(code: Int) {
        TODO("Not yet implemented")
    }
}