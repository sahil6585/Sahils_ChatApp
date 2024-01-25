package com.example.sahils_app.Adapter


import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sahils_app.Activity.ChattingActivity
import com.example.sahils_app.FriendUserr
import com.example.sahils_app.R
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso



class FriendAdapter(var context:Context) : RecyclerView.Adapter<FriendAdapter.MyViewHolder3>() {

    private val userList = ArrayList<FriendUserr>()
    private var storageRef = Firebase.storage
    private lateinit var uri : Uri

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder3 {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.friend_design,
            parent,false
        )
        return MyViewHolder3(itemView)

    }


    override fun onBindViewHolder(holder: MyViewHolder3, position: Int) {

        val currentitem = userList[position]
        holder.fName.text = currentitem.name
        Picasso.get().load(currentitem.url).into(holder.fImage)

        holder.itemView.setOnClickListener {

           /* var bundle = Bundle()
            bundle.putString("name",currentitem.name )
            chatFragment.arguments = bundle
*/
            var i = Intent(context,ChattingActivity::class.java)
            i.putExtra("name",currentitem.name)
            i.putExtra("image",currentitem.url)
            i.putExtra("uid",currentitem.uid)
            context.startActivity(i)



        }


    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun updateUserList(userList: List<FriendUserr>){

        this.userList.clear()
        this.userList.addAll(userList)
        notifyDataSetChanged()

    }

    class  MyViewHolder3(itemView : View) : RecyclerView.ViewHolder(itemView){

        var fName : TextView = itemView.findViewById(R.id.fName)
        var fImage : ImageView = itemView.findViewById(R.id.fImage)

    }

}