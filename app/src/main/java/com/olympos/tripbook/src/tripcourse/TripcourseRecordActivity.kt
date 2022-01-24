package com.olympos.tripbook.src.tripcourse

import android.content.Intent
import android.os.Bundle
import com.olympos.tripbook.R
import com.olympos.tripbook.config.BaseActivity
import com.olympos.tripbook.databinding.ActivityTripcourseRecordBinding

class TripcourseRecordActivity : BaseActivity() {

    lateinit var binding : ActivityTripcourseRecordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTripcourseRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        //topbar layout view randering
        binding.tripcourseRecordTopbarLayout.topbarTitleTv.setText(R.string.tripcourse_record_title)
        binding.tripcourseRecordTopbarLayout.topbarSubbuttonIb.setImageResource(R.drawable.btn_base_check_black)

        //상단바 - 뒤로가기 버튼
        binding.tripcourseRecordTopbarLayout.topbarBackIb.setOnClickListener {
            //다이어로그 뜨기(여행 발자국 기록을 취소하시겠습니까?)
            //확인 -> TripcourseActivity로 이동
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
}