package com.example.sportsmates.home.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(val items:ArrayList<Fragment>,val context:FragmentActivity):FragmentStateAdapter(context) {

    override fun getItemCount(): Int =items.size

    override fun createFragment(position: Int): Fragment {
        return items[position]
    }
}