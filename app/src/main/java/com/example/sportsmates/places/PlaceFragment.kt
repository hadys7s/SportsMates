package com.example.sportsmates.places

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sportsmates.databinding.CoachFragmentBinding
import org.koin.android.viewmodel.ext.android.viewModel

class PlaceFragment : Fragment() {

    private val viewModel: PlaceViewModel by viewModel()

    private var _binding: CoachFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var placeAdapter: PlaceAdapter


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

    fun setCoaches(newsList: List<Place>) {
        placeAdapter = PlaceAdapter(newsList, activity)
        binding.coachList.adapter = placeAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    companion object {
        fun newInstance() = PlaceFragment()
    }


}