package com.olympos.tripbook.src.home.model

import com.olympos.tripbook.src.tripcourse.model.Card

interface HomeGetProcess {
    fun onGetHomeLoading()
    fun onGetHomeSuccess(result: Int)
    fun onGetHomeFailure(code: Int, message: String)
}

interface CardsView {
    fun onGetCardsLoading()
    fun onGetCardsSuccess(cards : ArrayList<Card>)
    fun onGetCardsFailure(code : Int, message : String)
}