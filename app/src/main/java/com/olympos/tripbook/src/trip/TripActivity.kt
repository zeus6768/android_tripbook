package com.olympos.tripbook.src.trip

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.olympos.tripbook.R
import com.olympos.tripbook.config.BaseActivity
import com.olympos.tripbook.databinding.ActivityTripBinding
import com.olympos.tripbook.src.trip.model.Trip
import com.olympos.tripbook.src.tripcourse.TripcourseActivity
import java.text.SimpleDateFormat
import java.util.*


class TripActivity : BaseActivity() {
    private lateinit var binding: ActivityTripBinding
    private lateinit var trip: Trip
//    private var decorator = RangeDayDecorator(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTripBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()

        //click 리스너
        binding.tripTopbarLayout.topbarBackIb.setOnClickListener(this)
        binding.tripTopbarLayout.topbarSubbuttonIb.setOnClickListener(this)
        binding.tripThemeTheme1Ll.setOnClickListener(this)
        binding.tripThemeTheme2Ll.setOnClickListener(this)
        binding.tripThemeTheme3Ll.setOnClickListener(this)
        binding.tripThemeTheme4Ll.setOnClickListener(this)
        binding.tripNextStepBtnTv.setOnClickListener(this)
        binding.tripCalendarMcv.setOnClickListener(this)
    }

    private fun initView() {
        //상단바
        binding.tripTopbarLayout.topbarTitleTv.setText(R.string.trip_title)
        binding.tripTopbarLayout.topbarSubtitleTv.visibility = View.GONE
        binding.tripTopbarLayout.topbarSubbuttonIb.visibility = View.GONE

        //캘린더
        val calendar = binding.tripCalendarMcv
//        val year = binding.tripDatePickerYear
//        val month = binding.tripDatePickerMonth

        //년, 월 최대 최소 값 및 현재값 세팅
//        year.maxValue = getCurrentDate().split(".")[0].toInt()
//        year.minValue = 1980
//        month.maxValue = 12
//        month.minValue = 1
//        year.value = getCurrentDate().split(".")[0].toInt()
//        month.value = getCurrentDate().split(".")[1].toInt()
//
//        //Number Picker 순환안되도록
//        year.wrapSelectorWheel = false
//        month.wrapSelectorWheel = false

        //날짜 선택
        calendar.topbarVisible = true
        calendar.setOnRangeSelectedListener { widget, dates ->
            //출발일
            binding.tripDateDepartureMonthTv.text = dates.first().toString().split("-")[1]
            binding.tripDateDepartureDayTv.text = dates.first().toString().split("-")[2].dropLast(1)
            //도착일
            binding.tripDateArrivalMonthTv.text = dates.last().toString().split("-")[1]
            binding.tripDateArrivalDayTv.text = dates.last().toString().split("-")[2].dropLast(1)
        }
        //        calendar.setOnDateChangedListener(this)
//        calendar.addDecorator(decorator)
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

    //테마 선택 시 글씨색 변경 및 테두리색 변경
    private fun themeSelected(v: View?) {
        Log.d("click listener", "themeSelect")
        when (v!!.id) {
            R.id.trip_theme_theme1_ll -> {
                binding.tripThemeTheme1Cv.setStrokeColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.tripbook_main_1)))
                binding.tripThemeTheme1Tv.setTextColor(ContextCompat.getColor(this, R.color.tripbook_main_1))
                binding.tripThemeTheme2Cv.setStrokeColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.dark_gray)))
                binding.tripThemeTheme2Tv.setTextColor(ContextCompat.getColor(this, R.color.dark_gray))
                binding.tripThemeTheme3Cv.setStrokeColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.dark_gray)))
                binding.tripThemeTheme3Tv.setTextColor(ContextCompat.getColor(this, R.color.dark_gray))
            }
            R.id.trip_theme_theme2_ll -> {
                binding.tripThemeTheme1Cv.setStrokeColor(
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            this,
                            R.color.dark_gray
                        )
                    )
                )
                binding.tripThemeTheme1Tv.setTextColor(
                    ContextCompat.getColor(
                        this,
                        R.color.dark_gray
                    )
                )
                binding.tripThemeTheme2Cv.setStrokeColor(
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            this,
                            R.color.tripbook_main_1
                        )
                    )
                )
                binding.tripThemeTheme2Tv.setTextColor(
                    ContextCompat.getColor(
                        this,
                        R.color.tripbook_main_1
                    )
                )
                binding.tripThemeTheme3Cv.setStrokeColor(
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            this,
                            R.color.dark_gray
                        )
                    )
                )
                binding.tripThemeTheme3Tv.setTextColor(
                    ContextCompat.getColor(
                        this,
                        R.color.dark_gray
                    )
                )
            }
            R.id.trip_theme_theme3_ll -> {
                binding.tripThemeTheme1Cv.setStrokeColor(
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            this,
                            R.color.dark_gray
                        )
                    )
                )
                binding.tripThemeTheme1Tv.setTextColor(
                    ContextCompat.getColor(
                        this,
                        R.color.dark_gray
                    )
                )
                binding.tripThemeTheme2Cv.setStrokeColor(
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            this,
                            R.color.dark_gray
                        )
                    )
                )
                binding.tripThemeTheme2Tv.setTextColor(
                    ContextCompat.getColor(
                        this,
                        R.color.dark_gray
                    )
                )
                binding.tripThemeTheme3Cv.setStrokeColor(
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            this,
                            R.color.tripbook_main_1
                        )
                    )
                )
                binding.tripThemeTheme3Tv.setTextColor(
                    ContextCompat.getColor(
                        this,
                        R.color.tripbook_main_1
                    )
                )
            }
        }
    }

    private fun startTripcourseActivity() {
        val intent = Intent(this, TripcourseActivity::class.java)
        startActivity(intent)
    }

    //현재 날짜 getter
    private fun getCurrentDate(): String {
        // 현재시간을 가져오기
        val now: Long = System.currentTimeMillis()
        // 현재 시간을 Date 타입으로 변환
        val date = Date(now)
        // 날짜, 시간을 가져오고 싶은 형태 선언
        val dateFormat = SimpleDateFormat("yyyy.MM.dd", Locale("ko", "KR"))

        return dateFormat.format(date)
    }
}