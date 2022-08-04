package com.olympos.tripbook.src.trip

interface TripCountView {
    fun getTripCountSuccess(result: Int)
    fun getTripCountFailure(code: Int, message: String)
}