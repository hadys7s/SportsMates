package com.example.sportsmates.home.events

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.sportsmates.databinding.ActivityEventDetailBinding
import com.example.sportsmates.ext.setFullScreenWithTransparentStatusBar


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
    private fun fetchEventData(){
        val event:Event?=intent.getParcelableExtra(EVENT_ITEM)
        bindData(event)
    }
    private fun bindData(event:Event?){
        binding.eventName.text=event!!.name
        binding.details.text=event!!.description
        binding.openValue.text=event!!.start
        binding.closeValue.text=event!!.finish
        binding.ticketPrice.text=event!!.ticketPrice
        binding.location.text=event!!.place
        Glide.with(this)
            .load(event.img)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.eventImg)
    }
    private fun attachCLickListeners() {
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
    }
    companion object {
        private const val EVENT_ITEM = "eventItem"
        fun start(activity: FragmentActivity?, eventItem: Event,option:Bundle) {
            val intent = Intent(activity, EventDetailActivity::class.java)
            intent.putExtra(EVENT_ITEM, eventItem)
            activity?.startActivity(intent,option)
        }
    }
}