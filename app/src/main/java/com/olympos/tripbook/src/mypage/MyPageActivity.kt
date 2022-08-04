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
import com.olympos.tripbook.src.trip.GetTripCountView
import com.olympos.tripbook.src.trip.GetAllTripsView
import com.olympos.tripbook.src.trip.TripApiController
import com.olympos.tripbook.src.trip.model.Trip
import com.olympos.tripbook.utils.getNickname
import java.util.*
import kotlin.collections.ArrayList

class MyPageActivity : BaseActivity(), GetTripCountView, GetAllTripsView {

    private val tripApiController = TripApiController()

    private lateinit var binding: ActivityUserMypageBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityUserMypageBinding.inflate(layoutInflater)

        setContentView(binding.root)

        tripApiController.setGetTripCountView(this)
        tripApiController.setGetAllTripsView(this)

        initView()

    }

    override fun onResume() {

        super.onResume()

        tripApiController.getTripCount()
        tripApiController.getAllTrips()

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

    override fun onGetTripCountSuccess(result: Int) {
        binding.mypageHistoryCount01Tv.text = result.toString()
    }

    override fun onGetTripCountFailure(code: Int, message: String) {

        Log.e("MyPageActivity.kt", "onGetTripCountFailure() status code $code")
        when(code) {
            400 -> Toast.makeText(this, "네트워크 상태를 확인해주세요.", Toast.LENGTH_SHORT).show()
            // Todo(1500, 1504, 1507, 1509 -> userService.autoSignin())
            2105 -> Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

    }

    override fun onGetAllTripsLoading() {
        // TODO("Not yet implemented")
    }

    override fun onGetAllTripsSuccess(result: ArrayList<Trip>) {

        result.reverse()

        val tripHistoryRVAdapter = TripHistoryRVAdapter(result)

        binding.mypageHistoryRecyclerview.adapter = tripHistoryRVAdapter
        binding.mypageHistoryRecyclerview.layoutManager = LinearLayoutManager(this)

    }

    override fun onGetAllTripsFailure(code: Int, message: String) {
        Log.e("MyPageActivity.kt", "onGetTripCountFailure() status code $code")
    }

}