package com.olympos.tripbook.src.tripcourse

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.olympos.tripbook.R
import com.olympos.tripbook.config.BaseActivity
import com.olympos.tripbook.databinding.ActivityTripcourseSelectCountryBinding

class TripcourseSelectCountryActivity : BaseActivity(), OnMapReadyCallback {

    lateinit var binding : ActivityTripcourseSelectCountryBinding

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTripcourseSelectCountryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.tripcourse_select_country_map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        initView()
    }

    private fun initView() {
        //topbar layout view randering
        binding.tripcourseSelectCountryTopbarLayout.topbarTitleTv.setText(R.string.tripcourse_select_country_title)
        binding.tripcourseSelectCountryTopbarLayout.topbarSubtitleTv.visibility = View.GONE
        binding.tripcourseSelectCountryTopbarLayout.topbarSubbuttonIb.visibility = View.GONE

        //상단바 - 뒤로가기 버튼
        binding.tripcourseSelectCountryTopbarLayout.topbarBackIb.setOnClickListener {
            //선택을 취소하시겠습니까 다이어로그 뜨기
            val intent = Intent(this@TripcourseSelectCountryActivity, TripcourseRecordActivity::class.java)
            startActivity(intent)
        }

        //선택 완료 버튼 - TripcourseRecordActivity로 이동(with 선택 결과)
        binding.tripcourseSelectCountrySelectCompleteBtn.setOnClickListener {
            val intent = Intent(this@TripcourseSelectCountryActivity, TripcourseRecordActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(
            MarkerOptions()
            .position(sydney)
            .title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}