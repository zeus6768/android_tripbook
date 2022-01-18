package com.olympos.tripbook.src.tripcourse

import android.os.Bundle
import android.view.View
import com.olympos.tripbook.R
import com.olympos.tripbook.config.BaseActivity
import com.olympos.tripbook.databinding.ActivityTripcourseSelectHashtagBinding

class TripcourseSelectHashtagActivity : BaseActivity() {

    lateinit var binding : ActivityTripcourseSelectHashtagBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTripcourseSelectHashtagBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        binding.tripcourseSelectHashtagTopbarLayout.topbarTitleTv.setText(R.string.tripcourse_select_hashtag_title)
        binding.tripcourseSelectHashtagTopbarLayout.topbarSubtitleTv.visibility = View.GONE
        binding.tripcourseSelectHashtagTopbarLayout.topbarSubtitleTv.visibility = View.GONE

    }
}