package com.olympos.tripbook.src.trip.view

interface GetTripCountView {
    fun onGetTripCountSuccess(result: Int)
    fun onGetTripCountFailure(code: Int, message: String)
}