package com.olympos.tripbook.src.tripcourse.model

import retrofit2.Call
import retrofit2.http.*

interface CardRetrofitInterface {
    @POST("/app/course")
    fun postCard(@Body card : Card): Call<PostCardResponse>
    //Gson 객체로 바꿔서 서버로 보내는 어노테이션 : @Body

//    @GET("/app/course/:courseIdx")
//    fun getCard() : Call<GetCardResponse>

    @PATCH("/app/course/deleteCourse/{userIdx}/{courseIdx}")
    fun deleteCard(@Path("userIdx") userIdx : String, @Path("courseIdx") courseIdx :String) : Call<ServerDefaultResponse>

    @GET("/app/trip/:tripIdx/courses")
    fun getTripcourses(tripIdx: String): Call<GetTripcourseResponse>

    @PATCH("/app/course/courseDate/{userIdx}/{courseIdx}")
    fun patchDate(@Path("userIdx") userIdx : String, @Path("courseIdx") courseIdx :String, @Body courseDate : String) : Call<ServerDefaultResponse>

    @PATCH("/app/course/courseDate/{userIdx}/{courseIdx}")
    fun patchTime(@Path("userIdx") userIdx : String, @Path("courseIdx") courseIdx : String, @Body courseTime : String) : Call<ServerDefaultResponse>

    @PATCH("/app/course/courseDate/{userIdx}/{courseIdx}")
    fun patchTitle(@Path("userIdx") userIdx : String, @Path("courseIdx") courseIdx:String, @Body courseTitle : String) : Call<ServerDefaultResponse>

    @PATCH("/app/course/courseDate/:userIdx/:courseIdx")
    fun patchImg(@Path("userIdx") userIdx : String, @Path("courseIdx") courseIdx:String, @Body courseImg : String) : Call<ServerDefaultResponse>

    @PATCH("/app/course/courseDate/:userIdx/:courseIdx")
    fun patchBody(@Path("userIdx") userIdx : String, @Path("courseIdx") courseIdx:String, @Body courseComment : String) : Call<ServerDefaultResponse>
}