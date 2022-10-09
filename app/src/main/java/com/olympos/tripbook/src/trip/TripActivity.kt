package com.olympos.tripbook.src.trip

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import com.olympos.tripbook.R
import com.olympos.tripbook.config.BaseActivity
import com.olympos.tripbook.config.BaseDialog
import com.olympos.tripbook.databinding.ActivityTripBinding
import com.olympos.tripbook.src.trip.controller.TripApiController
import com.olympos.tripbook.src.trip.model.Trip
import com.olympos.tripbook.src.trip.view.PostTripView
import com.olympos.tripbook.src.tripcourse.TripCourseActivity
import com.olympos.tripbook.utils.getUserIdx
import com.olympos.tripbook.utils.saveTripIdx

class TripActivity : BaseActivity(), PostTripView {
    private lateinit var binding: ActivityTripBinding

    private var trip = Trip()
    val gson = Gson()
//    private var decorator = RangeDayDecorator(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTripBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()

        //여행 정보 가져옴
        if(intent.hasExtra("tripDataFromTripcourse")) {
            val json = intent.getStringExtra("tripDataFromTripcourse")
            trip = gson.fromJson(json, Trip::class.java)

            initTripDataView(trip)
        }
    }

    private fun initView() {
        //상단바
        binding.tripTopbarLayout.topbarTitleTv.setText(R.string.trip_title)
        binding.tripTopbarLayout.topbarSubtitleTv.visibility = View.GONE
        binding.tripTopbarLayout.topbarSubbuttonIb.visibility = View.GONE

        //제목 글자수 제한
        binding.tripTitleEt.addTextChangedListener(object : TextWatcher {
            var userInput = binding.tripTitleEt

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }

            override fun afterTextChanged(s: Editable?) {
                if (userInput.isFocused && userInput.length() > 200) {
                    userInput.setText(s.toString().substring(0, 200))
                    userInput.setSelection(s!!.length - 1)
                    Toast.makeText(this@TripActivity, "14자까지 입력 가능합니다.", Toast.LENGTH_SHORT).show()
                }
            }
        })

        //캘린더
        val calendar = binding.tripCalendarMcv
        trip.userIdx = getUserIdx().toString()

        //날짜 선택
        calendar.topbarVisible = true
        calendar.setOnRangeSelectedListener { _, dates ->

            // yyyy-MM-dd 패턴 찾아 문자열 추출
            val dMatch = Regex("(\\d+).(\\d+).(\\d+)").find(dates.first().toString())!!
            val aMatch = Regex("(\\d+).(\\d+).(\\d+)").find(dates.last().toString())!!
            val departureDate = dMatch.value
            val arrivalDate = aMatch.value

            //출발일
            binding.tripDateDepartureMonthTv.text = departureDate.split("-")[1]
            binding.tripDateDepartureDayTv.text = departureDate.split("-")[2]
            //도착일
            binding.tripDateArrivalMonthTv.text = arrivalDate.split("-")[1]
            binding.tripDateArrivalDayTv.text = arrivalDate.split("-")[2]

            trip.departureDate = departureDate
            trip.arrivalDate = arrivalDate

        }

        //click 리스너
        binding.tripTopbarLayout.topbarBackIb.setOnClickListener(this)
        binding.tripTopbarLayout.topbarSubbuttonIb.setOnClickListener(this)
