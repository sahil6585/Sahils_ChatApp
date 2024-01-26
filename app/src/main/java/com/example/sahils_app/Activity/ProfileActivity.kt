package com.example.sahils_app.Activity

import android.annotation.SuppressLint
import android.content.Intent

import android.net.Uri

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts

import com.example.sahils_app.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

class ProfileActivity : AppCompatActivity() {


    private lateinit var db :DatabaseReference
    private lateinit var binding: ActivityProfileBinding

   private var storageRef = Firebase.storage
      private lateinit var uri : Uri


    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        storageRef = FirebaseStorage.getInstance()


        val galleryImage = registerForActivityResult(
            ActivityResultContracts.GetContent(),
            ActivityResultCallback {
                binding.imageView.setImageURI(it)
                uri = it!!
            })

        binding.upload.setOnClickListener {
            galleryImage.launch("image/*")
        }

        binding.SaveProfile.setOnClickListener {

            var name = binding.nameBox.text.toString()
            storageRef.getReference("ProfileImage").child(System.currentTimeMillis().toString())
                .putFile(uri)
                .addOnSuccessListener { task->
                    task.metadata!!.reference!!.downloadUrl
                        .addOnSuccessListener {
                            var userId = FirebaseAuth.getInstance().currentUser!!.uid
                            var mapImage = mapOf(
                                "url" to it.toString(),
                                "name" to name,
                                "uid" to userId
                            )

                            var databaseReference = FirebaseDatabase.getInstance().getReference("UserProfile")
                            databaseReference.child(userId).setValue(mapImage)
                                .addOnSuccessListener {
                                    Toast.makeText(this, "Successfull", Toast.LENGTH_SHORT).show()
                                    startActivity(Intent(this,DashboardActivity::class.java))
                                }
                                .addOnFailureListener{
                                    Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show()
                                }
                        }
                }

            //////////////////////////////////////////////////////

            storageRef.getReference("Request_Image").child(System.currentTimeMillis().toString())
                .putFile(uri)
                .addOnSuccessListener { task->
                    task.metadata!!.reference!!.downloadUrl
                        .addOnSuccessListener {
                            var userId = FirebaseAuth.getInstance().currentUser!!.uid
                            var mapImage = mapOf(
                                "request_url" to it.toString(),
                                "request_name" to name
                            )

                            var databaseReference = FirebaseDatabase.getInstance().getReference("request_Profile")
                            databaseReference.child(userId).setValue(mapImage)
                                .addOnSuccessListener {
                                    Toast.makeText(this, "Successfull", Toast.LENGTH_SHORT).show()
                                   // startActivity(Intent(this,DashboardActivity::class.java))
                                }
                                .addOnFailureListener{
                                   // Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show()
                                }
                        }
                }

        }
    }
}
