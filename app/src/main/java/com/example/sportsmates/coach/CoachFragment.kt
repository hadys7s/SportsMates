package com.example.sportsmates.coach

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sportsmates.databinding.CoachFragmentBinding
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

    }

    fun setCoaches(newsList: List<Coach>) {
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