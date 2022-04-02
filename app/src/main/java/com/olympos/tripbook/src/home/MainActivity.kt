package com.olympos.tripbook.src.home

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.olympos.tripbook.R
import com.olympos.tripbook.config.BaseActivity
import com.olympos.tripbook.databinding.ActivityMainBinding
import com.olympos.tripbook.src.home.model.HomeGetProcess
import com.olympos.tripbook.src.home.model.HomeService
import com.olympos.tripbook.src.trip.TripActivity
import com.olympos.tripbook.src.tripcourse_view.TripcourseViewFragment
import com.olympos.tripbook.utils.getNickname
import com.olympos.tripbook.utils.getTripIdx

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener, HomeGetProcess {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()

//        initViewpager()

        binding.mainDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED) //스와이프 비활성화

        //click 리스너
        binding.homeLeftDrawerBtn.setOnClickListener(this)
        binding.mainContentRecordBtnTv.setOnClickListener(this)
        binding.mainLeftNavigationView.setNavigationItemSelectedListener(this)

    }

    override fun onResume() {
        super.onResume()
        getTripCount()
    }

    private fun initView() {
        getTripCount()
        binding.mainUserNameTv.text = getNickname() + "님의 추억"
        binding.mainLeftNavigationView.findViewById<TextView>(R.id.main_drawer_header_name_tv).text = getNickname() + "님"
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
        }
    }

//    //toolbar 메뉴 생성(add items to the action bar if it is present)
//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.activity_main_left_drawer, menu)
////        menuInflater.inflate(R.menu.activity_main_right_drawer, menu)
//        return true
//    }

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

    override fun onGetHomeLoading() {
        //todo
    }

    override fun onGetHomeSuccess(result: Int) {
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

    override fun onGetHomeFailure(code: Int, message: String) {
        when(code) {
            400 -> Toast.makeText(this, "네트워크 상태를 확인해주세요.", Toast.LENGTH_SHORT).show()
            2105 -> Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }


//    private fun initViewpager() {
//        val tripVP = TripViewpagerAdapter(this)
////        tripVP.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
//
//        binding.mainContentVp.adapter = tripVP
//        binding.mainContentVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
//        binding.mainContentCi.setViewPager(binding.mainContentVp)
//    }

}