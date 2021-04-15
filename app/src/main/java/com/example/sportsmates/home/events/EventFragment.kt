package com.example.sportsmates.home.events

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sportsmates.R
import com.example.sportsmates.databinding.FragmentEventBinding
import org.koin.android.viewmodel.ext.android.viewModel
import www.sanju.motiontoast.MotionToast

class EventFragment : Fragment() {
    private val viewModel: EventViewModel by viewModel()
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
        viewModel.retriveEventSucess
            .observe(this, Observer {
                stopShimmerLoading()
                setUsers(it)
            })
        viewModel.retriveEventError.observe(this, Observer {
            stopShimmerLoading()
            MotionToast.darkToast(
                activity!!, "", it,
                MotionToast.TOAST_WARNING,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                ResourcesCompat.getFont(activity!!, R.font.helvetica_regular)
            )
        })
    }

    private fun setupList() {
        binding.shimmerViewContainer.startShimmer()
        binding.eventList.run {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun stopShimmerLoading() {
        binding.shimmerViewContainer.stopShimmer()
        binding.shimmerViewContainer.visibility = View.GONE
    }

    private fun setUsers(eventList: List<Event>?) {
        eventAdapter = EventAdapter(eventList, activity)
        binding.eventList.adapter = eventAdapter
        eventAdapter.onItemClick = { event, targetImage ->
            openActivityWithTransitionAnimation(event, targetImage)
        }
    }

    private fun openActivityWithTransitionAnimation(event: Event, targetImage: ImageView) {
        val option =
            ActivityOptionsCompat.makeSceneTransitionAnimation(activity!!, targetImage, "img")
                .toBundle()
        EventDetailActivity.start(activity, event, option!!)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() = EventFragment()
    }

}