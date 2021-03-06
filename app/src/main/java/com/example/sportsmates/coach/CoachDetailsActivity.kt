package com.example.sportsmates.coach

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import com.example.sportsmates.booking.BookingActivity
import com.example.sportsmates.databinding.ActivityCoashDetailsBinding
import com.example.sportsmates.ext.setFullScreenWithTransparentStatusBar
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
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
        setFullScreenWithTransparentStatusBar()
        setupSlider()
        attachCLickListeners()
        fetchArguments()
        observeCoachImages()
    }

    private fun observeCoachImages(coachId: String?) {
        viewModel.retrievePhotoDetails(coachId)
    }

    private fun setupSlider() {
        sliderAdapter = SliderAdapterExample(this)
        binding.coachImagesSlider.setSliderAdapter(sliderAdapter)
        binding.coachImagesSlider.setIndicatorAnimation(IndicatorAnimationType.DROP);
        binding.coachImagesSlider.setSliderTransformAnimation(SliderAnimations.CUBEOUTROTATIONTRANSFORMATION);
        binding.coachImagesSlider.startAutoCycle();
    }

    private fun fetchArguments() {
        val coach: CoachUiModel? = intent.getParcelableExtra((COACH_ITEM))
        observeCoachImages(coach?.coachId)
        bindData(coach)
    }

    private fun bindData(coach: CoachUiModel?) {
        binding.coachName.text = coach?.name
        binding.details.text = coach?.about
        binding.address.text = coach?.address
        binding.sportName.text = coach?.sportName
        binding.pricePerHour.text = coach?.pricePerHour
    }

    private fun observeCoachImages() {
        viewModel.listOfSCoachesImagesEvent.observe(this, Observer { imageList ->
            sliderAdapter.renewItems(imageList)
        })
    }

    private fun attachCLickListeners() {
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
        binding.bookBtn.setOnClickListener {
            startBookingActivity()
        }
    }

    private fun startBookingActivity(){
        val coach: CoachUiModel? = intent.getParcelableExtra((COACH_ITEM))
        BookingActivity.start(this,couchItem = coach)
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

