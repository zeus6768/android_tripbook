package com.olympos.tripbook.src.tripcourse_view.model

import com.olympos.tripbook.src.trip.model.Trip
import com.olympos.tripbook.src.tripcourse.model.Card

interface TripResponseView {
    fun onGetCardsLoading()
    fun onGetCardsSuccess(cards : ArrayList<Card>)
    fun onGetCardsFailure(code : Int, message : String)
}

interface RecentTripResponseView {
    fun onGetRecentTripLoading()
    fun onGetRecentTripSuccess(trip : Trip)
    fun onGetRecentTripFailure(code : Int, message : String)
}

interface RecentTripCardsResponseView {
    fun onGetRecentTripCardsLoading()
    fun onGetRecentTripCardsSuccess(cards : ArrayList<Card>)
    fun onGetRecentTripCardsFailure(code : Int, message : String)
}