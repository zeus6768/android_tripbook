package com.olympos.tripbook.src.trip.view

import com.olympos.tripbook.src.trip.model.Trip

interface GetTripView {
    fun onGetTripLoading()
    fun onGetTripSuccess(result: Trip)
    fun onGetTripFailure(code: Int, message: String)
}