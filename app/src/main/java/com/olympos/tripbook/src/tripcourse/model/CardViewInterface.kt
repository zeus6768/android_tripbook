package com.olympos.tripbook.src.tripcourse.model

interface CardViewInterface {
}

interface CardsView {
    fun onGetCardsLoading()
    fun onGetCardsSuccess(cards : ArrayList<CardResponse>)
    fun onGetCardsFailure(code : Int, message : String)
}