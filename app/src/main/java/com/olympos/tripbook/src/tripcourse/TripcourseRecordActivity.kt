package com.olympos.tripbook.src.tripcourse

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.google.gson.Gson
import com.olympos.tripbook.R
import com.olympos.tripbook.config.BaseActivity
import com.olympos.tripbook.databinding.ActivityTripcourseRecordBinding
import com.olympos.tripbook.src.tripcourse.model.Card


class TripcourseRecordActivity : BaseActivity() {

    lateinit var binding : ActivityTripcourseRecordBinding

    private var card : Card = Card()
//    private var hashtag : Hashtag = Hashtag()

    private val COUNTRY_ACTIVITY_CODE = 10
    private val HASHTAG_ACTIVITY_CODE = 20

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTripcourseRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()

        addHashtagDumyInfo()

        getInputInfo()
    }

    //종료된 액티비티에서 정보 받아오기
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(resultCode) {
            COUNTRY_ACTIVITY_CODE -> { //SelectCountryActivity에서 장소 정보 가져오기
                card.cardCountry = data?.getStringExtra("country_result")!!
                binding.tripcourseRecordSelectCountryBtn.setText(card.cardCountry)
            }
            HASHTAG_ACTIVITY_CODE -> { //SelectHashtagActivity에서 해시태그 정보 가져오기
                //해시태그 저장
            }
        }
    }

    private fun initView() {
        //topbar layout view randering
        binding.tripcourseRecordTopbarLayout.topbarTitleTv.setText(R.string.tripcourse_record_title)
        binding.tripcourseRecordTopbarLayout.topbarSubbuttonIb.setImageResource(R.drawable.btn_base_check_black)

        //상단바 - 뒤로가기 버튼
        binding.tripcourseRecordTopbarLayout.topbarBackIb.setOnClickListener {
            showDialog("발자국 작성 취소", "발자국 작성을 취소하시겠습니까?\n"
                    + "입력중이던 정보는 저장되지 않습니다.", "취소하기")
            finish()
        }

        //상단바 - 편집완료 버튼
        binding.tripcourseRecordTopbarLayout.topbarSubbuttonIb.setOnClickListener {
            //저장 완료
        }

        //여행 사진 추가
        binding.tripcourseRecordRequestImgBtn.setOnClickListener {
            //사진 업로드
        }

        //여행 날짜 선택 - Dialog 생성
        binding.tripcourseRecordSelectDateBtn.setOnClickListener {

        }

        //여행 도시 선택 - TripcourseSelectContryActivity로 이동
        binding.tripcourseRecordSelectCountryBtn.setOnClickListener {
            val intent = Intent(this@TripcourseRecordActivity, TripcourseSelectCountryActivity::class.java)
            startActivity(intent)
        }

        //해시태그 선택 - TripcourseSelectHashtagActivity로 이동
        binding.tripcourseRecordRequestHashtagBtn.setOnClickListener {
            val intent = Intent(this@TripcourseRecordActivity, TripcourseSelectHashtagActivity::class.java)
            startActivity(intent)
        }
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
        //여행 제목 입력 안된 경우
        if(binding.tripcourseRecordRequestTitleEt.text.toString().isEmpty()) {
            val myToast = Toast.makeText(this.applicationContext, "제목을 입력해 주세요", Toast.LENGTH_SHORT)
            myToast.show()
        } else {
            card.cardTitle = binding.tripcourseRecordRequestTitleEt.text.toString()
        }
        //내용이 비지 않았다면 가져오기
        if(!binding.tripcourseRecordRequestBodyEt.text.toString().isEmpty()){
            card.body = binding.tripcourseRecordRequestBodyEt.text.toString()
        }

        //날짜 가져오기

        //도시 가져오기

        //해시태그 가져오기

    }
}