package com.example.pencatatanuser.Activity

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
import com.google.android.material.button.MaterialButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditActivity : AppCompatActivity() {

    private lateinit var editUsername: AutoCompleteTextView
    private lateinit var editPassword: EditText
    private lateinit var buttonEdit: MaterialButton

    private val api by lazy { ApiRetrofit().endpoint}

//    Ambil package data username dari Intent
    private val username by lazy { intent.getSerializableExtra("username") as UserModel.Data }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        setupView()
        setupListener()

        getUser()
    }

    private fun setupView(){
        editUsername = findViewById(R.id.edit_username)
        editPassword = findViewById(R.id.edit_password)
        buttonEdit = findViewById(R.id.button_edit)

//      Set username password pada EditText
        editUsername.setText(username.username)
        editPassword.setText(username.password)
    }

    private fun setupListener(){
        buttonEdit.setOnClickListener{
            api.update(username.idpengguna!!, editUsername.text.toString(), editPassword.text.toString())
                .enqueue(object : Callback<SubmitModel> {
                    override fun onResponse(
                        call: Call<SubmitModel>,
                        response: Response<SubmitModel>
                    ) {
                        if (response.isSuccessful) {
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

                    var adapter = ArrayAdapter(this@EditActivity, android.R.layout.simple_list_item_1, suggestion)
                    editUsername.threshold=0
                    editUsername.setAdapter(adapter)

                }

            }

            override fun onFailure(call: Call<UserModel>, t: Throwable) {
                Log.e("CreateActivity", t.toString())
            }

        })
    }
    }
