package com.olympos.tripbook.src.tripcourse

import com.olympos.tripbook.src.tripcourse.model.Card

//Activity - start at 1
const val COUNTRY_ACTIVITY_CODE = 11
const val HASHTAG_ACTIVITY_CODE = 12

//Recycler View - card hasData value
const val FALSE = 0
const val TRUE = 1

val tripCards = ArrayList<Card>()

var focusedCardPosition : Int = 0