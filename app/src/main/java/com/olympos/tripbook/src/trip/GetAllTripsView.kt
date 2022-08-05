package com.olympos.tripbook.src.trip

import com.olympos.tripbook.src.trip.model.Trip

interface GetAllTripsView {
    fun onGetAllTripsLoading()
    fun onGetAllTripsSuccess(result: ArrayList<Trip>)
    fun onGetAllTripsFailure(code: Int, message: String)
}