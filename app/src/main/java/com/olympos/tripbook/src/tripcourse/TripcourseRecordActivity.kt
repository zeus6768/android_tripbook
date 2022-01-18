package com.olympos.tripbook.src.tripcourse

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
        binding.tripcourseRecordTopbarLayout.topbarSubbuttonIb.setOnClickListener {
        }

        //여행 날짜 선택
        binding.tripcourseRecordSelectDateBtn.setOnClickListener {

        }

        //여행 도시 선택 :
        binding.tripcourseRecordSelectCountryBtn.setOnClickListener {

        }
    }
}