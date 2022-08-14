package com.olympos.tripbook.src.user.view

interface UserAuthView {
    fun autoSignInSuccess()
    fun autoSignInFailure(code: Int)

    fun signUpUserSuccess()
    fun signUpUserFailure(code: Int)

    fun signUpProfileSuccess()
    fun signUpProfileFailure(code: Int)

    fun kakaoSignInSuccess()
    fun kakaoSignInFailure(code: Int)

    fun updateKakaoAccessTokenSuccess()
    fun updateKakaoAccessTokenFailure(code: Int)

    fun updateProfileSuccess()
    fun updateProfileFailure(code: Int)

    fun getProfileSuccess()
    fun getProfileFailure(code: Int)

    fun updateAccessTokenSuccess()
    fun updateAccessTokenFailure(code: Int)
}