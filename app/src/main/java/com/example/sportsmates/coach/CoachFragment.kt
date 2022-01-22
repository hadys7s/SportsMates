package com.example.sportsmates.coach

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sportsmates.databinding.CoachFragmentBinding
import com.example.sportsmates.ext.stopShimmer
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
        viewModel.listOfSCoachesEvent.observe(this, Observer {
            stopShimmer(binding.shimmerViewContainer)
            setCoaches(it)
        })
    }


    private fun setupList() {
        binding.coachList.run {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }


    private fun setCoaches(newsList: List<Coach>?) {
        coachAdapter = CoachAdapter(newsList, activity)
        binding.coachList.adapter = coachAdapter
        coachAdapter.onItemClick = {
            CoachDetailsActivity.start(activity, it.toUiModel())
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
