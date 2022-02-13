package com.olympos.tripbook.src.tripcourse

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.olympos.tripbook.R
import com.olympos.tripbook.config.BaseActivity
import com.olympos.tripbook.databinding.ActivityTripcourseSelectCountryBinding
import java.io.IOException


class TripcourseSelectCountryActivity : BaseActivity(), OnMapReadyCallback {

    lateinit var binding : ActivityTripcourseSelectCountryBinding

    private lateinit var mMap: GoogleMap
    private lateinit var geocoder : Geocoder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTripcourseSelectCountryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.tripcourse_select_country_map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        initView()
    }

    //뒤로가기 -> 다이어로그 -> 확인 -> 액티비티 종료
    override fun onOKClicked() {
        super.onOKClicked()
        finish()
    }

    private fun initView() {
        //topbar layout view randering
        binding.tripcourseSelectCountryTopbarLayout.topbarTitleTv.setText(R.string.tripcourse_select_country_title)
        binding.tripcourseSelectCountryTopbarLayout.topbarSubtitleTv.visibility = View.GONE
        binding.tripcourseSelectCountryTopbarLayout.topbarSubbuttonIb.visibility = View.GONE

        binding.tripcourseSelectCountryTopbarLayout.topbarBackIb.setOnClickListener(this)
        binding.tripcourseSelectCountrySelectCompleteBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        super.onClick(v)

        when(v!!.id) {
            R.id.tripcourse_select_country_select_complete_btn -> { //선택 완료 버튼 - 액티비티 종료(선택 결과 전달)

                //결과 문자열로 변환 필요

                val intent = Intent(this@TripcourseSelectCountryActivity, TripcourseRecordActivity::class.java)
                intent.putExtra("country_result", intent)
                setResult(COUNTRY_ACTIVITY_CODE, intent)
                finish()
            }
            R.id.topbar_back_ib -> { //뒤로가기 버튼
                showDialog("도시 선택 취소", "도시 선택을 취소하시겠습니까?\n"
                        + "검색중인 정보는 저장되지 않습니다.", "취소하기")
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val seoul = LatLng(-37.33, 126.58)
        mMap.addMarker(
            MarkerOptions()
            .position(seoul)
            .title("Seoul")
        )
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(seoul, 16F)) //넓게 보고 싶으면 숫자를 내리고, 올리고 싶다면 숫자를 높이면 됨


        // 맵 터치 이벤트 구현 //
        mMap.setOnMapClickListener {
            point ->
                val mOptions = MarkerOptions()
                mOptions.title("마커 좌표")      // 마커 타이틀
                val latitude = point.latitude       // 위도
                val longitude = point.longitude     // 경도
                mOptions.snippet("$latitude, $longitude")   // 마커의 스니펫(간단한 텍스트) 설정
                mOptions.position(LatLng(latitude, longitude))     // LatLng: 위도 경도 쌍을 나타냄
                googleMap.addMarker(mOptions)       // 마커(핀) 추가
        }

        //Search View 사용? : https://machine-woong.tistory.com/135 참고

        binding.tripcourseSelectCountrySearchBtn.setOnClickListener{
            val inputString : String = binding.tripcourseSelectCountrySearchEt.text.toString()

            var addressList: List<Address>? = null
            try { // search view에 입력한 텍스트(주소, 지역, 장소 등)을 지오 코딩을 이용해 변환
                addressList = geocoder.getFromLocationName(inputString, 10) //str = 주소, 10 = 최대 검색 결과 개수
            } catch (e: IOException) {
                e.printStackTrace()
            }
            Log.d("First Search Address", addressList!![0].toString())

            // 콤마를 기준으로 split
            val splitStr: List<String> = addressList!![0].toString().split(",")
            val address = splitStr[0].substring(splitStr[0].indexOf("\"") + 1, splitStr[0].length - 2) // 주소
            Log.d("Search Address", address)

            val latitude = splitStr[10].substring(splitStr[10].indexOf("=") + 1) // 위도
            val longitude = splitStr[12].substring(splitStr[12].indexOf("=") + 1) // 경도
            Log.d("Result of latitude", latitude)
            Log.d("Result of longitude", longitude)

            // 좌표(위도, 경도) 생성
            val point = LatLng(latitude.toDouble(), longitude.toDouble())

            // 마커 생성
            val mOptions2 = MarkerOptions()
            mOptions2.title("search result")
            mOptions2.snippet(address)
            mOptions2.position(point)

            // 마커 추가
            mMap.addMarker(mOptions2)

            // 해당 좌표로 화면 줌
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 15f))
        }
    }
}