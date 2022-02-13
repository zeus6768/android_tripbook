package com.olympos.tripbook.src.tripcourse_view

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.olympos.tripbook.R
import com.olympos.tripbook.config.BaseActivity
import com.olympos.tripbook.databinding.ActivityTripcourseRecordViewBinding
import com.olympos.tripbook.src.tripcourse.model.Card

class TripcourseRecordViewActivity : BaseActivity() {

    private lateinit var binding : ActivityTripcourseRecordViewBinding

    private var card = Card()
    private var gson : Gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        initView()
    }

    private fun initView() {
        binding.tripcourseRecordViewTopbarLayout.topbarTitleTv.setText(R.string.tripcourse_record_title)
        binding.tripcourseRecordViewTopbarLayout.topbarSubbuttonIb.setImageResource(R.drawable.btn_base_check_black)
        binding.tripcourseRecordViewTopbarLayout.topbarSubtitleTv.visibility = View.GONE

        card = gson.fromJson(intent.getStringExtra("card"), card::class.java)
        binding.tripcourseRecordViewBodyTv.hint = card.body
        binding.tripcourseRecordViewTitleTv.hint = card.title
        binding.tripcourseRecordViewSelectDateBtn.text = card.date
        Glide.with(this.applicationContext).load(card.coverImg).into(binding.tripcourseRecordImgIv)

        binding.tripcourseRecordViewBodyTv.text = card.body
        binding.tripcourseRecordViewTitleTv.text = card.title
        binding.tripcourseRecordViewSelectDateBtn.text = card.date

        binding.tripcourseRecordViewTopbarLayout.topbarBackIb.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        super.onClick(v)
        when(v!!.id) {
            R.id.topbar_back_ib -> finish()
        }
    }
}