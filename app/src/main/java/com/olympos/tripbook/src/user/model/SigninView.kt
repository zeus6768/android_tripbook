package com.olympos.tripbook.src.user.model

interface SigninView {
    fun signUpUserSuccess()
    fun signUpUserFailure(code: Int)

    fun signUpProfileSuccess()
    fun signUpProfileFailure(code: Int)

    fun kakaoSigninSuccess()
    fun kakaoSigninFailure(code: Int)

    fun updateKakaoAccessTokenSuccess()
    fun updateKakaoAccessTokenFailure(code: Int)

    fun updateProfileSuccess()
    fun updateProfileFailure(code: Int)
}