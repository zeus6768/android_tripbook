package com.olympos.tripbook.src.viewer

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.olympos.tripbook.R
import com.olympos.tripbook.config.BaseActivity
import com.olympos.tripbook.databinding.ActivityTripcourseViewBinding
import com.olympos.tripbook.src.trip.model.Trip
import com.olympos.tripbook.utils.*

class TripCourseViewActivity: BaseActivity() {

    private lateinit var binding: ActivityTripcourseViewBinding

    private val tripData: Trip? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTripcourseViewBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initView()

        //todo fragment 연결해야 함
    }

    private fun initView() {
        binding.tripcourseViewTopbarLayout.topbarTitleTv.setText(R.string.tripcourse_title)
        binding.tripcourseViewTopbarLayout.topbarSubbuttonIb.visibility = View.GONE

        //todo 서버에서 trip 데이터 가져와야 함
        if( tripData != null ) {
            //출발일
            val dDate = tripData.departureDate.split("-")
            val dYear = dDate[0].substring(2,4)
            val dMonth = dDate[1]
            val dDay = dDate[2]
            saveDepartureYear(dYear.toInt())
            saveDepartureMonth(dMonth.toInt())
            saveDepartureDay(dDay.toInt())

            //도착일
            val aDate = tripData.arrivalDate.split("-")
            val aYear = aDate[0].substring(2,4)
            val aMonth = aDate[1]
            val aDay = aDate[2]
            saveArrivalYear(aYear.toInt())
            saveArrivalMonth(aMonth.toInt())
            saveArrivalDay(aDay.toInt())

            val period = dYear + "년 " + dMonth + "월 " + dDay + "일 ~ " + aYear + "년 " + aMonth + "월 " + aDay + "일"

            //여행 제목 띄우기
            binding.tripcourseViewTitlebarPeriodTv.text = period
            binding.tripcourseViewTitlebarTitleTv.text = tripData.tripTitle

        } else {
            Toast.makeText(this, "카드 내용 없음", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}