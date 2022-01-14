package com.olympos.tripbook.src.home

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.olympos.tripbook.R
import com.olympos.tripbook.config.BaseActivity
import com.olympos.tripbook.databinding.ActivityMainBinding

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.main_toolbar_layout)) // 툴바를 액티비티의 앱바로 지정
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // 드로어를 꺼낼 홈 버튼 활성화
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu) // 홈버튼 이미지 변경
        supportActionBar?.setDisplayShowTitleEnabled(false) // 툴바에 타이틀 안보이게
        supportActionBar?.setLogo(R.drawable.logo)
        supportActionBar?.setDisplayUseLogoEnabled(true)

        binding.mainNavigationView.setNavigationItemSelectedListener(this) //navigation 리스너
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->{ // 메뉴 버튼
                binding.mainDrawerLayout.openDrawer(GravityCompat.START)    // 네비게이션 드로어 열기
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
}