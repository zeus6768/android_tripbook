package com.olympos.tripbook.src.tripcourse_view.model

import com.olympos.tripbook.src.tripcourse.model.Card

interface TripResponseView {
    fun onGetCardsLoading()
    fun onGetCardsSuccess(cards : ArrayList<Card>)
    fun onGetCardsFailure(code : Int, message : String)
}