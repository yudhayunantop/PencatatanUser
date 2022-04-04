package com.example.pencatatanuser.Api

import com.example.pencatatanuser.Model.SubmitModel
import com.example.pencatatanuser.Model.UserModel
import retrofit2.Call
import retrofit2.http.*

interface ApiEndPoint {

    @GET("restapi/user/")
    fun user() : Call<UserModel>

    @FormUrlEncoded
    @POST("restapi/user/")
    fun create(
        @Field("username") username: String,
        @Field("password") password: String
    ) : Call<SubmitModel>

    @FormUrlEncoded
    @POST("restapi/user/{idpengguna}")
    fun update(
        @Path("idpengguna") idpengguna: Int,
        @Field("username") username: String,
        @Field("password") password: String
    ) : Call<SubmitModel>

    @DELETE("restapi/user/{idpengguna}")
    fun delete(
        @Path("idpengguna") idpengguna: Int
    ) : Call<SubmitModel>
}