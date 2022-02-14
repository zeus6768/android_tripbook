package com.olympos.tripbook.src.tripcourse.model

interface CardViewInterface {
}

interface CardsView {
    fun onGetCardsLoading()
    fun onGetCardsSuccess(cards : ArrayList<Card>)
    fun onGetCardsFailure(code : Int, message : String)
}

interface PostCardView {
    fun onPostCardLoading()
    fun onPostCardSuccess(courseIdx : Int)
    fun onPostCardFailure(code : Int, message : String)
}

interface ServerView {
    fun onServerLoading()
    fun onServerSuccess()
    fun onServerFailure(code : Int, message : String)
}