package com.olympos.tripbook.src.trip

interface PostTripView {
    fun onPostTripLoading()
    fun onPostTripSuccess(result: Int)
    fun onPostTripFailure(code: Int, message: String)
}