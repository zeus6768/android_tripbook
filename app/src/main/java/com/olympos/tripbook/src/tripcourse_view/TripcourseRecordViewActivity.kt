package com.olympos.tripbook.src.tripcourse_view

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.olympos.tripbook.R
import com.olympos.tripbook.config.BaseActivity
import com.olympos.tripbook.databinding.ActivityTripcourseRecordViewBinding
import com.olympos.tripbook.src.tripcourse.model.Card
import com.olympos.tripbook.src.tripcourse_view.model.filledCards
import com.olympos.tripbook.src.tripcourse_view.model.focusedViewCardPosition

class TripcourseRecordViewActivity : BaseActivity() {

    private lateinit var binding : ActivityTripcourseRecordViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTripcourseRecordViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        Log.d("checkpoint", "TripcourseRecordViewActivity start")
        binding.tripcourseRecordViewTopbarLayout.topbarTitleTv.setText(R.string.tripcourse_record_title)
        binding.tripcourseRecordViewTopbarLayout.topbarSubbuttonIb.setImageResource(R.drawable.btn_base_check_black)
        binding.tripcourseRecordViewTopbarLayout.topbarSubtitleTv.visibility = View.GONE

        binding.tripcourseRecordViewBodyTv.text = filledCards[focusedViewCardPosition].body
        binding.tripcourseRecordViewTitleTv.text = filledCards[focusedViewCardPosition].title
        binding.tripcourseRecordViewSelectDateBtn.text = filledCards[focusedViewCardPosition].date
        Glide.with(this.applicationContext).load(filledCards[focusedViewCardPosition].coverImg).into(binding.tripcourseRecordImgIv)

        binding.tripcourseRecordViewBodyTv.text = filledCards[focusedViewCardPosition].body
        binding.tripcourseRecordViewTitleTv.text = filledCards[focusedViewCardPosition].title
        binding.tripcourseRecordViewSelectDateBtn.text = filledCards[focusedViewCardPosition].date

        binding.tripcourseRecordViewTopbarLayout.topbarSubbuttonIb.setOnClickListener(this)
        binding.tripcourseRecordViewTopbarLayout.topbarBackIb.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        super.onClick(v)
        when(v!!.id) {
            R.id.topbar_back_ib -> finish()
            R.id.topbar_subbutton_ib -> {
                //todo 카드 수정페이지
                Toast.makeText(this, "카드 수정페이지로 이동하도록 연결", Toast.LENGTH_SHORT).show()
            }
        }
    }
}