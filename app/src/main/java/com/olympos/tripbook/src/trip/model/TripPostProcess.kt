package com.olympos.tripbook.src.trip.model

interface TripPostProcess {
    fun onPostTripLoading()
    fun onPostTripSuccess(result: Int)
    fun onPostTripFailure(code: Int, message: String)
}