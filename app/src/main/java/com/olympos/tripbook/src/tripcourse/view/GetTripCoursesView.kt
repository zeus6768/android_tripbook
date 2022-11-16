package com.olympos.tripbook.src.tripcourse.view

import com.olympos.tripbook.src.tripcourse.model.TripCourse

interface GetTripCoursesView {
    fun onGetTripCoursesSuccess(result: ArrayList<TripCourse>)
    fun onGetTripCoursesFailure(code: Int, message: String)
}