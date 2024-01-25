package com.example.sahils_app.Adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sahils_app.AllUserrr
import com.example.sahils_app.R
import com.example.sahils_app.Userr
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso



class AllUserAdapter(var context:Context) : RecyclerView.Adapter<AllUserAdapter.MyViewHolder2>() {

    private val userList = ArrayList<AllUserrr>()
    private var storageRef = Firebase.storage
    private lateinit var uri : Uri

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder2 {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.request_design,
            parent,false
        )
        return MyViewHolder2(itemView)

    }


    override fun onBindViewHolder(holder: MyViewHolder2, position: Int) {

        val currentitem = userList[position]
        holder.fName.text = currentitem.request_name
        Picasso.get().load(currentitem.request_url).into(holder.fImage)
///////////////////////////////////////////////////////////////////////


    }
    ////////////////////////////////////////////////////////////////////////
    override fun getItemCount(): Int {
        return userList.size
    }

    fun updateUserList(userListt: List<AllUserrr>){

        this.userList.clear()
        this.userList.addAll(userListt)
        notifyDataSetChanged()

    }

    class  MyViewHolder2(itemView : View) : RecyclerView.ViewHolder(itemView){

        var fName : TextView = itemView.findViewById(R.id.fName)
        var fImage : ImageView = itemView.findViewById(R.id.fImage)

    }

}