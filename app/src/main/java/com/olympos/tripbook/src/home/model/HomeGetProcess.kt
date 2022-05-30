package com.olympos.tripbook.src.home.model

interface HomeGetProcess {
    fun getTripCountLoading()
    fun getTripCountSuccess(result: Int)
    fun getTripCountFailure(code: Int, message: String)
}