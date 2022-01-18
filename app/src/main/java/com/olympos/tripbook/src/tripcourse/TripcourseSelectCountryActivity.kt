package com.olympos.tripbook.src.tripcourse

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
        binding.tripcourseSelectCountryTopbarLayout.topbarTitleTv.setText(R.string.tripcourse_select_country_title)
        binding.tripcourseSelectCountryTopbarLayout.topbarSubtitleTv.visibility = View.GONE
        binding.tripcourseSelectCountryTopbarLayout.topbarSubtitleTv.visibility = View.GONE

    }
}