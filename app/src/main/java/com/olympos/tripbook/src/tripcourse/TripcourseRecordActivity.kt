package com.olympos.tripbook.src.tripcourse

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.olympos.tripbook.R
import com.olympos.tripbook.config.BaseActivity
import com.olympos.tripbook.databinding.ActivityTripcourseRecordBinding
import com.olympos.tripbook.src.tripcourse.model.Card
import com.olympos.tripbook.src.tripcourse.model.Hashtag

class TripcourseRecordActivity : BaseActivity() {

    lateinit var binding : ActivityTripcourseRecordBinding

    private var card : Card = Card()
    private var hashtag : Hashtag = Hashtag()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTripcourseRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()

        addHashtagDumyInfo()
        getInputInfo()

        //click 리스너
        binding.tripcourseRecordTopbarLayout.topbarBackIb.setOnClickListener(this)
        binding.tripcourseRecordTopbarLayout.topbarSubbuttonIb.setOnClickListener(this)
        binding.tripcourseRecordImgCl.setOnClickListener(this)

        //내용 최대 200자 이벤트 처리
        binding.tripcourseRecordContentEt.addTextChangedListener(object : TextWatcher {
            val wordCountTv = binding.tripcourseRecordContentWordcountTv
            var userInput = binding.tripcourseRecordContentEt

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
                    Toast.makeText(this@TripcourseRecordActivity, "200자까지 입력 가능합니다.", Toast.LENGTH_SHORT).show()
                }
            }
        })

        addHashtagDumyInfo()

        getInputInfo()
    }

    private fun initView() {
        //상단바
        binding.tripcourseRecordTopbarLayout.topbarTitleTv.setText(R.string.tripcourse_record_title)
        binding.tripcourseRecordTopbarLayout.topbarSubbuttonIb.setImageResource(R.drawable.btn_base_check_black)
        binding.tripcourseRecordTopbarLayout.topbarSubtitleTv.visibility = View.GONE

        //여행 날짜 선택 - Dialog 생성
        binding.tripcourseRecordSelectDateBtn.setOnClickListener {

        }

        //여행 도시 선택 - TripcourseSelectContryActivity로 이동
        binding.tripcourseRecordSelectCountryBtn.setOnClickListener {
            val intent = Intent(this@TripcourseRecordActivity, TripcourseSelectCountryActivity::class.java)
            startActivity(intent)
        }

        //해시태그 선택 - TripcourseSelectHashtagActivity로 이동
        binding.tripcourseRecordHashtagAddBtn.setOnClickListener {
            val intent =
                Intent(this@TripcourseRecordActivity, TripcourseSelectHashtagActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onClick(v: View?) {
        super.onClick(v)

        when (v!!.id) {
            R.id.topbar_back_ib ->
                showDialog("안내", "여행 발자국 기록을 취소하시겠습니까?", "확인")
            R.id.topbar_subbutton_ib -> {
                //todo 저장완료
            }
            R.id.tripcourse_record_img_cl ->
                photoSelect()
        }
    }

    private fun photoSelect() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivity(intent)
    }

    override fun onOKClicked() {
        super.onOKClicked()
        startTripcourseActivity()
    }

    private fun startTripcourseActivity() {
        val intent = Intent(this, TripcourseActivity::class.java)
        startActivity(intent)
    }

    private fun addHashtagDumyInfo() {
//        var i = 0
//        for(i in 0..10) {
//            hashtagInfo.location
//            hashtagInfo.weather
//            hashtagInfo.feeling
//            hashtagInfo.companion
//            hashtagInfo.event
//        }
    }

    private fun getInputInfo() {
        if(binding.tripcourseRecordTitleEt.text.toString().isEmpty()) {
            //입력이 안된 경우
        } else {
            card.cardTitle = binding.tripcourseRecordTitleEt.text.toString()
        }
        if(!binding.tripcourseRecordContentEt.text.toString().isEmpty()){
            card.body = binding.tripcourseRecordContentEt.text.toString()
        }
    }
}