package com.example.sportsmates.coach.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sportsmates.coach.CoachAdapter
import com.example.sportsmates.coach.data.Coach
import com.example.sportsmates.coach.data.toUiModel
import com.example.sportsmates.databinding.CoachFragmentBinding
import com.example.sportsmates.ext.displayErrorToast
import com.example.sportsmates.ext.stateCollector
import com.example.sportsmates.ext.stopShimmer
import com.example.sportsmates.networking.Resource
import org.koin.android.viewmodel.ext.android.viewModel


class CoachFragment : Fragment() {
    private val viewModel: CoachViewModel by viewModel()
    private var _binding: CoachFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var coachAdapter: CoachAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CoachFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupList()
        attachObservers()
    }

    private fun attachObservers() {
        stateCollector(viewModel.listOfSCoachesState)
        {
            when (it) {
                is Resource.Success -> {
                    stopShimmer(binding.shimmerViewContainer)
                    setCoaches(it.data)
                }
                is Resource.Error -> {
                    displayErrorToast(message = it.throwable.message.toString())
                }
                is Resource.Loading -> {

                }
            }
        }
    }


    private fun setupList() {
        binding.coachList.run {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }


    private fun setCoaches(coachesList: List<Coach?>) {
        coachAdapter = CoachAdapter(coachesList, requireActivity())
        binding.coachList.adapter = coachAdapter
        coachAdapter.onItemClick = {
            CoachDetailsActivity.start(activity, it?.toUiModel())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() = CoachFragment()
    }
}
