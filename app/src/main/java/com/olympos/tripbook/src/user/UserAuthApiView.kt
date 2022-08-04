package com.olympos.tripbook.src.user

interface UserAuthApiView {
    fun autoSigninSuccess()
    fun autoSigninFailure(code: Int)

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

    fun getProfileSuccess()
    fun getProfileFailure(code: Int)

    fun updateAccessTokenSuccess()
    fun updateAccessTokenFailure(code: Int)
}