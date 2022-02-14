package com.olympos.tripbook.src.tripcourse.model

import android.util.Log
import com.olympos.tripbook.src.user.model.ApiTestResponse
import com.olympos.tripbook.src.user.model.ApiTestRetrofitInterface
import com.olympos.tripbook.utils.ApplicationClass.Companion.retrofit
import com.olympos.tripbook.utils.ApplicationClass.Companion.TAG
import retrofit2.*

class CardService{

    private lateinit var cardsView : CardsView
    private lateinit var serverView: ServerView

    fun setCardsView(cardsView : CardsView) {
        this.cardsView = cardsView
    }

    fun setServerView(serverView: ServerView) {
        this.serverView = serverView
    }

    //작성 완료한 카드 서버로 전송
    fun postCard(card : Card) {
        Log.d("CheckPoint : ", "CardService-postCard Activated")
        serverView.onServerLoading()

        val cardRetrofitService = retrofit.create(CardRetrofitInterface::class.java)
        cardRetrofitService.postCard(card).enqueue(object : Callback<PostCardResponse> {
            override fun onResponse(call: Call<PostCardResponse>, response: Response<PostCardResponse>) {
                if (response.isSuccessful) {
                    val res = response.body()!!
                    Log.d("__res", response.body()!!.toString())
                    when (res.code) {
                        1000 -> { //성공
                            Log.d("CardService-postCard", res.code.toString() + " : " + res.message)
                            serverView.onServerSuccess()
                        }
                        else -> { //의도된 실패
                            Log.d("CardService-postCard", res.code.toString() + " : " + res.message)
                            serverView.onServerFailure(res.code, res.message)
                        }
                    }
                }
            }
            override fun onFailure(call: Call<PostCardResponse>, t: Throwable) {
                serverView.onServerFailure(400, t.message.toString())
                Log.d("CardService-postCard", t.toString()) //네트워크 실패
            }
        })
    }

    //여행 가져오기
    fun getTrip(tripIdx : Int){
        val cardRetrofitService = retrofit.create(CardRetrofitInterface::class.java)

        cardsView.onGetCardsLoading()

        cardRetrofitService.getTrip(tripIdx).enqueue(object : Callback<GetTripResponse> {
            override fun onResponse(call: Call<GetTripResponse>, response: Response<GetTripResponse>) {
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
            override fun onFailure(call: Call<GetTripResponse>, t: Throwable) {
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