//        binding.tripThemeTheme1Ll.setOnClickListener(this)
//        binding.tripThemeTheme2Ll.setOnClickListener(this)
//        binding.tripThemeTheme3Ll.setOnClickListener(this)
//        binding.tripThemeTheme4Ll.setOnClickListener(this)
        binding.tripNextStepBtnTv.setOnClickListener(this)
        binding.tripCalendarMcv.setOnClickListener(this)
    }

    private fun initTripDataView(tripData: Trip) {
        //상단바
        binding.tripTopbarLayout.topbarTitleTv.setText("여행 수정하기")

        binding.tripTitleEt.hint = tripData.tripTitle

        //출발일
        binding.tripDateDepartureMonthTv.text = tripData.departureDate.split("-")[1]
        binding.tripDateDepartureDayTv.text = tripData.departureDate.split("-")[2]
        //도착일
        binding.tripDateArrivalMonthTv.text = tripData.arrivalDate.split("-")[1]
        binding.tripDateArrivalDayTv.text = tripData.arrivalDate.split("-")[2]
    }

    override fun onClick(v: View?) {
        super.onClick(v)

        when (v!!.id) {
            R.id.topbar_back_ib -> {
                val dlg = BaseDialog(this)
                dlg.listener = CancleDialog()
                dlg.show("여행 작성 취소", "여행 작성을 취소하시겠습니까?\n 입력 내용은 저장되지않습니다.", "확인")
            }
//            R.id.trip_theme_theme1_ll, R.id.trip_theme_theme2_ll, R.id.trip_theme_theme3_ll ->
//                themeSelected(v)
//            R.id.trip_theme_theme4_ll ->
//                Toast.makeText(this, "추후 추가 예정", Toast.LENGTH_SHORT).show()
            R.id.trip_next_step_btn_tv -> {
                if(intent.hasExtra("tripDataFromTripcourse")) {
                    if( binding.tripTitleEt.text.isNotEmpty() ) {
                        trip.tripTitle = binding.tripTitleEt.text.toString()
                    }
                    val returnIntent = Intent(this@TripActivity, TripCourseActivity::class.java)
                    returnIntent.putExtra("modifiedTrip", gson.toJson(trip))
                    setResult(999981, returnIntent) //임시 코드
                    finish()
                    return
                }

                if( !checkValidation() ) { return }
                //제목 입력
                trip.tripTitle = binding.tripTitleEt.text.toString()

                startTripcourseActivity()

                //postTrip(trip)

                Log.d("api test 확인용", " userIdx: " + trip.userIdx + " tripTitle: " + trip.tripTitle +
                        " departureDate: " + trip.departureDate + " arrivalDate: " + trip.arrivalDate + " themeIdx: " + trip.themeIdx)
            }
        }
    }

    //성공시 true 리턴
    private fun checkValidation(): Boolean {
        if( binding.tripTitleEt.text.isEmpty() ) {
            Toast.makeText(this, "제목을 입력해주세요", Toast.LENGTH_SHORT).show()
            return false
        }
        if( trip.departureDate == "" ) {
            Toast.makeText(this, "출발일을 지정해주세요.", Toast.LENGTH_SHORT).show()
            return false
        }
        if( trip.arrivalDate == "" ) {
            Toast.makeText(this, "도착일을 지정해주세요.", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    //테마 선택 시 글씨색 변경 및 테두리색 변경
//    private fun themeSelected(v: View?) {
//        Log.d("click listener", "themeSelect")
//        when (v!!.id) {
//            R.id.trip_theme_theme1_ll -> {
//                binding.tripThemeTheme1Cv.setStrokeColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.tripbook_main_1)))
//                binding.tripThemeTheme1Tv.setTextColor(ContextCompat.getColor(this, R.color.tripbook_main_1))
//                binding.tripThemeTheme2Cv.setStrokeColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.dark_gray)))
//                binding.tripThemeTheme2Tv.setTextColor(ContextCompat.getColor(this, R.color.dark_gray))
//                binding.tripThemeTheme3Cv.setStrokeColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.dark_gray)))
//                binding.tripThemeTheme3Tv.setTextColor(ContextCompat.getColor(this, R.color.dark_gray))
//
//                trip.themeIdx = 1
//            }
//            R.id.trip_theme_theme2_ll -> {
//                binding.tripThemeTheme1Cv.setStrokeColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.dark_gray)))
//                binding.tripThemeTheme1Tv.setTextColor(ContextCompat.getColor(this, R.color.dark_gray))
//                binding.tripThemeTheme2Cv.setStrokeColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.tripbook_main_1)))
//                binding.tripThemeTheme2Tv.setTextColor(ContextCompat.getColor(this, R.color.tripbook_main_1))
//                binding.tripThemeTheme3Cv.setStrokeColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.dark_gray)))
//                binding.tripThemeTheme3Tv.setTextColor(ContextCompat.getColor(this, R.color.dark_gray))
//
//                trip.themeIdx = 2
//            }
//            R.id.trip_theme_theme3_ll -> {
//                binding.tripThemeTheme1Cv.setStrokeColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.dark_gray)))
//                binding.tripThemeTheme1Tv.setTextColor(ContextCompat.getColor(this, R.color.dark_gray))
//                binding.tripThemeTheme2Cv.setStrokeColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.dark_gray)))
//                binding.tripThemeTheme2Tv.setTextColor(ContextCompat.getColor(this, R.color.dark_gray))
//                binding.tripThemeTheme3Cv.setStrokeColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.tripbook_main_1)))
//                binding.tripThemeTheme3Tv.setTextColor(ContextCompat.getColor(this, R.color.tripbook_main_1))
//
//                trip.themeIdx = 3
//            }
//        }
//    }

    private fun postTrip(trip: Trip) {
        val tripApiController = TripApiController()
        tripApiController.setPostTripView(this)
        tripApiController.postTrip(trip)
    }

    override fun onPostTripLoading() {
        binding.tripLoadingPb.visibility = View.VISIBLE
    }

    override fun onPostTripSuccess(result : Int) {
        binding.tripLoadingPb.visibility = View.GONE

        // response로 받은 tripIdx를 저장한 후 TripcourseActivity 실행
        saveTripIdx(result)
        startTripcourseActivity()
    }

    override fun onPostTripFailure(code: Int, message: String) {
        binding.tripLoadingPb.visibility = View.GONE

        when(code) {
            400 -> Toast.makeText(this, "네트워크 상태를 확인해주세요.", Toast.LENGTH_SHORT).show()
            2101 -> Toast.makeText(this, "제목을 입력해주세요", Toast.LENGTH_SHORT).show()
            2102 -> Toast.makeText(this, "출발일을 지정해주세요.", Toast.LENGTH_SHORT).show()
            2103 -> Toast.makeText(this, "도착일을 지정해주세요.", Toast.LENGTH_SHORT).show()
            2104 -> Toast.makeText(this, "테마를 선택해주세요.", Toast.LENGTH_SHORT).show()
            2105 -> Toast.makeText(this, "유저 인덱스 오류", Toast.LENGTH_SHORT).show()
            2107 -> Toast.makeText(this, "제목을 14자 이내로 입력해주세요.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun startTripcourseActivity() {
        val intent = Intent(this, TripCourseActivity::class.java)
        val tripData = gson.toJson(trip)

        intent.putExtra("tripData", tripData)
        startActivity(intent)

        Log.d("__tripData__ trip", tripData)
        Log.d("userIdx Check", trip.userIdx)
        finish()
    }

    override fun onBackPressed() {
        val dlg = BaseDialog(this)
        dlg.listener = CancleDialog()
        dlg.show("여행 작성 취소", "여행 작성을 취소하시겠습니까?\n 입력 내용은 저장되지않습니다.", "확인")
    }

    inner class CancleDialog: BaseDialog.BaseDialogClickListener {
        override fun onOKClicked() {
            //todo Trip 내용 삭제
            finish()
        }

        override fun onCancelClicked() {

        }
    }
}