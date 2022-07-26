package com.olympos.tripbook.src.user

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import com.olympos.tripbook.R
import com.olympos.tripbook.config.BaseActivity
import com.olympos.tripbook.databinding.ActivityUserMypageBinding
import com.olympos.tripbook.src.home.MainActivity
import com.olympos.tripbook.src.splash.SplashActivity
import com.olympos.tripbook.utils.getNickname

class MypageActivity : BaseActivity() {
    private lateinit var binding: ActivityUserMypageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserMypageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()

        binding.mypageTopbarLayout.topbarBackIb.setOnClickListener(this)
        binding.mypageTopbarLayout.topbarSubbuttonIb.setOnClickListener(this)
        binding.mypageHistoryButtonTv.setOnClickListener(this)
        binding.mypageHistoryButtonView.setOnClickListener(this)

    }

    @SuppressLint("SetTextI18n")
    private fun initView() {
        binding.mypageTopbarLayout.topbarTitleTv.text = "마이페이지"
        binding.mypageTopbarLayout.topbarSubtitleTv.text = ""

        binding.mypageProfileNameTv.text = getNickname() + "님"
        binding.mypageHistoryNameTv.text = getNickname() + "님의 추억"
        binding.mypageHistoryTitleTv.text = getNickname() + "님의 활동 히스토리"
    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.topbar_back_ib ->
                finish()
            R.id.topbar_subbutton_ib ->
                startMainActivity()
            R.id.mypage_history_button_tv, R.id.mypage_history_button_view ->
                startMainActivity()
        }
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}