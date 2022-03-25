package com.olympos.tripbook.src.tripcourse.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

//@Parcelize
data class Country(
    val name : String,
    val fullAddress : String,
    val location : LocationEntity
)
//) : Parcelable

data class LocationEntity (
    val lan : Float,
    val lun : Float
)
