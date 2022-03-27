package com.example.pencatatanuser.Model

import java.io.Serializable

data class UserModel (
    val status: String?,
    val message: String?,
    val data: List<Data>
    ){
    data class Data (
        val username: String?,
        val password:String?,
        val idpengguna:Int?) : Serializable
}