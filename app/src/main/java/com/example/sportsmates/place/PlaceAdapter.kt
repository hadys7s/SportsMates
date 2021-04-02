package com.example.sportsmates.place

import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.sportsmates.coach.GlideApp
import com.example.sportsmates.databinding.CoachListItemBinding
import com.example.sportsmates.ext.inflater

class PlaceAdapter(private val placeList: List<Place>?, private val context: FragmentActivity?) :
    RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {
    var onItemClick: ((Place) -> Unit)? = null


    inner class ViewHolder(private val binding: CoachListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(placeItem: Place) {
            binding.partnerName.text = placeItem.name
            binding.partnerSport.text = placeItem.placeType
            binding.partnerAddress.text = placeItem.address
            binding.pricePerHour.text = placeItem.pricePerHour + "/hour"
            GlideApp.with(context!!)
                .load(placeItem.imageList?.get(0))
                .into(binding.partnerImage)
            itemView.setOnClickListener { onItemClick?.invoke(placeItem) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(CoachListItemBinding.inflate(parent.context.inflater, parent, false))
    }

    override fun getItemCount(): Int = placeList!!.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        placeList?.get(position)?.let { holder.bind(it) }
    }

}
