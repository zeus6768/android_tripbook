package com.olympos.tripbook.src.tripcourse.model

interface CardViewInterface {
}

interface CardsView {
    fun onGetCardsLoading()
    fun onGetCardsSuccess(cards : ArrayList<Card>)
    fun onGetCardsFailure(code : Int, message : String)
}