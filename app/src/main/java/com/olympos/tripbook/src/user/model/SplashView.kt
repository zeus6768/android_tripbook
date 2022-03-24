package com.olympos.tripbook.src.user.model

interface SplashView {
    fun autoSigninSuccess()
    fun autoSigninFailure(code: Int)

    fun updateAccessTokenSuccess()
    fun updateAccessTokenFailure(code: Int)
}