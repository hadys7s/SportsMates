package com.example.sportsmates.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sportsmates.R
import com.example.sportsmates.databinding.FragmentHomeBinding
import com.example.sportsmates.home.events.EventFragment
import com.example.sportsmates.home.news.presentation.fragment.NewsFragment
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
           attachViewPager()
    }
    private fun attachViewPager(){
        val fragments:ArrayList<Fragment>?= arrayListOf(
            NewsFragment.newInstance(),
            EventFragment.newInstance()
        )
        val adapter= activity?.let { ViewPagerAdapter(fragments!!, it) }
        binding.viewPager.adapter=adapter
        TabLayoutMediator(binding.tabs,binding.viewPager){tab ,position->
            when(position){
                0->{tab.text=getString(R.string.tab_layout_news)
                }
                1->{tab.text=getString(R.string.tab_layout_event)}
            }
        }.attach()
        binding.viewPager.isUserInputEnabled = false
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}