package com.example.pencatatanuser.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.pencatatanuser.Api.ApiRetrofit
import com.example.pencatatanuser.R
import com.example.pencatatanuser.Model.SubmitModel
import com.example.pencatatanuser.Adapter.UserAdapter
import com.example.pencatatanuser.Model.UserModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val api by lazy { ApiRetrofit().endpoint}
    private lateinit var userAdapter: UserAdapter
    private lateinit var listUser: RecyclerView
    private lateinit var fabcreate: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupView()
        setupList()
//        tambah aksi
        setupListener()
    }

    override fun onStart() {
        super.onStart()
        getUser()
    }

    private fun setupView() {
//        Ambil id dari activity_main.xml
        listUser = findViewById(R.id.list_user)
        fabcreate = findViewById(R.id.fab_create)
    }

    private fun setupList(){
        userAdapter = UserAdapter(arrayListOf(), object : UserAdapter.OnAdapterListener {
            override fun onUpdate(username: UserModel.Data) {
                startActivity(
//                    Kirim keseluruhan data dalam package username
                    Intent(this@MainActivity, EditActivity::class.java)
                        .putExtra("username", username))
            }

            override fun onDelete(username: UserModel.Data) {
                api.delete(username.idpengguna!!)
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
                                getUser()
                            }
                        }

                        override fun onFailure(call: Call<SubmitModel>, t: Throwable) {
                            TODO("Not yet implemented")
                        }

                    })
            }

        })
        listUser.adapter = userAdapter
    }

    private fun setupListener() {
//        Ambil id dari activity_main.xml
        fabcreate.setOnClickListener{
            startActivity(Intent(this, CreateActivity::class.java))
        }
    }
    private fun getUser(){
        api.user().enqueue(object : Callback<UserModel>{
            override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
                if (response.isSuccessful){
                    val listData = response.body()!!.data
                    userAdapter.setData(listData)
//                    listData.forEach{
//                        Log.e("MainActivity", "data ${it.username}")
//                    }
                }

            }

            override fun onFailure(call: Call<UserModel>, t: Throwable) {
                Log.e("MainActivity", t.toString())
            }

        })
    }
}