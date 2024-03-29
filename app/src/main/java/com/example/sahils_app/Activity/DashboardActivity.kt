package com.example.sahils_app.Activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.sahils_app.Adapter.MyAdapter

import com.example.sahils_app.Fragments.FriendsFragment
import com.example.sahils_app.R
import com.example.sahils_app.databinding.ActivityDashboardBinding

class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding
    lateinit var shared: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setSupportActionBar(binding.tool)
      /*  binding.tab.setupWithViewPager(binding.viewpager)*/
      /*  setupdata()*/

        supportFragmentManager.beginTransaction().replace(binding.frame.id,FriendsFragment()).commit()

        shared=getSharedPreferences("USER", Context.MODE_PRIVATE)
        Toast.makeText(applicationContext, "Welcome : "+shared.getString("eml","null"), Toast.LENGTH_SHORT).show()
    }

   /* private fun setupdata() {
        var adapter = MyAdapter(supportFragmentManager)
        adapter.adddata(FriendsFragment(), "")
        binding.viewpager.adapter = adapter
    }*/

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.lg->
            {
                shared.edit().clear().commit()
                var i= Intent(applicationContext,LoginActivity::class.java)
                startActivity(i)
            }
            R.id.updateProfile->
            {
                var i= Intent(applicationContext,ProfileActivity::class.java)
                startActivity(i)
            }

        }
        return super.onOptionsItemSelected(item)
    }
}