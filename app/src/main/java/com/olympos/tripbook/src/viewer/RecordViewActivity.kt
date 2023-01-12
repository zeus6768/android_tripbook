package com.olympos.tripbook.src.viewer

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.olympos.tripbook.R
import com.olympos.tripbook.config.BaseActivity
import com.olympos.tripbook.databinding.ActivityRecordViewBinding
import com.olympos.tripbook.src.tripcourse.model.TripCourse

class RecordViewActivity : BaseActivity() {

    private lateinit var binding: ActivityRecordViewBinding
    val tripCourse: TripCourse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecordViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        binding.recordViewTopbarLayout.topbarTitleTv.setText(R.string.tripcourse_title)
        //todo sub button 이미지 바꿔야함
        binding.recordViewTopbarLayout.topbarSubbuttonIb.setImageResource(R.drawable.btn_base_check_black)
        binding.recordViewTopbarLayout.topbarSubtitleTv.visibility = View.GONE

        //todo 서버에서 이 카드 데이터 가져와야 함

        if( tripCourse != null ) {
            binding.recordViewBodyTv.text = tripCourse.courseComment
            binding.recordViewTitleTv.text = tripCourse.courseTitle

            binding.recordViewSelectDateBtn.text = tripCourse.courseDate
            //todo 이미지 넣어야 함
//            Glide.with(this.applicationContext).load(tripCourse[focusedViewCardPosition].imgUrl).into(binding.tripcourseRecordImgIv)
        }
        else {
            Toast.makeText(this, "카드 내용 없음", Toast.LENGTH_SHORT).show()
            finish()
        }

        binding.recordViewTopbarLayout.topbarSubbuttonIb.setOnClickListener(this)
        binding.recordViewTopbarLayout.topbarBackIb.setOnClickListener(this)
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