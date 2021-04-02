package com.example.sportsmates.coach

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import com.example.sportsmates.R
import com.example.sportsmates.databinding.ActivityCoashDetailsBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class CoachDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCoashDetailsBinding

    private lateinit var sliderAdapter: SliderAdapterExample

    private val viewModel: CoachViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoashDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setupSlider()
        attachCLickListeners()
        fetchArguments()
        observeCoachImages()


    }

    private fun observeCoachImages(coachId: String?) {
        GlobalScope.launch {
            viewModel.retrievePhotoDetails(coachId)
        }
    }

    private fun setupSlider() {
        sliderAdapter = SliderAdapterExample(this)
        binding.coachImagesSlider.setSliderAdapter(sliderAdapter)
    }

    private fun fetchArguments() {
        val coach: CoachUiModel? = intent.getParcelableExtra((COACH_ITEM))
        observeCoachImages(coach?.coachId)
        bindData(coach)
    }

    private fun bindData(coach: CoachUiModel?) {
        binding.coachName.text = coach?.name
        binding.details.text = coach?.about
        binding.pricePerHour.text = coach?.pricePerHour + "/Per Hour"
        binding.address.text = coach?.address
        binding.sportName.text = coach?.sportName
    }

    private fun observeCoachImages() {
        viewModel._listOfSCoachesimagesEvent.observe(this, Observer { imageList ->
            sliderAdapter.renewItems(imageList)
        })
    }

    private fun attachCLickListeners() {
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
    }

    companion object {
        private const val COACH_ITEM = "coachItem"
        fun start(activity: FragmentActivity?, coachItem: CoachUiModel) {
            val intent = Intent(activity, CoachDetailsActivity::class.java)
            intent.putExtra(COACH_ITEM, coachItem)
            activity?.startActivity(intent)
        }
    }

}