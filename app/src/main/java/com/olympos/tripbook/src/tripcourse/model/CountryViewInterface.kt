package com.olympos.tripbook.src.tripcourse.model

interface CountryView {
    fun onGetCountryLoading()
    fun onGetCountrySuccess(countrys : ArrayList<Country>)
    fun onGetCountryFailure(code : Int, message : String)
}