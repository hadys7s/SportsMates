package com.example.sportsmates.place

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sportsmates.databinding.FragmentPlaceBinding
import org.koin.android.viewmodel.ext.android.viewModel

class PlaceFragment : Fragment() {
    private val viewModel: PLaceViewModel by viewModel()
    private var _binding: FragmentPlaceBinding? = null
    private val binding get() = _binding!!
    private lateinit var placeAdapter: PlaceAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlaceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupList()
        viewModel._listOfSPlacesEvent.observe(this, Observer {
            setPlaces(it)
        })

    }


    private fun setupList() {
        binding.placesList.run {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }


    private fun setPlaces(placesList: List<Place>?) {
        placeAdapter = PlaceAdapter(placesList, activity)
        binding.placesList.adapter = placeAdapter
        placeAdapter.onItemClick = {
            PlaceDetailsActivity.start(activity, it.toUiModel())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    companion object {
        fun newInstance() = PlaceFragment()
    }

}