package com.olympos.tripbook.src.tripcourse

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
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

    var searchList : List<Address>? = null

//    private lateinit var fusedLocationClient : FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTripcourseSelectCountryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        geocoder = Geocoder(this)

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
                val result = searchList?.get(0)
                Log.d("SELECT_COUNTRY_RESULT", result.toString())

                val intent = Intent(this@TripcourseSelectCountryActivity, TripcourseRecordActivity::class.java)
                intent.putExtra("country_result", result)
                setResult(COUNTRY_ACTIVITY_CODE, intent)
                finish()
            }
            R.id.topbar_back_ib -> { //뒤로가기 버튼
                showDialog("도시 선택 취소", "도시 선택을 취소하시겠습니까?\n"
                        + "검색중인 정보는 저장되지 않습니다.", "취소하기")
            }
            R.id.tripcourse_select_country_search_btn -> {
                Log.d("ButtonClicked", "tripcourse_select_country_search_btn")
                val inputString : String = binding.tripcourseSelectCountrySearchEt.text.toString()

                try { // search view에 입력한 텍스트(주소, 지역, 장소 등)을 지오 코딩을 이용해 변환
                    searchList = geocoder.getFromLocationName(inputString, 3) //str = 주소, 10 = 최대 검색 결과 개수
                } catch (e: IOException) {
                    e.printStackTrace()
                    Log.d("Geocoder Err", "onComplete : 주소 변환 실패")
                }

                Log.d("Geocoder Result", searchList.toString())

                if(searchList?.size == 0) {
                    Toast.makeText(this, "입력한 지역이 없습니다.", Toast.LENGTH_SHORT).show()
                }
                else {
                    Log.d("First Search Address", searchList!![0].toString())

                    val point = LatLng(searchList?.get(0)!!.latitude, searchList?.get(0)!!.longitude)

                    val resultMarker = MarkerOptions()

                    resultMarker.title(searchList?.get(0)?.countryName)
                    resultMarker.position(point)

                    // 마커 추가
                    mMap.addMarker(resultMarker)

                    // 해당 좌표로 화면 줌
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 15f))
                }
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        try { // search view에 입력한 텍스트(주소, 지역, 장소 등)을 지오 코딩을 이용해 변환
            searchList = geocoder.getFromLocationName("서울", 3) //str = 주소, 10 = 최대 검색 결과 개수
        } catch (e: IOException) {
            e.printStackTrace()
            Log.d("Geocoder Err", "onComplete : 주소 변환 실패")
        }

        val seoul = LatLng(searchList?.get(0)!!.latitude, searchList?.get(0)!!.longitude)

        mMap.addMarker(
            MarkerOptions()
                .position(seoul)
                .title("서울")
        )
        mMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                seoul,
                16F
            )
        ) //넓게 보고 싶으면 숫자를 내리고, 올리고 싶다면 숫자를 높이면 됨

        // 맵 터치 이벤트 구현 //
//        mMap.setOnMapClickListener { point ->
//            val mOptions = MarkerOptions()
//            mOptions.title("마커 좌표")      // 마커 타이틀
//            val latitude = point.latitude       // 위도
//            val longitude = point.longitude     // 경도
//            mOptions.snippet("$latitude, $longitude")   // 마커의 스니펫(간단한 텍스트) 설정
//            mOptions.position(LatLng(latitude, longitude))     // LatLng: 위도 경도 쌍을 나타냄
//            googleMap.addMarker(mOptions)       // 마커(핀) 추가
//        }
    }
}