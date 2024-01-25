    package com.example.sahils_app.Activity

    import android.content.DialogInterface
    import android.content.Intent
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

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityLoginBinding.inflate(layoutInflater)
            val view = binding.root
            setContentView(view)

            mAuth = FirebaseAuth.getInstance()

            binding.registerbtn.setOnClickListener {
                var i = Intent(applicationContext,RegisterActivity::class.java)
                startActivity(i)
            }

            binding.signinbtn.setOnClickListener {

                var email = binding.email.text.toString()
                var pass = binding.password.text.toString()

                login(email,pass)

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