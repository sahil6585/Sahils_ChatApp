package com.example.sahils_app.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.sahils_app.Model.Message
import com.example.sahils_app.R
import com.example.sahils_app.databinding.DeleteLayoutBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class MessageAdapter(var context: Context, var messageList:ArrayList<Message>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var  ITEM_RECEIVE = 1
    var  ITEM_SENT = 2
    lateinit var messages: ArrayList<Message>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType == 1)
        {
            //inflate receive
            var view: View = LayoutInflater.from(context).inflate(R.layout.receive_msg,parent,false)
            return ReceiveViewHolder(view)
        }
        else
        {
            //inflate sent
            var view: View = LayoutInflater.from(context).inflate(R.layout.send_msg,parent,false)
            return SentViewHolder(view)

        }

    }

    override fun getItemViewType(position: Int): Int {
        var currentMessage = messageList[position]

        if (FirebaseAuth.getInstance().currentUser!!.uid.equals(currentMessage.senderid))
        {
            return ITEM_SENT
        }
        else
        {
            return ITEM_RECEIVE
        }
    }
    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        var currentMessage = messageList[position]

        if (holder.javaClass==SentViewHolder::class.java)
        {
            //do the stuff for sent view holder

            var viewHolder = holder as SentViewHolder
            holder.sentMessage.text=currentMessage.message

        }
        else
        {
            //do the stuff for receive view holder
            var viewHolder = holder as ReceiveViewHolder
            holder.receiveMessage.text=currentMessage.message
        }



    }

    class SentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        var sentMessage = itemView.findViewById<TextView>(R.id.message)

    }
    class ReceiveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        var receiveMessage = itemView.findViewById<TextView>(R.id.message)

    }

}