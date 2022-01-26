package com.olympos.tripbook.src.home

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.olympos.tripbook.config.BaseActivity
import com.olympos.tripbook.databinding.ActivityMainBinding
import android.view.View
import com.olympos.tripbook.R
import com.olympos.tripbook.src.trip.TripActivity
import java.security.MessageDigest
import java.util.*


class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initFragment()

//        initViewpager()

        binding.mainDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED) //스와이프 비활성화

        //click 리스너
        binding.homeLeftDrawerBtn.setOnClickListener(this)
        binding.mainContentRecordBtnTv.setOnClickListener(this)
        binding.mainLeftNavigationView.setNavigationItemSelectedListener(this)

    }

    //
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
        startActivity(intent)
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