package com.olympos.tripbook.src.record

import android.os.Bundle
import android.view.View
import com.olympos.tripbook.R
import com.olympos.tripbook.config.BaseActivity
import com.olympos.tripbook.databinding.ActivityRecordBinding

class RecordActivity : BaseActivity() {
    private lateinit var binding: ActivityRecordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        //상단바
        binding.recordTopbarLayout.topbarTitleTv.setText(R.string.record_title)
        //todo 여행제목 가져오기
//        binding.recordTopbarLayout.topbarSubtitleTv.setText()
        binding.recordTopbarLayout.topbarSubbuttonIb.setImageResource(R.drawable.btn_base_check_black)
    }
}