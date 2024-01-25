package com.example.sahils_app.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class MyAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm)
{

    //FOR FREGMENT ONLY

    var listFragment:MutableList<Fragment> = ArrayList()
    var listtitle:MutableList<String> = ArrayList()
    override fun getCount(): Int {
        return listFragment.size
    }

    override fun getItem(position: Int): Fragment {
        return listFragment.get(position)
    }

    override fun getPageTitle(position: Int): CharSequence {
        return listtitle.get(position)
    }

    fun adddata(fragment: Fragment,title:String)
    {
        listFragment.add(fragment)
        listtitle.add(title)
    }
}