package com.olympos.tripbook.src.trip

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.gson.Gson
import com.olympos.tripbook.R
import com.olympos.tripbook.config.BaseActivity
import com.olympos.tripbook.databinding.ActivityTripcourseBinding
import com.olympos.tripbook.src.trip.model.Trip
import com.olympos.tripbook.src.trip.model.Tripcourse
import com.olympos.tripbook.src.trip.model.TripcourseRVAdapter
import com.olympos.tripbook.utils.*


class TripcourseActivity : BaseActivity() {
    private lateinit var binding: ActivityTripcourseBinding
    private lateinit var tripcourseRVAdapter: TripcourseRVAdapter
    private var tripData = Trip()
    val mCourses = ArrayList<Tripcourse>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTripcourseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView() // Trip init
        sampleData() // 더미데이터
        initRecyclerView() // 현재는 더미데이터 기반

        //click 리스너
        binding.tripcourseAddBtn.setOnClickListener(this)
    }

    private fun initView() {
        //topbar layout view randering
        binding.tripcourseTopbarLayout.topbarTitleTv.setText(R.string.tripcourse_title)
        binding.tripcourseTopbarLayout.topbarSubbuttonIb.setImageResource(R.drawable.btn_base_check_black)

        val gson = Gson()
        //여행 정보 가져옴
        tripData = gson.fromJson(intent.getStringExtra("tripData"), Trip::class.java)

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
        binding.tripcoursePeriodTv.text = period
        binding.tripcourseTitleTv.text = tripData.tripTitle

        //click listener
        binding.tripcourseTopbarLayout.topbarBackIb.setOnClickListener(this)
        binding.tripcourseTopbarLayout.topbarSubbuttonIb.setOnClickListener(this)
        binding.tripcourseAddBtn.setOnClickListener(this)

        //타이틀바 길게 클릭 - 여행 삭제하기
//        registerForContextMenu(binding.tripcourseTitleFl)
    }

    fun initRecyclerView() {
        tripcourseRVAdapter = TripcourseRVAdapter(mCourses) // 어댑터 객체 생성 및 데이터 삽입
        binding.tripcourseCourseRv.adapter = tripcourseRVAdapter // 리사이클러뷰에 어댑터 연결
        binding.tripcourseCourseRv.layoutManager = StaggeredGridLayoutManager(2,1) // 레이아웃 매니저 연결
    }

    fun sampleData() {
        Log.d("__DATA__", "sample data")
        mCourses.apply {
            add(Tripcourse(0, "비행기에서 만난 파란 하늘", "2021년 03월 10일", "무계획으로 전 날 예매를 하고 캐리어도 없이 비행기에 탔다! 조금 걱정..."))
            add(Tripcourse(4, "비행기에서 만난 파란 하늘", "2021년 03월 10일", "무계획으로 전 날 예매를 하고 캐리어도 없이 비행기에 탔다! 조금 걱정..."))
            add(Tripcourse(4, "비행기에서 만난 파란 하늘", "2021년 03월 10일", "무계획으로 전 날 예매를 하고 캐리어도 없이 비행기에 탔다! 조금 걱정..."))

        }
    }
    override fun onClick(v: View?) {
        super.onClick(v)

        when (v!!.id) {
            R.id.tripcourse_add_btn -> {
                tripcourseRVAdapter.addItem(Tripcourse(0, "비행기에서 만난 파란 하늘", "2021년 03월 10일", "무계획으로 전 날 예매를 하고 캐리어도 없이 비행기에 탔다! 조금 걱정..."))
            }
        }
    }

//    private fun startTripcourseActivity() {
////        val intent = Intent(this, TripcourseActivity::class.java)
//        val gson = Gson()
//        val tripData = gson.toJson(trip)
//
//        intent.putExtra("tripData", tripData)
//        startActivity(intent)
//
//        Log.d("__tripData__ trip", tripData)
//        Log.d("userIdx Check", trip.userIdx)
//        finish()
//    }
}