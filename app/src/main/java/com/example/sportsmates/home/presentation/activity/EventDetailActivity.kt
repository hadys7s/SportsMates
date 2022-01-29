package com.example.sportsmates.home.presentation.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.sportsmates.booking.BookingActivity
import com.example.sportsmates.databinding.ActivityEventDetailBinding
import com.example.sportsmates.ext.setFullScreenWithTransparentStatusBar
import com.example.sportsmates.home.data.datamodels.EventDataItem


class EventDetailActivity : AppCompatActivity() {
    private lateinit var binding:ActivityEventDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setFullScreenWithTransparentStatusBar()
        attachCLickListeners()
        fetchEventData()
    }

    private fun fetchEventData() {
        val eventDataItem: EventDataItem? = intent.getParcelableExtra(EVENT_ITEM)
        bindData(eventDataItem)
    }

    private fun bindData(eventDataItem: EventDataItem?) {
        binding.eventName.text = eventDataItem?.name
        binding.details.text = eventDataItem?.description
        binding.openValue.text = eventDataItem?.start
        binding.closeValue.text = eventDataItem?.finish
        binding.ticketPrice.text = eventDataItem?.ticketPrice
        binding.location.text = eventDataItem?.place
        Glide.with(this)
            .load(eventDataItem?.img)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.eventImg)
    }

    private fun attachCLickListeners() {
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
        binding.attendBtn.setOnClickListener {
           startBookingActivity()
        }
    }
    private fun startBookingActivity() {
        val eventDataItem: EventDataItem? = intent.getParcelableExtra(EVENT_ITEM)
        BookingActivity.start(this, eventDataItemItem = eventDataItem)
    }

    companion object {
        private const val EVENT_ITEM = "eventItem"
        fun start(activity: FragmentActivity?, eventDataItemItem: EventDataItem, option: Bundle?) {
            val intent = Intent(activity, EventDetailActivity::class.java)
            intent.putExtra(EVENT_ITEM, eventDataItemItem)
            activity?.startActivity(intent, option)
        }
    }
}