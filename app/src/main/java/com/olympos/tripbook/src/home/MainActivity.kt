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
import com.olympos.tripbook.src.home.view.MainVPAdapter
import com.olympos.tripbook.src.trip.TripActivity
import com.olympos.tripbook.src.trip.controller.TripApiController
import com.olympos.tripbook.src.trip.model.Trip
import com.olympos.tripbook.src.trip.view.GetAllTripsView
import com.olympos.tripbook.src.trip.view.GetTripCountView
import com.olympos.tripbook.src.user.MyPageActivity
import com.olympos.tripbook.src.user.MyPastTripActivity
import com.olympos.tripbook.src.user.controller.UserAuthApiController
import com.olympos.tripbook.src.user.view.UserAuthView
import com.olympos.tripbook.utils.*


class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener, GetAllTripsView, GetTripCountView, UserAuthView {

    private val tripApiController = TripApiController()
    private val userAuthApiController = UserAuthApiController()

    private var isDialogShown = false
    private var tripCount = 0

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        initDrawer()
    }

    override fun onResume() {
        super.onResume()
        tripApiController.getTripCount()
    }

    override fun onClick(v: View?) {
        super.onClick(v)

        when (v!!.id) {
            R.id.main_left_drawer_btn -> binding.mainDrawerLayout.openDrawer(GravityCompat.START)
            R.id.main_content_record_btn_tv -> startTripActivity()
            R.id.main_drawer_header_logout_tv -> userLogout()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu0 -> startMyPageActivity()
            R.id.menu1 -> startMyPastTripActivity()
            else -> Toast.makeText(this, "준비중입니다.", Toast.LENGTH_SHORT).show()
        }
        return false
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {
        tripApiController.setAllTripsView(this)
        tripApiController.setTripCountView(this)
        userAuthApiController.setUserView(this)
        binding.mainLeftDrawerBtn.setOnClickListener(this)
        binding.mainContentRecordBtnTv.setOnClickListener(this)
        binding.mainLeftNavigationView.setNavigationItemSelectedListener(this)
        binding.mainDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED) //스와이프 비활성화
        binding.mainUserNameTv.text = getNickname() + "님의 추억"
    }

    @SuppressLint("SetTextI18n")
    private fun initDrawer() {
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

    private fun initHomeFragment(trips: ArrayList<Trip>) {
        if (tripCount == 0) {
            setEmptyHomeFragment()
            showHomeDialog()
        } else {
            binding.mainHomeViewpager.adapter = MainVPAdapter(this, trips)
        }
    }

    private fun setEmptyHomeFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_home_constraint_layout, EmptyHomeFragment())
            .commitAllowingStateLoss()
    }

    private fun showHomeDialog() {
        if (!isDialogShown) {
            showImgDialog(
                "트립북을 시작해보세요!",
                "상단의 ‘여행 기록하러 가기’\n버튼을 눌러\n여행 발자국을 남겨보세요.",
                "확인",
                R.drawable.img_home_notice
            )
            isDialogShown = !isDialogShown
        }
    }

    private fun startSplashActivity() = startActivity(Intent(this, SplashActivity::class.java))


    private fun startMyPageActivity() = startActivity(Intent(this, MyPageActivity::class.java))

    private fun startMyPastTripActivity() = startActivity(Intent(this, MyPastTripActivity::class.java))


    private fun startTripActivity() = startActivity(Intent(this, TripActivity::class.java))

    private fun userLogout() {
        logout()
        startSplashActivity()
    }

    override fun onGetTripCountSuccess(result: Int) {
        Log.d("MainActivity", "onGetTripCountSuccess() tripCount $result")
        tripCount = result
        tripApiController.getAllTrips()
    }

    override fun onGetTripCountFailure(code: Int, message: String) {

        Log.e("MainActivity", "onGetTripCountFailure() status code $code")

        when(code) {
            400 -> Toast.makeText(this, "네트워크 상태를 확인해주세요.", Toast.LENGTH_SHORT).show()
            1500, 1504, 1507, 1509 -> userAuthApiController.autoSignIn()
            2105 -> Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onGetAllTripsSuccess(result: ArrayList<Trip>) {
        Log.d("MainActivity", "onGetAllTripsSuccess()")
        initHomeFragment(result)
    }

    override fun onGetAllTripsFailure(code: Int, message: String) {
        Log.e("MainActivity", "onGetAllTripsFailure() status code $code")

        when(code) {
            400 -> Toast.makeText(this, "네트워크 상태를 확인해주세요.", Toast.LENGTH_SHORT).show()
            1500, 1504, 1507, 1509 -> userAuthApiController.autoSignIn()
            2105 -> Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun autoSignInSuccess() {

        Log.d(
            "MainActivity",
            " \nautoSignInSuccess()" +
                "\nuserIdx: " + getUserIdx() +
                "\nAT: " + getAccessToken() +
                "\nRT: " + getRefreshToken() +
                "\nKAT: " + getKakaoAccessToken() +
                "\nKRT: " + getKakaoRefreshToken()
        )

        val kakaoAccessToken = getKakaoAccessToken()
        if (kakaoAccessToken != null) {
            val token = HashMap<String, String?>()
            token["kakaoAccessToken"] = kakaoAccessToken
            userAuthApiController.updateProfile(token, getUserIdx())
        } else {
            Toast.makeText(this, "로그아웃되었습니다. 로그인 해주세요.", Toast.LENGTH_SHORT).show()
            userLogout()
        }
    }

    override fun autoSignInFailure(code: Int) {

        Log.e("MainActivity", "autoSignInFailure() status code $code")

        when (code) {

            1500, 1504, 1507, 1509 -> {
                val tokens = HashMap<String, String?>()
                tokens["refreshToken"] = getRefreshToken()
                userAuthApiController.updateAccessToken(tokens, getUserIdx())
            }

            else -> {
                Toast.makeText(this, "로그아웃되었습니다. 로그인 해주세요.", Toast.LENGTH_SHORT).show()
                userLogout()
            }
        }
    }

    override fun signUpUserSuccess() {

        Log.d("MainActivity", "signUpUserSuccess()")

        val token = HashMap<String, String?>()
        token["kakaoAccessToken"] = getKakaoAccessToken()
        userAuthApiController.signUpProfile(token)

    }

    override fun signUpUserFailure(code: Int) {
        Log.e("MainActivity", "signUpUserFailure() status code $code")
        Toast.makeText(this, "로그아웃되었습니다. 로그인 해주세요.", Toast.LENGTH_SHORT).show()
        userLogout()
    }

    override fun signUpProfileSuccess() {

        Log.d("MainActivity", "signUpProfileSuccess()")

        val profile = HashMap<String, String?>()
        profile["nickname"] = getNickname()
        profile["profileImageUrl"] = getUserImage()

        val tokens = HashMap<String, String?>()
        tokens["kakaoAccessToken"] = getKakaoAccessToken()
        tokens["kakaoRefreshToken"] = getKakaoRefreshToken()

        userAuthApiController.kakaoSignIn(profile, tokens)
    }

    override fun signUpProfileFailure(code: Int) {
        Log.e("MainActivity", "signUpProfileFailure() status code $code")
        Toast.makeText(this, "로그아웃되었습니다. 로그인 해주세요.", Toast.LENGTH_SHORT).show()
        userLogout()
    }

    override fun kakaoSignInSuccess() {

        Log.d("MainActivity", "kakaoSignInSuccess()")
        Log.d("MainActivity", " \nKAT: " + getKakaoAccessToken() + "\nKRT: " + getKakaoRefreshToken())

        userAuthApiController.autoSignIn()
    }

    override fun kakaoSignInFailure(code: Int) {

        Log.e("MainActivity", "kakaoSignInFailure() status code $code")

        when (code) {

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

            else -> {
                Toast.makeText(this, "로그아웃되었습니다. 로그인 해주세요.", Toast.LENGTH_SHORT).show()
                userLogout()
            }
        }
    }

    override fun updateKakaoAccessTokenSuccess() {

        Log.d("MainActivity", "updateKakaoAccessTokenSuccess()")

        val profile = HashMap<String, String?>()
        profile["nickname"] = getNickname()
        profile["profileImageUrl"] = getUserImage()

        val tokens = HashMap<String, String?>()
        tokens["kakaoAccessToken"] = getKakaoAccessToken()
        tokens["kakaoRefreshToken"] = getKakaoRefreshToken()

        userAuthApiController.kakaoSignIn(profile, tokens)

    }

    override fun updateKakaoAccessTokenFailure(code: Int) {
        Log.e("MainActivity", "updateKakaoAccessTokenFailure() status code $code")
        Toast.makeText(this, "로그아웃되었습니다. 로그인 해주세요.", Toast.LENGTH_SHORT).show()
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
        Log.d("MainActivity",
            "getProfileSuccess()\n" + "nickname: " + getNickname() + "\nuserImg: " + getUserImage())
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
            Toast.makeText(this, "로그아웃되었습니다. 로그인 해주세요.", Toast.LENGTH_SHORT).show()
            userLogout()
        }
    }

    override fun updateAccessTokenFailure(code: Int) {
        
        Log.e("MainActivity", "updateAccessTokenFailure() status code $code")
        
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
            
            else -> {
                Toast.makeText(this, "로그아웃되었습니다. 로그인 해주세요.", Toast.LENGTH_SHORT).show()
                userLogout()
            }
            
        }
    }

}