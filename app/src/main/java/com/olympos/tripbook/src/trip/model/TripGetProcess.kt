package com.olympos.tripbook.src.trip.model

interface TripGetProcess {
    fun onGetTripLoading()
    fun onGetTripSuccess(result: Trip)
    fun onGetTripFailure(code: Int, message: String)
}