package com.example.sportsmates.coach

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sportsmates.databinding.CoachFragmentBinding
import com.example.sportsmates.places.Place
import com.example.sportsmates.places.PlaceAdapter
import com.example.sportsmates.places.PlaceFragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class CoachFragment : Fragment() {

    private val viewModel: CoachViewModel by viewModel()

    private var _binding: CoachFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var coachAdapter: CoachAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CoachFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupList()
        viewModel._listOfSCoachesEvent.observe(this, Observer {
            setCoaches(it)
        })

    }


    private fun setupList() {
        binding.coachList.run {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }


    fun setCoaches(newsList: List<Coach>?) {
        coachAdapter = CoachAdapter(newsList, activity)
        binding.coachList.adapter = coachAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    companion object {
        fun newInstance() = CoachFragment()
    }


}