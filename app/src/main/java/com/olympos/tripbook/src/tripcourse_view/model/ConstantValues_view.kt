package com.olympos.tripbook.src.tripcourse_view.model

import com.olympos.tripbook.src.trip.model.Trip
import com.olympos.tripbook.src.tripcourse.model.Card

var recentTrip = Trip()
var recentTripCards = ArrayList<Card>()

var focusedCardPosition : Int = 0

val filledCards = ArrayList<Card>()

val serverTripCards = ArrayList<Card>()