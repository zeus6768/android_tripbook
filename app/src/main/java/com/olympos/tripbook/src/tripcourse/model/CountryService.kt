package com.olympos.tripbook.src.tripcourse.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CountryService {
    val GOOGLE_MAP_URL = "??"

    val retrofit = Retrofit.Builder()
        .baseUrl(GOOGLE_MAP_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val server: CountryRetrofitInterface = retrofit.create(CountryRetrofitInterface::class.java)
//
//    fun getRetrofit() : Retrofit{
//        val retrofit = Retrofit.Builder()
//            .baseUrl(DEVELOP_URL)
//            .addConverterFactory(GsonConverterFactory.create()) //데이터 객체화 해줌
//            .build()
//
//        return retrofit
//    }
//
//
//    //레트로 핏 인스턴스 생//맨뒤 요청의 슬래쉬는 RetrofitInterface 부분에 붙어있음!
//    val retrofit = Retrofit.Builder()
//        .baseUrl("http://13.125.121.202")
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()
//
//    //생성된 레트로핏 객채를 사용해서 서비스를 서비스 객체로 생성
//    val authService = retrofit.create(CountryRetrofitInterface::class.java)
//
//    fun signUp(country : Country) {
//        val retrofit = getRetrofit() //레트로 핏 객체 생성
//        val authService = retrofit
//            .create(CountryRetrofitInterface::class.java)
//
//        signUpView.onSignUpLoading() // 로딩중
//
//        authService.signUp(user).enqueue(object : Callback<AuthResponse> {
//            override fun onResponse() { 성공 처리 }
//            override fun onFailure() { 실패 처리 }
//        })
//    }
}