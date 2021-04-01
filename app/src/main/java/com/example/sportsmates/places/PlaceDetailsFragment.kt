package com.example.sportsmates.places

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sportsmates.databinding.FragmentCoachDetailsBinding

class PlaceDetailsFragment : Fragment() {

    private var _binding: FragmentCoachDetailsBinding? = null
    private val binding get() = _binding!!

    private val images = listOf(
        "https://images.unsplash.com/photo-1517649763962-0c623066013b?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80",
        "https://images.unsplash.com/photo-1541534741688-6078c6bfb5c5?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1349&q=80",
        "https://images.unsplash.com/photo-1459865264687-595d652de67e?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80"
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCoachDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        attachCLickListeners()
        binding.coachImagesSlider.setItems(images)
        binding.coachImagesSlider.addTimerToSlide(2000)
        binding.coachImagesSlider.getIndicator()


    }

    private fun attachCLickListeners() {
        binding.coachImagesSlider.setOnClickListener {
            binding.coachImagesSlider.openfullScreen()

        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    companion object {
        fun newInstance() =
            PlaceDetailsFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}