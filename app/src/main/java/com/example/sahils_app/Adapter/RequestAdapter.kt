package com.example.sahils_app.Adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.sahils_app.Activity.ReqActivity
import com.example.sahils_app.R
import com.example.sahils_app.Userr
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso



class RequestAdapter(var context:Context) : RecyclerView.Adapter<RequestAdapter.MyViewHolder>() {

    private val userList = ArrayList<Userr>()
    private var storageRef = Firebase.storage
    private lateinit var uri : Uri

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.request_design,
            parent,false
        )
        return MyViewHolder(itemView)

    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = userList[position]
        holder.fName.text = currentitem.name
        Picasso.get().load(currentitem.url).into(holder.fImage)
///////////////////////////////////////////////////////////////////////


        /*holder.reqSend.setOnClickListener {

            var i = Intent(context, ReqActivity::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            i.putExtra("name",currentitem.name)
            i.putExtra("url",currentitem.url)
            context.startActivity(i)

        }*/
    }
////////////////////////////////////////////////////////////////////////
    override fun getItemCount(): Int {
        return userList.size
    }

    fun updateUserList(userList: List<Userr>){

        this.userList.clear()
        this.userList.addAll(userList)
        notifyDataSetChanged()

    }

    class  MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        var fName : TextView = itemView.findViewById(R.id.fName)
        var fImage : ImageView = itemView.findViewById(R.id.fImage)
        var reqSend : Button = itemView.findViewById(R.id.reqSend)

    }

}