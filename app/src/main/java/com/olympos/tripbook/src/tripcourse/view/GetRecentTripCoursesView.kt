package com.olympos.tripbook.src.tripcourse.view

import com.olympos.tripbook.src.tripcourse.model.TripCourse

interface GetRecentTripCoursesView {
    fun onGetRecentTripCoursesSuccess(result: ArrayList<TripCourse>)
    fun onGetRecentTripCoursesFailure(code: Int, message: String)
}