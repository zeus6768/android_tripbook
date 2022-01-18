package com.olympos.tripbook.src.tripcourse

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.olympos.tripbook.R
import com.olympos.tripbook.config.BaseActivity
import com.olympos.tripbook.databinding.ActivityTripcourseSelectCountryBinding

class TripcourseSelectCountryActivity : BaseActivity() {

    lateinit var binding : ActivityTripcourseSelectCountryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTripcourseSelectCountryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        //topbar layout view randering
        binding.tripcourseSelectCountryTopbarLayout.topbarTitleTv.setText(R.string.tripcourse_select_country_title)
        binding.tripcourseSelectCountryTopbarLayout.topbarSubtitleTv.visibility = View.GONE
        binding.tripcourseSelectCountryTopbarLayout.topbarSubbuttonIb.visibility = View.GONE

        //상단바 - 뒤로가기 버튼
        binding.tripcourseSelectCountryTopbarLayout.topbarBackIb.setOnClickListener {
            //선택을 취소하시겠습니까 다이어로그 뜨기
            val intent = Intent(this@TripcourseSelectCountryActivity, TripcourseRecordActivity::class.java)
            startActivity(intent)
        }

        //선택 완료 버튼 - TripcourseRecordActivity로 이동(with 선택 결과)
        binding.tripcourseSelectCountrySelectCompleteBtn.setOnClickListener {
            val intent = Intent(this@TripcourseSelectCountryActivity, TripcourseRecordActivity::class.java)
            startActivity(intent)
        }

    }
}