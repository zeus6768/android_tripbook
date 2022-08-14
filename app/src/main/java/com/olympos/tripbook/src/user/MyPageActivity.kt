package com.olympos.tripbook.src.user

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.olympos.tripbook.R
import com.olympos.tripbook.config.BaseActivity
import com.olympos.tripbook.databinding.ActivityUserMyPageBinding
import com.olympos.tripbook.src.home.MainActivity
import com.olympos.tripbook.src.trip.GetAllTripsView
import com.olympos.tripbook.src.trip.GetTripCountView
import com.olympos.tripbook.src.trip.TripApiController
import com.olympos.tripbook.src.trip.model.Trip
import com.olympos.tripbook.src.user.view.MyPageRVAdapter
import com.olympos.tripbook.utils.getNickname
import com.olympos.tripbook.utils.getUserImage

class MyPageActivity : BaseActivity(), GetTripCountView, GetAllTripsView {

    private val tripApiController = TripApiController()

    private lateinit var binding: ActivityUserMyPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityUserMyPageBinding.inflate(layoutInflater)

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

        Glide.with(this)
            .load(getUserImage())
            .circleCrop()
            .placeholder(R.drawable.img_home_profile)
            .error(R.drawable.img_home_profile)
            .into(binding.mypageProfileIv)

        binding.mypageTopbarLayout.topbarTitleTv.text = "마이페이지"
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
            R.id.topbar_back_ib -> finish()
            R.id.topbar_subbutton_ib,
            R.id.mypage_history_button_tv,
            R.id.mypage_history_button_view -> startMainActivity()
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

        Log.e("MyPageActivity", "onGetTripCountFailure() status code $code")
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

        val myPageRVAdapter = MyPageRVAdapter(result)

        binding.mypageHistoryRecyclerview.adapter = myPageRVAdapter
        binding.mypageHistoryRecyclerview.layoutManager = LinearLayoutManager(this)

    }

    override fun onGetAllTripsFailure(code: Int, message: String) {
        Log.e("MyPageActivity", "onGetTripCountFailure() status code $code")
    }

}