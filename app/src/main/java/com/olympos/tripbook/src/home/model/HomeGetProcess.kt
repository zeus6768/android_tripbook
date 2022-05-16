package com.olympos.tripbook.src.home.model

import com.olympos.tripbook.src.tripcourse.model.Card

interface HomeGetProcess {
    fun getTripCountLoading()
    fun getTripCountSuccess(result: Int)
    fun getTripCountFailure(code: Int, message: String)
}