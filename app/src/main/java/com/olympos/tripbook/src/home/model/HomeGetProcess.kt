package com.olympos.tripbook.src.home.model

interface HomeGetProcess {
    fun onGetHomeLoading()
    fun onGetHomeSuccess(result: Int)
    fun onGetHomeFailure(code: Int, message: String)
}