package com.olympos.tripbook.src.user

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.olympos.tripbook.R
import com.olympos.tripbook.config.BaseActivity
import com.olympos.tripbook.databinding.ActivityUserMyPastTripBinding
import com.olympos.tripbook.src.home.MainActivity
import com.olympos.tripbook.src.trip.GetAllTripsView
import com.olympos.tripbook.src.trip.TripApiController
import com.olympos.tripbook.src.trip.model.Trip
import com.olympos.tripbook.src.user.view.MyPastTripRVAdapter

class MyPastTripActivity : BaseActivity(), GetAllTripsView {

    private val tripApiController = TripApiController()

    private lateinit var binding: ActivityUserMyPastTripBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityUserMyPastTripBinding.inflate(layoutInflater)

        setContentView(binding.root)

        tripApiController.setGetAllTripsView(this)

        initView()
    }

    override fun onResume() {
        super.onResume()
        tripApiController.getAllTrips()
    }

    private fun initView() {

        binding.mypasttripTopbarLayout.topbarTitleTv.text = "나의 지난 여행"
        binding.mypasttripTopbarLayout.topbarSubtitleTv.text = ""

        binding.mypasttripTopbarLayout.topbarBackIb.setOnClickListener(this)
        binding.mypasttripTopbarLayout.topbarSubbuttonIb.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.topbar_back_ib -> finish()
            R.id.topbar_subbutton_ib -> startMainActivity()
        }
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onGetAllTripsLoading() {
        // TODO("Not yet implemented")
    }

    override fun onGetAllTripsSuccess(result: ArrayList<Trip>) {

        val myPastTripRVAdapter = MyPastTripRVAdapter(result)

        binding.mypasttripRecyclerview.adapter = myPastTripRVAdapter
        binding.mypasttripRecyclerview.layoutManager = StaggeredGridLayoutManager(2, 1)

    }

    override fun onGetAllTripsFailure(code: Int, message: String) {
        Log.e("MyPageActivity", "onGetTripCountFailure() status code $code")
    }
}