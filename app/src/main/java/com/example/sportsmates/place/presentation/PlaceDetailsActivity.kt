package com.example.sportsmates.place.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import com.example.sportsmates.booking.BookingActivity
import com.example.sportsmates.coach.SliderAdapterExample
import com.example.sportsmates.databinding.ActivityPlaceDetailsBinding
import com.example.sportsmates.ext.setFullScreenWithTransparentStatusBar
import com.example.sportsmates.ext.stateCollector
import com.example.sportsmates.networking.Resource
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import org.koin.android.viewmodel.ext.android.viewModel

class PlaceDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlaceDetailsBinding

    private lateinit var sliderAdapter: SliderAdapterExample
    private var _place: PlaceUiModel? = null
    private val viewModel: PLaceViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlaceDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setFullScreenWithTransparentStatusBar()
        setupSlider()
        attachCLickListeners()
        fetchArguments()
        observeCoachImages()
    }

    private fun observeCoachImages(coachId: String?) {
        lifecycleScope.launchWhenResumed {
            viewModel.getPlacesListOfImages(coachId)
        }
    }

    private fun setupSlider() {
        sliderAdapter = SliderAdapterExample(this)
        binding.coachImagesSlider.setSliderAdapter(sliderAdapter)
        binding.coachImagesSlider.setIndicatorAnimation(IndicatorAnimationType.DROP);
        binding.coachImagesSlider.setSliderTransformAnimation(SliderAnimations.CUBEOUTROTATIONTRANSFORMATION);
        binding.coachImagesSlider.startAutoCycle();
    }

    private fun fetchArguments() {
        val place: PlaceUiModel? = intent.getParcelableExtra((PLACE_ITEM))
        observeCoachImages(place?.placeId)
        bindData(place)
    }

    private fun bindData(place: PlaceUiModel?) {
        binding.placeName.text = place?.name
        binding.details.text = place?.about
        binding.pricePerHour.text = place?.pricePerHour + "/Per Hour"
        binding.openValue.text = place?.open
        binding.closeValue.text = place?.close
        binding.location.text = place?.address
    }

    private fun observeCoachImages() {
        stateCollector(viewModel.listOfPlacesImagesEvent)
        { state ->
            when (state) {
                is Resource.Success -> {
                    sliderAdapter.renewItems(state.data)

                }
                is Resource.Error -> {

                }
                Resource.Loading -> {

                }
            }
        }
    }

    private fun attachCLickListeners() {
        binding.backBtn.setOnClickListener {
           onBackPressed()
        }
        binding.bookButton.setOnClickListener {
            startBookingActivity()
        }
    }
    private fun startBookingActivity(){
        val place: PlaceUiModel? = intent.getParcelableExtra((PLACE_ITEM))
        BookingActivity.start(this,placeItem = place )
    }

    companion object {
        private const val PLACE_ITEM = "placeItem"
        fun start(activity: FragmentActivity?, placeItem: PlaceUiModel) {
            val intent = Intent(activity, PlaceDetailsActivity::class.java)
            intent.putExtra(PLACE_ITEM, placeItem)
            activity?.startActivity(intent)
        }
    }
}