package com.olympos.tripbook.src.trip.view

import com.olympos.tripbook.src.trip.model.Trip

interface GetAllTripsView {
    fun onGetAllTripsSuccess(result: ArrayList<Trip>)
    fun onGetAllTripsFailure(code: Int, message: String)
}