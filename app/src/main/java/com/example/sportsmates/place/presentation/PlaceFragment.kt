package com.example.sportsmates.place.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sportsmates.databinding.FragmentPlaceBinding
import com.example.sportsmates.ext.displayErrorToast
import com.example.sportsmates.ext.stateCollector
import com.example.sportsmates.ext.stopShimmer
import com.example.sportsmates.networking.Resource
import com.example.sportsmates.place.data.Place
import com.example.sportsmates.place.data.toUiModel
import org.koin.android.viewmodel.ext.android.viewModel

class PlaceFragment : Fragment() {
    private val viewModel: PLaceViewModel by viewModel()
    private var _binding: FragmentPlaceBinding? = null
    private val binding get() = _binding!!
    private lateinit var placeAdapter: PlaceAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlaceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupList()
        attachObservers()

    }

    private fun attachObservers() {
        stateCollector(viewModel.listOfSPlacesEvent)
        { state ->
            when (state) {
                is Resource.Success -> {
                    stopShimmer(binding.shimmerViewContainer)
                    setPlaces(state.data)
                }
                is Resource.Error -> {
                    displayErrorToast("Error",state.exception.message.toString())
                }
                Resource.Loading -> {

                }
            }
        }
    }


    private fun setupList() {
        binding.placesList.run {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }


    private fun setPlaces(placesList: List<Place?>) {
        placeAdapter = PlaceAdapter(placesList, requireActivity())
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
