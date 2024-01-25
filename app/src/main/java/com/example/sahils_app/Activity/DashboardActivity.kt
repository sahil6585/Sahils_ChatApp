package com.example.sahils_app.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.sahils_app.Adapter.MyAdapter
import com.example.sahils_app.Fragments.AllUserFragment
import com.example.sahils_app.Fragments.ChatFragment
import com.example.sahils_app.Fragments.FriendsFragment
import com.example.sahils_app.Fragments.RequestFragment
import com.example.sahils_app.R
import com.example.sahils_app.databinding.ActivityDashboardBinding

class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setSupportActionBar(binding.tool)
        binding.tab.setupWithViewPager(binding.viewpager)
        setupdata()
    }

    private fun setupdata() {
        var adapter = MyAdapter(supportFragmentManager)
        adapter.adddata(RequestFragment(), "REQUESTS")
        adapter.adddata(ChatFragment(), "CHATS")
        adapter.adddata(FriendsFragment(), "FRIENDS")
        binding.viewpager.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.lg->
            {
               /* var i= Intent(applicationContext,LoginActivity::class.java)
                startActivity(i)*/
            }
            R.id.AllUser->
            {
                var fm : FragmentManager =supportFragmentManager
                var t  : FragmentTransaction =fm.beginTransaction()
                t.replace(R.id.ALLUSERFRAGMENT,AllUserFragment()).commit()
            }

        }
        return super.onOptionsItemSelected(item)
    }
}