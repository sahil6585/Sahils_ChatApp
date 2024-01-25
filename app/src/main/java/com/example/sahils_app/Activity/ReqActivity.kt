package com.example.sahils_app.Activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sahils_app.databinding.ActivityReqBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso


class ReqActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReqBinding
    private var storageRef = Firebase.storage
    private lateinit var db : DatabaseReference
    private lateinit var uri : Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReqBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val i = intent
        var name = i.getStringExtra("name")
        var image = i.getStringExtra("url")
        /* Log.d("abcabc", image!!)*/

        Picasso.get().load(image).into(binding.rImage)
        binding.rName.setText(name)

        binding.ConReqSend.setOnClickListener {

            storageRef.getReference("Req_Profile_Image")
                .child(System.currentTimeMillis().toString())
                .putFile(Uri.parse(image))
                .addOnSuccessListener { task ->
                    task.metadata!!.reference!!.downloadUrl
                        .addOnSuccessListener {
                            var userId = FirebaseAuth.getInstance().currentUser!!.uid
                            var mapImage = mapOf(
                                "url" to it.toString(),
                                "name" to name
                            )

                            var databaseReference =
                                FirebaseDatabase.getInstance().getReference("Req_User_Profile")
                            databaseReference.child(userId).setValue(mapImage)
                                .addOnSuccessListener {
                                    Toast.makeText(this, "Successfull", Toast.LENGTH_SHORT).show()
                                    // startActivity(Intent(this,DashboardActivity::class.java))
                                }
                                .addOnFailureListener {
                                    Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show()
                                }
                        }
                }


        }
    }
}