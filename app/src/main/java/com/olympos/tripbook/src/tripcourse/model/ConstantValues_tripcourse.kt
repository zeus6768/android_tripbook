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

const val TITLE_CHANGED = 101
const val BODY_CHANGED = 102
const val IMG_CHANGED = 103
const val DATE_CHANGED = 104


