package com.olympos.tripbook.src.trip

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import com.olympos.tripbook.R
import com.olympos.tripbook.config.BaseActivity
import com.olympos.tripbook.databinding.ActivityRecordBinding
import com.olympos.tripbook.src.tripcourse.model.TripCourse

class RecordActivity : BaseActivity(), DateSelectDialog.DialogClickListener {
    private lateinit var binding: ActivityRecordBinding
    lateinit var tripDate: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        //상단바
        binding.recordTopbarLayout.topbarTitleTv.setText(R.string.record_title)
        binding.recordTopbarLayout.topbarSubbuttonIb.setImageResource(R.drawable.btn_base_check_black)
        //todo 여행제목 가져오기
        //  binding.recordTopbarLayout.topbarSubtitleTv.setText()

        //저장된 내용이 있다면 보여주기
        if(intent.hasExtra("courseData")) {
            val gson = Gson()
            val json = intent.getStringExtra("courseData")
            val courseData = gson.fromJson(json, TripCourse::class.java)
            setCourse(courseData)
        }

        //body : 내용 최대 200자 이벤트 처리
        binding.recordBodyEt.addTextChangedListener(object : TextWatcher {
            val wordCountTv = binding.recordContentWordcountTv
            var userInput = binding.recordBodyEt

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                wordCountTv.text = "0 / 200"
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                wordCountTv.text = userInput.length().toString() + " / 200"
            }

            override fun afterTextChanged(s: Editable?) {
                if (userInput.isFocused && userInput.length() > 200) {
                    userInput.setText(s.toString().substring(0, 200))
                    userInput.setSelection(s!!.length - 1)
                    Toast.makeText(this@RecordActivity,
                        "200자까지 입력 가능합니다.",
                        Toast.LENGTH_SHORT).show()
                }
            }
        })

        //click 리스너
        binding.recordTopbarLayout.topbarBackIb.setOnClickListener(this)
        binding.recordTopbarLayout.topbarSubbuttonIb.setOnClickListener(this)
        binding.recordSelectDateBtn.setOnClickListener(this)
        binding.recordSelectCountryBtn.setOnClickListener(this)
        binding.recordHashtagAddBtn.setOnClickListener(this)
    }

    private fun setCourse(course: TripCourse) {
        binding.recordTitleEt.hint = course.courseTitle
        binding.recordBodyEt.hint = course.courseContent
        binding.recordSelectDateBtn.text = course.courseDate
    }

    override fun onClick(v: View?) {
        super.onClick(v)

        when (v!!.id) {
            R.id.topbar_back_ib ->
                showDialog("발자국 작성 취소", "발자국 작성을 취소하시겠습니까?\n" + "작성하셨던 내용은 사라집니다.", "확인")
            R.id.topbar_subbutton_ib -> {
                //todo 입력받은 정보 저장
                saveInfo()
            }
            R.id.record_select_date_btn -> {
                showDateDialog("여행 날짜 선택", "날짜 선택 완료")
            }
            R.id.record_select_country_btn -> {

            }
            R.id.record_hashtag_add_btn -> {

            }
        }
    }

    //상단바 - 뒤로가기 버튼 -> 종료(저장 x)
    override fun onOKClicked() {
        super.onOKClicked()
        finish()
    }

    private fun saveInfo() {
        //정보 저장 후 나가기
        finish()
    }

    private fun showDateDialog(title: String, okMessage: String) {
        val dig = DateSelectDialog(this)
        dig.listener = this
        dig.show(title, okMessage)
    }

    override fun onDateOKClicked(selectedYear: Int, selectedMonth: Int, selectedDay: Int) {
        binding.recordSelectDateBtn.text = String.format("%d년 %d월 %d일", selectedYear, selectedMonth, selectedDay)
        tripDate = String.format("%d-%d-%d", selectedYear, selectedMonth, selectedDay)
    }

    override fun onDateCancelClicked() {
        TODO("Not yet implemented")
    }
}