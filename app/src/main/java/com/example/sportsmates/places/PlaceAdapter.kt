package com.example.sportsmates.places

import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sportsmates.databinding.CoachListItemBinding
import com.example.sportsmates.ext.inflater

class PlaceAdapter(private val placeList: List<Place>, private val context: FragmentActivity?) :
    RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {
    var onItemClick: ((Place) -> Unit)? = null


    inner class ViewHolder(private val binding: CoachListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(placeItem: Place) {
            binding.coachName.text = placeItem.name
          //  binding.coachSport.text = placeItem.sportName
            binding.coachAddress.text = placeItem.address
            binding.pricePerHour.text = placeItem.pricePerHour + "/hour"
            Glide.with(context!!)
                .load(placeItem.imageList[0])
                .into(binding.coachImage)
            itemView.setOnClickListener { onItemClick?.invoke(placeItem) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(CoachListItemBinding.inflate(parent.context.inflater, parent, false))
    }

    override fun getItemCount(): Int = placeList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(placeList[position])
    }


}
