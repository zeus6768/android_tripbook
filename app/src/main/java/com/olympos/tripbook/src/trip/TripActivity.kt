package com.olympos.tripbook.src.trip

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.applikeysolutions.cosmocalendar.selection.OnDaySelectedListener
import com.applikeysolutions.cosmocalendar.selection.RangeSelectionManager
import com.olympos.tripbook.R
import com.olympos.tripbook.config.BaseActivity
import com.olympos.tripbook.databinding.ActivityTripBinding
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.olympos.tripbook.src.tripcourse.TripcourseActivity


class TripActivity : BaseActivity() {
    private lateinit var binding: ActivityTripBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTripBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        settingPermission()

        //click 리스너
        binding.tripTopbarLayout.topbarBackIb.setOnClickListener(this)
        binding.tripTopbarLayout.topbarSubbuttonIb.setOnClickListener(this)
        binding.tripThemeTheme1Ll.setOnClickListener(this)
        binding.tripThemeTheme2Ll.setOnClickListener(this)
        binding.tripThemeTheme3Ll.setOnClickListener(this)
        binding.tripThemeTheme4Ll.setOnClickListener(this)
        binding.tripNextStepBtnTv.setOnClickListener(this)


//        binding.tripCalendarCv.isShowDaysOfWeekTitle = false
//        binding.tripCalendarCv.selectionManager = RangeSelectionManager(OnDaySelectedListener {
//            Log.e(" CALENDAR ", "========== setSelectionManager ==========")
//            Log.e(" CALENDAR ", "Selected Dates : " + binding.tripCalendarCv.selectedDates.size)
//            if (binding.tripCalendarCv.selectedDates.size <= 0) return@OnDaySelectedListener
//            Log.e(" CALENDAR ", "Selected Days : " + binding.tripCalendarCv.selectedDays)
//        })

    }

    private fun initView() {
        binding.tripTopbarLayout.topbarTitleTv.text = "여행 기록하기"
        binding.tripTopbarLayout.topbarSubtitleTv.visibility = View.GONE
        binding.tripTopbarLayout.topbarSubbuttonIb.visibility = View.GONE
    }

    override fun onClick(v: View?) {
        super.onClick(v)

        when (v!!.id) {
            R.id.topbar_back_ib ->
                finish()
            R.id.trip_theme_theme1_ll, R.id.trip_theme_theme2_ll, R.id.trip_theme_theme3_ll ->
                themeSelected(v)
            R.id.trip_theme_theme4_ll ->
                Toast.makeText(this, "추후 추가 예정", Toast.LENGTH_SHORT).show()
            R.id.trip_next_step_btn_tv ->
                startTripcourseActivity()
        }
    }

    //테마 선택 시 글씨색 변경 및 테두리색 변경(추가 전)
    private fun themeSelected(v: View?) {
        Log.d("click listener", "themeSelect")
        when (v!!.id) {
            R.id.trip_theme_theme1_ll -> {
                binding.tripThemeTheme1Tv.setTextColor(
                    ContextCompat.getColor(
                        this,
                        R.color.tripbook_main_1
                    )
                )
                binding.tripThemeTheme2Tv.setTextColor(ContextCompat.getColor(this, R.color.gray))
                binding.tripThemeTheme3Tv.setTextColor(ContextCompat.getColor(this, R.color.gray))
            }
            R.id.trip_theme_theme2_ll -> {
                binding.tripThemeTheme1Tv.setTextColor(ContextCompat.getColor(this, R.color.gray))
                binding.tripThemeTheme2Tv.setTextColor(
                    ContextCompat.getColor(
                        this,
                        R.color.tripbook_main_1
                    )
                )
                binding.tripThemeTheme3Tv.setTextColor(ContextCompat.getColor(this, R.color.gray))
            }
            R.id.trip_theme_theme3_ll -> {
                binding.tripThemeTheme1Tv.setTextColor(ContextCompat.getColor(this, R.color.gray))
                binding.tripThemeTheme2Tv.setTextColor(ContextCompat.getColor(this, R.color.gray))
                binding.tripThemeTheme3Tv.setTextColor(
                    ContextCompat.getColor(
                        this,
                        R.color.tripbook_main_1
                    )
                )
            }
        }
    }

    private fun settingPermission() {
        var permissionListener = object : PermissionListener {
            //            어떠한 형식을 상속받는 익명 클래스의 객체를 생성하기 위해 다음과 같이 작성
            override fun onPermissionGranted() {
                Toast.makeText(this@TripActivity, "권한 허가", Toast.LENGTH_SHORT).show()
            }

            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                Toast.makeText(this@TripActivity, "권한 거부", Toast.LENGTH_SHORT).show()
            }
        }

        TedPermission.with(this)
            .setPermissionListener(permissionListener)
            .setRationaleMessage("카메라 사진 권한 필요")
            .setDeniedMessage("카메라 권한 요청 거부")
            .setPermissions(android.Manifest.permission.CAMERA)
            .check()
    }

    private fun startTripcourseActivity() {
        val intent = Intent(this, TripcourseActivity::class.java)
        startActivity(intent)
    }

}