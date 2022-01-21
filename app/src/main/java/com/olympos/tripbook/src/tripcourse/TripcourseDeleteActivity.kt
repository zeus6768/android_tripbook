package com.olympos.tripbook.src.tripcourse

import android.os.Bundle
import android.view.View
import com.olympos.tripbook.R
import com.olympos.tripbook.config.BaseActivity
import com.olympos.tripbook.databinding.ActivityTripcourseDeleteBinding

class TripcourseDeleteActivity : BaseActivity() {

    lateinit var binding : ActivityTripcourseDeleteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTripcourseDeleteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        //topbar layout view randering
        binding.tripcourseDeleteTopbarLayout.topbarTitleTv.setText(R.string.tripcourse_delete_title)
        binding.tripcourseDeleteTopbarLayout.topbarSubtitleTv.visibility = View.GONE
        binding.tripcourseDeleteTopbarLayout.topbarSubbuttonIb.visibility = View.GONE

        //상단바 - 뒤로가기 버튼 -> 홈으로 이동하는건가??
        binding.tripcourseDeleteTopbarLayout.topbarBackIb.setOnClickListener {
//            val intent = Intent(this@TripcourseDeleteActivity, TripcourseRecordActivity::class.java)
//            startActivity(intent)
        }

        //홈 버튼
        binding.tripcourseDeleteHomeBtn.setOnClickListener {
//            val intent = Intent(this@TripcourseDeleteActivity, TripcourseRecordActivity::class.java)
//            startActivity(intent)
        }

    }
}