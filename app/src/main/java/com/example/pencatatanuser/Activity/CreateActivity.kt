package com.example.pencatatanuser.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.Toast
import com.example.pencatatanuser.Api.ApiRetrofit
import com.example.pencatatanuser.R
import com.example.pencatatanuser.Model.SubmitModel
import com.example.pencatatanuser.Model.UserModel
import com.example.pencatatanuser.Activity.ScanQRCodeActivity
import com.google.android.material.button.MaterialButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateActivity : AppCompatActivity() {

    private lateinit var createUsername: AutoCompleteTextView
    private lateinit var createPassword:  EditText
    private lateinit var buttonCreate: MaterialButton
    private lateinit var buttonCreateQR: MaterialButton

    private val api by lazy { ApiRetrofit().endpoint}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)
        setupView()
        setupListener()

        getUser()
    }

    private fun setupView(){
        createUsername = findViewById(R.id.create_username)
        createPassword = findViewById(R.id.create_password)
        buttonCreate = findViewById(R.id.button_create)
        buttonCreateQR = findViewById(R.id.button_createqr)
    }

    private fun setupListener(){
        buttonCreateQR.setOnClickListener {
            startActivity(Intent(this, ScanQRCodeActivity::class.java))
        }

        buttonCreate.setOnClickListener{
//            Cek username dan password
            if (createUsername.text.toString().isNotEmpty() &&
                createPassword.text.toString().isNotEmpty()){
                Log.e("CreateActivity", createUsername.text.toString())
                Log.e("CreateActivity", createPassword.text.toString())

                api.create(createUsername.text.toString(), createPassword.text.toString())
                    .enqueue(object : Callback<SubmitModel>{
                        override fun onResponse(
                            call: Call<SubmitModel>,
                            response: Response<SubmitModel>
                        ) {
                            if (response.isSuccessful){
                                val submit = response.body()
                                Toast.makeText(
                                    applicationContext,
                                    submit!!.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                                finish()
                            }
                        }

                        override fun onFailure(call: Call<SubmitModel>, t: Throwable) {
                            TODO("Not yet implemented")
                        }

                    })
            }
            else{
                Toast.makeText(applicationContext, "Username / Password harus diisi!!!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getUser(){
        api.user().enqueue(object : Callback<UserModel>{
            override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
                if (response.isSuccessful){
                    val listData = response.body()!!.data

//                    listData.forEach{
//                        Log.e("CreateActivity", "data ${it.username}")
//                    }
//                    Menampilkan auto suggest
                    var suggestion= listData.map { it.username }

                    var adapter = ArrayAdapter(this@CreateActivity, android.R.layout.simple_list_item_1, suggestion)
                    createUsername.threshold=0
                    createUsername.setAdapter(adapter)

                }

            }

            override fun onFailure(call: Call<UserModel>, t: Throwable) {
                Log.e("CreateActivity", t.toString())
            }

        })
    }
}