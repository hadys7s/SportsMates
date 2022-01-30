package com.example.sportsmates.home.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sportsmates.databinding.FragmentEventBinding
import com.example.sportsmates.ext.displayWarningToast
import com.example.sportsmates.ext.withTransitionAnimation
import com.example.sportsmates.ext.stopShimmer
import com.example.sportsmates.home.data.datamodels.EventDataItem
import com.example.sportsmates.home.presentation.adapter.EventAdapter
import com.example.sportsmates.home.presentation.viewmodel.EventsViewModel
import com.example.sportsmates.home.presentation.activity.EventDetailActivity
import org.koin.android.viewmodel.ext.android.viewModel

class EventFragment : Fragment() {
    private val viewModel: EventsViewModel by viewModel()
    private var _binding: FragmentEventBinding? = null
    private val binding get() = _binding!!
    private lateinit var eventAdapter: EventAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupList()
        attachEventObservers()
    }

    private fun attachEventObservers() {
        viewModel.listOfEventDataItems.observe(this, Observer {
                stopShimmer(binding.shimmerViewContainer)
                setUsers(it)
            })
        viewModel.retriveEventError.observe(this, Observer {
            stopShimmer(binding.shimmerViewContainer)
            displayWarningToast("Warning", it)
        })
    }

    private fun setupList() {
        binding.eventList.run {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun setUsers(eventDataItemList: List<EventDataItem?>) {
        eventAdapter = EventAdapter(eventDataItemList, requireActivity())
        binding.eventList.adapter = eventAdapter
        eventAdapter.onItemClick = { event, targetImage ->
            EventDetailActivity.start(requireActivity(), event, withTransitionAnimation(targetImage))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    companion object {
        fun newInstance() = EventFragment()
    }
}
