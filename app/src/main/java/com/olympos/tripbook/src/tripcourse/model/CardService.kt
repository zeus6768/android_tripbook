package com.olympos.tripbook.src.tripcourse.model

import android.util.Log
import com.olympos.tripbook.src.user.model.ApiTestResponse
import com.olympos.tripbook.src.user.model.ApiTestRetrofitInterface
import com.olympos.tripbook.utils.ApplicationClass.Companion.retrofit
import com.olympos.tripbook.utils.ApplicationClass.Companion.TAG
import com.olympos.tripbook.utils.saveNickname
import retrofit2.*

class CardService{

    private lateinit var cardsView : CardsView

    fun setCardsView(cardsView : CardsView) {
        this.cardsView = cardsView
    }

    //작성 완료한 카드 서버로 전송
    fun postCard(card : Card) {
        Log.d("CheckPoint : ", "CardService-postCard Activated")
        val cardRetrofitService = retrofit.create(CardRetrofitInterface::class.java)
        cardRetrofitService.postCard(card).enqueue(object : Callback<PostCardResponse> {
            override fun onResponse(call: Call<PostCardResponse>, response: Response<PostCardResponse>) {
                if (response.isSuccessful) {
                    val res = response.body()!!
                    Log.d("__res", response.body()!!.toString())
                    when (res.code) {
                        1000 -> Log.d("CardService-postCard", res.code.toString() + " : " + res.message) //성공
                        else -> Log.d("CardService-postCard", res.code.toString() + " : " + res.message) //의도된 실패
                    }
                }
            }
            override fun onFailure(call: Call<PostCardResponse>, t: Throwable) {
                //통신 실패 -> 에러 건네줌
                Log.d("CardService-postCard", t.toString()) //네트워크 실패
            }
        })
    }

    //여행 가져오기
    fun getTrip(){
        val cardRetrofitService = retrofit.create(CardRetrofitInterface::class.java)

        cardsView.onGetCardsLoading()

        cardRetrofitService.getTripcourses().enqueue(object : Callback<GetTripcourseResponse> {
            override fun onResponse(call: Call<GetTripcourseResponse>, response: Response<GetTripcourseResponse>) {
                Log.d("들어오는지 확인", "CardService-getTrip-onResponse")
                if (response.isSuccessful) {
                    val res = response.body()!!
                    Log.d("__res", response.body()!!.toString())
                    when (res.code) {
                        1000 -> {
                            Log.d("REST API TEST 성공", res.toString())
                            cardsView.onGetCardsSuccess(res.result)
                        }

                        else -> {
                            Log.d("통신 실패 : ", res.toString())
                            cardsView.onGetCardsFailure(res.code, res.message)
                        }
                    }
                }
            }
            override fun onFailure(call: Call<GetTripcourseResponse>, t: Throwable) {
                Log.d("들어오는지 확인", "CardService-getTrip-onFailure")
                cardsView.onGetCardsFailure(400, t.message.toString())
            }
        })
    }

    fun getCard(){
        val cardRetrofitService = retrofit.create(CardRetrofitInterface::class.java)

        cardRetrofitService.getCard().enqueue(object : Callback<GetCardResponse> {
            override fun onResponse(call: Call<GetCardResponse>, response: Response<GetCardResponse>) {
                Log.d("들어오는지 확인", "CardService-postCard-onResponse")
                if (response.isSuccessful) {
                    val res = response.body()!!
                    Log.d("__res", response.body()!!.toString())
                    when (res.code) {
                        1000 -> Log.d("REST API TEST 성공", res.toString())
                        else -> Log.d("통신 실패 : ", res.toString())
                    }
                }
            }
            override fun onFailure(call: Call<GetCardResponse>, t: Throwable) {
                Log.d("들어오는지 확인", "CardService-postCard-onFailure")
            }
        })
    }
}
