package com.example.sportsmates.coach

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.sportsmates.R
import com.google.firebase.storage.StorageReference
import com.smarteist.autoimageslider.SliderViewAdapter


class SliderAdapterExample(val context: Context) :
    SliderViewAdapter<SliderAdapterExample.SliderAdapterVH>() {
    private var mSliderItems: List<StorageReference> = ArrayList()

    fun renewItems(sliderItems: List<StorageReference>?) {
        mSliderItems = sliderItems!!
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup): SliderAdapterVH {
        val inflate: View =
            LayoutInflater.from(parent.context).inflate(R.layout.image_slider_layout_item, null)
        return SliderAdapterVH(inflate)
    }

    override fun onBindViewHolder(viewHolder: SliderAdapterVH, position: Int) {
        val sliderItem: StorageReference = mSliderItems[position]
        viewHolder.textViewDescription.textSize = 16f
        viewHolder.textViewDescription.setTextColor(Color.WHITE)
        GlideApp.with(context)
            .load(sliderItem)
            .fitCenter()
            .into(viewHolder.imageViewBackground)
    }

    override fun getCount(): Int {
        return mSliderItems.size
    }

    inner class SliderAdapterVH(itemView: View) : ViewHolder(itemView) {
        var myitemView: View = itemView
        var imageViewBackground: ImageView = itemView.findViewById(R.id.iv_auto_image_slider)
        var imageGifContainer: ImageView = itemView.findViewById(R.id.iv_gif_container)
        var textViewDescription: TextView = itemView.findViewById(R.id.tv_auto_image_slider)

    }
}
