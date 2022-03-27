package com.example.pencatatanuser.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import com.example.pencatatanuser.Api.ApiRetrofit
import com.example.pencatatanuser.R
import com.example.pencatatanuser.Model.UserModel
import com.google.android.material.button.MaterialButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private val api by lazy { ApiRetrofit().endpoint}

    private lateinit var inputUsername: EditText
    private lateinit var inputPassword: EditText
    private lateinit var buttonLogin: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setupView()
        setupListener()
    }

    private fun setupView(){
        inputUsername = findViewById(R.id.input_username)
        inputPassword = findViewById(R.id.input_password)
        buttonLogin = findViewById(R.id.button_login)
    }

    private fun setupListener(){
        buttonLogin.setOnClickListener{
            getUser()
        }
    }
    private fun getUser(){
        api.user().enqueue(object : Callback<UserModel> {
            override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
                if (response.isSuccessful){
                    val listData = response.body()!!.data
                    listData.forEach{
                        Log.e("LoginActivity", "data ${it.username}")
                        if (inputUsername.text.toString() == it.username &&
                            inputPassword.text.toString() == it.password){
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        }
                    }
                }

            }

            override fun onFailure(call: Call<UserModel>, t: Throwable) {
                Log.e("LoginActivity", t.toString())
            }

        })
    }
}