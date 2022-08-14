package com.olympos.tripbook.src.trip.view

interface PostTripView {
    fun onPostTripLoading()
    fun onPostTripSuccess(result: Int)
    fun onPostTripFailure(code: Int, message: String)
}