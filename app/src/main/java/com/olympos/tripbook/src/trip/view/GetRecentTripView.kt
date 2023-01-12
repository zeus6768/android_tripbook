package com.olympos.tripbook.src.trip.view

import com.olympos.tripbook.src.trip.model.Trip

interface GetRecentTripView {
    fun onGetRecentTripSuccess(result: Trip)
    fun onGetRecentTripFailure(code: Int, message: String)
}