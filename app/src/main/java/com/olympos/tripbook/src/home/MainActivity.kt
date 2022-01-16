package com.olympos.tripbook.src.home

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.olympos.tripbook.config.BaseActivity
import com.olympos.tripbook.databinding.ActivityMainBinding
import android.view.Menu
import com.olympos.tripbook.R


class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initNavigation()
//        initViewpager()

        setSupportActionBar(binding.mainToolbar) // 툴바를 액티비티의 앱바로 지정
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // 드로어를 꺼낼 홈 버튼 활성화
        supportActionBar?.setHomeAsUpIndicator(R.drawable.btn_home_menu) // 홈버튼 이미지 변경
        supportActionBar?.setDisplayShowTitleEnabled(false) // 툴바에 타이틀 안보이게
        supportActionBar?.setLogo(R.drawable.img_home_logo)
        supportActionBar?.setDisplayUseLogoEnabled(true)

        binding.mainDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED) //스와이프 비활성화
        binding.mainLeftNavigationView.setNavigationItemSelectedListener(this) //navigation 리스너
        binding.mainRightNavigationView.setNavigationItemSelectedListener(this) //navigation 리스너
    }

    //toolbar 메뉴 생성
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_main_left_drawer, menu)
        menuInflater.inflate(R.menu.activity_main_right_drawer, menu)
        return true
    }

//    override fun a(item: MenuItem): Boolean {
//        // Handle item selection
//        return when (item.itemId) {
//            R.id.actis-> {
//                addSomething()
//                true
//            }
//            R.id.action_settings -> {
//                startSettings()
//                true
//            }
//            else -> super.onOptionsItemSelected(item)
//        }
//    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->{ // 메뉴 버튼
                binding.mainDrawerLayout.openDrawer(GravityCompat.START)    // 네비게이션 드로어 열기
            }
            R.id.action_settings->{ // 메뉴 버튼
                binding.mainDrawerLayout.openDrawer(GravityCompat.END)    // 네비게이션 드로어 열기
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu1-> Toast.makeText(this,"나의 지난 여행",Toast.LENGTH_SHORT).show()
            R.id.menu2-> Toast.makeText(this,"다이어리 테마",Toast.LENGTH_SHORT).show()
            R.id.menu3-> Toast.makeText(this,"모두의 여행기",Toast.LENGTH_SHORT).show()
            R.id.menu4-> Toast.makeText(this,"새로운 소식",Toast.LENGTH_SHORT).show()
            R.id.menu5-> Toast.makeText(this,"이용방법",Toast.LENGTH_SHORT).show()
            R.id.menu6-> Toast.makeText(this,"설정",Toast.LENGTH_SHORT).show()
            R.id.menu7-> Toast.makeText(this,"버전정보",Toast.LENGTH_SHORT).show()
        }

        return false
    }

    private fun initNavigation() {
        supportFragmentManager.beginTransaction().replace(R.id.main_content_fl, HomeFragment())
            .commitAllowingStateLoss()
    }

//    private fun initViewpager() {
//        val tripVP = TripViewpagerAdapter(this)
////        tripVP.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
//
//        binding.mainContentVp.adapter = tripVP
//        binding.mainContentVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
//        binding.mainContentCi.setViewPager(binding.mainContentVp)
//    }
//
//    override fun onClick(v: View?) {
//        super.onClick(v)
//        //여행 기록하기
//        val tripVP = TripViewpagerAdapter(this)
//        if (v == binding.mainContentRecordBtnTv) {
//
//            tripVP.addFragment(fragment)
//            Toast.makeText(this, "여행기록하기", Toast.LENGTH_SHORT).show()
//        }
//    }
}