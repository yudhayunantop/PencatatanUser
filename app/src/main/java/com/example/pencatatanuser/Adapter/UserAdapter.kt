package com.example.pencatatanuser.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pencatatanuser.Model.UserModel
import com.example.pencatatanuser.R

class UserAdapter (
    val users:ArrayList<UserModel.Data>,
    val listener: OnAdapterListener
    ): RecyclerView.Adapter<UserAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_user, parent, false)
    )

    override fun getItemCount()= users.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        Menampilkan data username dan password pada list
        val data = users[position]
        holder.textUsername.text = data.username
        holder.textPassword.text = data.password

//        Memberi aksi saat itemView pada list diklik
        holder.itemView.setOnClickListener {
            listener.onUpdate(data)
        }

        holder.imageDelete.setOnClickListener{
            listener.onDelete(data)
        }
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val textUsername =view.findViewById<TextView>(R.id.text_username)
        val textPassword =view.findViewById<TextView>(R.id.text_password)
        val imageDelete =view.findViewById<ImageView>(R.id.image_delete)
    }

    public fun setData(data: List<UserModel.Data>){
        users.clear()
        users.addAll(data)
        notifyDataSetChanged()
    }

    interface OnAdapterListener{
        fun onUpdate(username: UserModel.Data)
        fun onDelete(username: UserModel.Data)
    }
}