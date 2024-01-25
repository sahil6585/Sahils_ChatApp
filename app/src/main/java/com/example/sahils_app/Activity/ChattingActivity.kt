package com.example.sahils_app.Activity

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sahils_app.Adapter.MessageAdapter
import com.example.sahils_app.Model.Message
import com.example.sahils_app.R
import com.example.sahils_app.databinding.ActivityChattingBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso


class ChattingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChattingBinding
    lateinit var messageAdapter: MessageAdapter
    lateinit var messageList:ArrayList<Message>
    lateinit var mDbRef : DatabaseReference
    lateinit var database: FirebaseDatabase
    var receiverRoom:String? = null
    var senderRoom:String? = null
    var senderUid : String? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChattingBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        database = FirebaseDatabase.getInstance()
        messageList = ArrayList()
        messageAdapter = MessageAdapter(this,messageList)
        mDbRef= FirebaseDatabase.getInstance().getReference()

        val i = intent
        val name = i.getStringExtra("name")
        var receiverUid = i.getStringExtra("uid")
        var url = i.getStringExtra("image")
        Picasso.get().load(url).into(binding.profile01)
        binding.name.text=name

        var senderUid = FirebaseAuth.getInstance().currentUser!!.uid
        senderRoom= receiverUid + senderUid
        receiverRoom= senderUid + receiverUid

        setSupportActionBar(binding.toolbar)
        supportActionBar!!.title = null

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter=messageAdapter

        //logic for adding data to recyclerview
        mDbRef.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object : ValueEventListener{
                @SuppressLint("NotifyDataSetChanged")
                override fun onDataChange(snapshot: DataSnapshot) {

                    messageList.clear()

                    for(postSnapshot in snapshot.children)
                    {
                        var message = postSnapshot.getValue(Message::class.java)
                        messageList.add(message!!)
                    }
                    messageAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })


        //adding the message to database
        binding.sendBtn.setOnClickListener {

            var message = binding.messageBox.text.toString()
            var messageObject = Message(message,senderUid)

            mDbRef.child("chats").child(senderRoom!!).child("messages").push().setValue(messageObject)
                .addOnSuccessListener {
                    mDbRef.child("chats").child(receiverRoom!!).child("messages").push().setValue(messageObject)
                }
            binding.messageBox.setText("")
        }

        var handler = Handler()
        binding.messageBox.addTextChangedListener(object :TextWatcher{

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                database.reference.child("Presence")
                    .child(senderUid)
                    .setValue("typing...")
                handler.removeCallbacksAndMessages(null)
                handler.postDelayed(userStoppedTyping,1000)
            }
            var userStoppedTyping = Runnable {
                database.reference.child("Presence")
                    .child(senderUid)
                    .setValue("Online")
            }
        })

        senderUid= FirebaseAuth.getInstance().uid!!
        database.reference.child("Presence").child(receiverUid!!)
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists())
                    {
                        val status = snapshot.getValue(String::class.java)
                        if (status == "offline")
                        {
                            binding.status.visibility = View.GONE
                        }
                        else{
                            binding.status.setText(status)
                            binding.status.visibility = View.VISIBLE
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {}
            })
    }

/////////////////////////////////////////////////////////////////////////
    override fun onResume() {
        super.onResume()
        val currentId = FirebaseAuth.getInstance().uid
        database.reference.child("Presence")
            .child(currentId!!)
            .setValue("Online")
    }

    override fun onPause() {
        super.onPause()
        val currentId = FirebaseAuth.getInstance().uid
        database.reference.child("Presence")
            .child(currentId!!)
            .setValue("offline")
    }
}