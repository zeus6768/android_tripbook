package com.olympos.tripbook.src.user.model

interface UserView {
    fun autoSigninSuccess()
    fun autoSigninFailure(code: Int)

    fun updateAccessTokenSuccess()
    fun updateAccessTokenFailure(code: Int)

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