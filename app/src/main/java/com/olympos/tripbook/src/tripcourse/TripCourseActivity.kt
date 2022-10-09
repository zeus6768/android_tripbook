package com.olympos.tripbook.src.tripcourse

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.gson.Gson
import com.olympos.tripbook.R
import com.olympos.tripbook.config.BaseActivity
import com.olympos.tripbook.config.BaseDialog
import com.olympos.tripbook.databinding.ActivityTripcourseBinding
import com.olympos.tripbook.src.trip.TripActivity
import com.olympos.tripbook.src.trip.model.Trip
import com.olympos.tripbook.src.tripcourse.model.TripCourse
import com.olympos.tripbook.src.tripcourse.view.TripCourseRVAdapter
import com.olympos.tripbook.utils.*


class TripCourseActivity : BaseActivity() {
    private lateinit var binding: ActivityTripcourseBinding
    private lateinit var tripcourseRVAdapter: TripCourseRVAdapter
    private var tripData = Trip()
    val mCourses = ArrayList<TripCourse>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTripcourseBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        initView() // Trip init
//        sampleData() // 더미데이터
//        initRecyclerView() // 현재는 더미데이터 기반
    }

    override fun onStart() {
        super.onStart()
        initView()
        sampleData() // 더미데이터
        initRecyclerView() // 현재는 더미데이터 기반
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

        //click 리스너
        binding.tripcourseAddBtn.setOnClickListener(this)
        binding.tripcourseTopbarLayout.topbarBackIb.setOnClickListener(this)
        binding.tripcourseTopbarLayout.topbarSubbuttonIb.setOnClickListener(this)

        //타이틀바 선택
        binding.tripcourseTitleFl.setOnClickListener(this)
    }

    fun initRecyclerView() {
        tripcourseRVAdapter = TripCourseRVAdapter(this, mCourses) // 어댑터 객체 생성 및 데이터 삽입
        binding.tripcourseCourseRv.adapter = tripcourseRVAdapter // 리사이클러뷰에 어댑터 연결
        binding.tripcourseCourseRv.layoutManager = StaggeredGridLayoutManager(2,1) // 레이아웃 매니저 연결

        tripcourseRVAdapter.setMyItemClickListener(object: TripCourseRVAdapter.MyItemClickListener {
            override fun onItemClick(course: TripCourse) {
                startRecordActivity(course)
            }
        })
    }

    fun sampleData() {
        Log.d("__DATA__", "sample data")
        mCourses.apply {
            add(TripCourse(0, "비행기에서 만난 파란 하늘", "2021년 03월 10일", "무계획으로 전 날 예매를 하고 캐리어도 없이 비행기에 탔다! 조금 걱정..."))
            add(TripCourse())
            add(TripCourse(1, "유채꽃 예뻤다", "2021년 03월 10일", "서울은 엄청 추운데 여긴 벌써 노랗게 꽃이 피고 따땃했다~ 너무 예쁘다 제주도는 어딜가나 노란 빛으로 물들어있다 내용내용내용"))
            add(TripCourse())
            add(TripCourse(2, "????", "!!!!!!!", "adsfasdfasdfsadfasdfasdfasdf"))
        }
    }
    override fun onClick(v: View?) {
        super.onClick(v)

        when (v!!.id) {
            R.id.tripcourse_add_btn -> {
                tripcourseRVAdapter.addItem(TripCourse())
            }
            R.id.topbar_back_ib -> {
                showDialog("홈으로 이동","아직 저장된 발자국 기록이 없습니다.\n여행 작성을 취소하시겠습니까?","확인")
            }
            R.id.tripcourse_title_fl -> {
                val intent = Intent(this, TripActivity::class.java)
                val gson = Gson()
                val tripData = gson.toJson(tripData)

                intent.putExtra("tripDataFromTripcourse", tripData)
                startActivity(intent)
            }
        }
    }

    override fun onOKClicked() {
        super.onOKClicked()
        finish()
    }

    private fun startRecordActivity(course: TripCourse) {
        val intent = Intent(this, RecordActivity::class.java)
        val gson = Gson()
        val itemSelected = gson.toJson(course)
        intent.putExtra("courseData", itemSelected)
        startActivity(intent)
    }

    override fun onBackPressed() {
        val dlg = BaseDialog(this)
        dlg.listener = CancleDialog()
        dlg.show("여행 작성 취소", "여행 작성을 취소하시겠습니까?\n 입력 내용은 저장되지않습니다.", "확인")
    }

    inner class CancleDialog(): BaseDialog.BaseDialogClickListener {
        override fun onOKClicked() {
            //todo 카드 내용 삭제
            finish()
        }

        override fun onCancelClicked() {

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