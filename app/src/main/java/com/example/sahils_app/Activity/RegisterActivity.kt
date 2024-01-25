package com.example.sahils_app.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.sahils_app.Model.UserModel
import com.example.sahils_app.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    lateinit var mAuth: FirebaseAuth
    lateinit var mDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        mAuth = FirebaseAuth.getInstance()

        binding.create.setOnClickListener {
            var name = binding.name.text.toString()
            var email = binding.emaill.text.toString()
            var pass = binding.pass.text.toString()

            signup(name,email,pass)

        }
    }

    private fun signup(name: String, email: String, pass: String) {
        mAuth.createUserWithEmailAndPassword(email,pass)
            .addOnCompleteListener(this) { task->
                if(task.isSuccessful)
                {
                    //code for jumping to home
                    addUserToDatabase(name,email,pass, mAuth.currentUser!!.uid)
                    val i = Intent(this,LoginActivity::class.java)
                    finish()
                    startActivity(i)

                }
                else
                {
                    Toast.makeText(applicationContext, "some error occurred", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun addUserToDatabase(name: String, email: String, pass: String, uid: String) {
        mDbRef = FirebaseDatabase.getInstance().getReference()
        mDbRef.child("USER").child(uid).setValue(UserModel(name,email,pass,uid))
    }
}