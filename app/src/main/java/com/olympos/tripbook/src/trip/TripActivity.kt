package com.olympos.tripbook.src.trip

import android.content.Intent
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.NumberPicker
import com.applikeysolutions.cosmocalendar.selection.OnDaySelectedListener
import com.applikeysolutions.cosmocalendar.selection.RangeSelectionManager
import com.olympos.tripbook.R
import com.olympos.tripbook.config.BaseActivity
import com.olympos.tripbook.databinding.ActivityTripBinding
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.olympos.tripbook.src.tripcourse.TripcourseActivity
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import java.lang.IllegalArgumentException


class TripActivity : BaseActivity() {
    private lateinit var binding: ActivityTripBinding
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

    }

    private fun initView() {
        //상단바
        binding.tripTopbarLayout.topbarTitleTv.text = "여행 기록하기"
        binding.tripTopbarLayout.topbarSubtitleTv.visibility = View.GONE
        binding.tripTopbarLayout.topbarSubbuttonIb.visibility = View.GONE

        //캘린더 - 년, 월 선택
        val year = binding.tripDatePickerYear
        val month = binding.tripDatePickerMonth
        year.maxValue=2022
        year.minValue=1980
        month.maxValue=12
        month.minValue=1

        //Number Picker 순환안되도록
        year.wrapSelectorWheel = false
        month.wrapSelectorWheel = false



        //캘린더
        val calendar = binding.tripCalendarMcv
        calendar.topbarVisible=false
//        calendar.setOnDateChangedListener(this)
//        calendar.setOnRangeSelectedListener(this)
//        calendar.addDecorator(decorator)

    }

    fun onRangeSelected(
        @NonNull widget: MaterialCalendarView?,
        @NonNull dates: List<CalendarDay?>
    ) {
        if (dates.size > 0) {
//            decorator.addFirstAndLast(dates[0], dates[dates.size - 1])
            binding.tripCalendarMcv.invalidateDecorators()
        }
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
                binding.tripThemeTheme1Cv.setStrokeColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.dark_gray)))
                binding.tripThemeTheme1Tv.setTextColor(ContextCompat.getColor(this, R.color.dark_gray))
                binding.tripThemeTheme2Cv.setStrokeColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.tripbook_main_1)))
                binding.tripThemeTheme2Tv.setTextColor(ContextCompat.getColor(this, R.color.tripbook_main_1))
                binding.tripThemeTheme3Cv.setStrokeColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.dark_gray)))
                binding.tripThemeTheme3Tv.setTextColor(ContextCompat.getColor(this, R.color.dark_gray))
            }
            R.id.trip_theme_theme3_ll -> {
                binding.tripThemeTheme1Cv.setStrokeColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.dark_gray)))
                binding.tripThemeTheme1Tv.setTextColor(ContextCompat.getColor(this, R.color.dark_gray))
                binding.tripThemeTheme2Cv.setStrokeColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.dark_gray)))
                binding.tripThemeTheme2Tv.setTextColor(ContextCompat.getColor(this, R.color.dark_gray))
                binding.tripThemeTheme3Cv.setStrokeColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.tripbook_main_1)))
                binding.tripThemeTheme3Tv.setTextColor(ContextCompat.getColor(this, R.color.tripbook_main_1))
            }
        }
    }

    private fun startTripcourseActivity() {
        val intent = Intent(this, TripcourseActivity::class.java)
        startActivity(intent)
    }

//    private fun setDividerColor(picker: NumberPicker, customDrawable: Drawable) {
//        val pickerFields = NumberPicker::class.java.declaredFields
//        for (pf in pickerFields) {
//            if (pf.name == "mSelectionDivider") {
//                pf.isAccessible = true
//                try {
//                    pf[picker] = customDrawable
//                } catch (e: IllegalArgumentException) {
//                    e.printStackTrace()
//                } catch (e: Resources.NotFoundException) {
//                    e.printStackTrace()
//                } catch (e: IllegalAccessException) {
//                    e.printStackTrace()
//                }
//                break
//            }
//        }
//    }
}