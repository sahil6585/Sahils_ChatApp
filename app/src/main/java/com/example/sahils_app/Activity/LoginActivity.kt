    package com.example.sahils_app.Activity

    import android.content.Context
    import android.content.DialogInterface
    import android.content.Intent
    import android.content.SharedPreferences
    import androidx.appcompat.app.AppCompatActivity
    import android.os.Bundle
    import android.widget.Toast
    import androidx.appcompat.app.AlertDialog
    import com.example.sahils_app.MainActivity
    import com.example.sahils_app.R
    import com.example.sahils_app.databinding.ActivityLoginBinding
    import com.google.firebase.auth.FirebaseAuth

    class LoginActivity : AppCompatActivity() {

        private lateinit var binding: ActivityLoginBinding
        lateinit var mAuth: FirebaseAuth
        lateinit var shared: SharedPreferences

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityLoginBinding.inflate(layoutInflater)
            val view = binding.root
            setContentView(view)

            mAuth = FirebaseAuth.getInstance()

            shared=getSharedPreferences("USER", Context.MODE_PRIVATE)

            if (shared.getBoolean("USER",false) && !shared.getString("eml","")!!.isEmpty())
            {
                startActivity(Intent(applicationContext,DashboardActivity::class.java))
                finish()
            }

            binding.registerbtn.setOnClickListener {
                var i = Intent(applicationContext,RegisterActivity::class.java)
                startActivity(i)
            }

            binding.signinbtn.setOnClickListener {


                var email = binding.email.text.toString()
                var pass = binding.password.text.toString()

                login(email,pass)


                var i=Intent(applicationContext,ProfileActivity::class.java)
                var editor:SharedPreferences.Editor = shared.edit()
                editor.putBoolean("USER",true)
                editor.putString("eml",email)
                editor.putString("pass",pass)
                editor.commit()
                startActivity(i)

            }
        }

        private fun login(email: String, pass: String) {
            mAuth.signInWithEmailAndPassword(email,pass)
                .addOnCompleteListener(this) { task->
                    if(task.isSuccessful)
                    {
                        //code for logging in user
                        var i = Intent(applicationContext, ProfileActivity::class.java)
                        finish()
                        startActivity(i)
                    }
                    else
                    {
                        Toast.makeText(applicationContext, "user does not exist", Toast.LENGTH_SHORT).show()
                    }
                }
        }


        override fun onBackPressed() {
            var builder = AlertDialog.Builder(this)
            builder.setTitle("Are you sure you want to exit?")
            builder.setPositiveButton("YES",{ dialogInterface: DialogInterface, i: Int -> finishAffinity()})
            builder.setNegativeButton("NO",{ dialogInterface: DialogInterface, i: Int -> })
            builder.show()
        }
    }