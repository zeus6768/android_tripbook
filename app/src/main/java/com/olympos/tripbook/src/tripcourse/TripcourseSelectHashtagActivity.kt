package com.olympos.tripbook.src.tripcourse

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.olympos.tripbook.R
import com.olympos.tripbook.config.BaseActivity
import com.olympos.tripbook.databinding.ActivityTripcourseSelectHashtagBinding

class TripcourseSelectHashtagActivity : BaseActivity() {

    private val HASHTAG_ACTIVITY_CODE = 20

    lateinit var binding : ActivityTripcourseSelectHashtagBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTripcourseSelectHashtagBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        //topbar layout view randering
        binding.tripcourseSelectHashtagTopbarLayout.topbarTitleTv.setText(R.string.tripcourse_select_hashtag_title)
        binding.tripcourseSelectHashtagTopbarLayout.topbarSubtitleTv.visibility = View.GONE
        binding.tripcourseSelectHashtagTopbarLayout.topbarSubbuttonIb.visibility = View.GONE

        //상단바 - 뒤로가기 버튼 - TripcourseRecordActivity로 이동
        binding.tripcourseSelectHashtagTopbarLayout.topbarBackIb.setOnClickListener {
            showDialog("해시태그 선택 취소", "해시태그 선택을 취소하시겠습니까?\n"
                    + "선택된 정보는 저장되지 않습니다.", "취소하기")
            finish()
        }

        //선택 완료 버튼 - TripcourseRecordActivity로 이동(with 선택 결과)
        binding.tripcourseSelectHashtagSelectCompleteBtn.setOnClickListener {
            val intent = Intent(this@TripcourseSelectHashtagActivity, TripcourseRecordActivity::class.java)

            //해시태그 정보 intent에 삽입

            intent.putExtra("hashtag_result", intent)
            setResult(HASHTAG_ACTIVITY_CODE, intent)
            finish()
        }
    }
}