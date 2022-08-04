package com.olympos.tripbook.src.mypage

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.olympos.tripbook.R
import com.olympos.tripbook.config.BaseActivity
import com.olympos.tripbook.databinding.ActivityUserMypageBinding
import com.olympos.tripbook.src.home.MainActivity
import com.olympos.tripbook.src.trip.TripCountView
import com.olympos.tripbook.src.home.model.HomeService
import com.olympos.tripbook.src.trip.model.Trip
import com.olympos.tripbook.utils.getNickname

class MyPageActivity : BaseActivity(), TripCountView {

    private val homeService = HomeService()

    private lateinit var binding: ActivityUserMypageBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityUserMypageBinding.inflate(layoutInflater)

        homeService.setHomeProcess(this)

        initView()

        initTripHistoryView()

        setContentView(binding.root)

    }

    override fun onResume() {

        super.onResume()

        homeService.getTripCount()

    }

    @SuppressLint("SetTextI18n")
    private fun initView() {
        binding.mypageTopbarLayout.topbarTitleTv.text = "나의 지난 여행"
        binding.mypageTopbarLayout.topbarSubtitleTv.text = ""

        binding.mypageProfileNameTv.text = getNickname() + "님"
        binding.mypageHistoryNameTv.text = getNickname() + "님의 추억"
        binding.mypageHistoryTitleTv.text = getNickname() + "님의 활동 히스토리"

        binding.mypageTopbarLayout.topbarBackIb.setOnClickListener(this)
        binding.mypageTopbarLayout.topbarSubbuttonIb.setOnClickListener(this)
        binding.mypageHistoryButtonTv.setOnClickListener(this)
        binding.mypageHistoryButtonView.setOnClickListener(this)
    }

    private fun initTripHistoryView() {

        val tripList = ArrayList<Trip>()

        // 더미 데이터
        tripList.add(Trip(0, "0", 1, "테스트여행제목1", "2022-08-04", "2022-08-06", "ACTIVE"))
        tripList.add(Trip(1, "1", 1, "테스트여행제목2", "2022-08-04", "2022-08-06", "ACTIVE"))
        tripList.add(Trip(2, "2", 1, "테스트여행제목3", "2022-08-04", "2022-08-06", "ACTIVE"))
        tripList.add(Trip(3, "3", 1, "테스트여행제목4", "2022-08-04", "2022-08-06", "ACTIVE"))

        val tripHistoryRVAdapter = TripHistoryRVAdapter(tripList)

        binding.mypageHistoryRecyclerview.adapter = tripHistoryRVAdapter
        binding.mypageHistoryRecyclerview.layoutManager = LinearLayoutManager(this)

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

    override fun getTripCountSuccess(result: Int) {
        binding.mypageHistoryCount01Tv.text = result.toString()
    }

    override fun getTripCountFailure(code: Int, message: String) {
        Log.e("MainActivity.kt", "getTripCountFailure() status code $code")
        when(code) {
            400 -> Toast.makeText(this, "네트워크 상태를 확인해주세요.", Toast.LENGTH_SHORT).show()
            // Todo(1500, 1504, 1507, 1509 -> userService.autoSignin())
            2105 -> Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

}