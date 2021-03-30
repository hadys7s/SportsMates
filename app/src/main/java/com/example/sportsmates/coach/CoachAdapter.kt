package com.example.sportsmates.coach

import android.content.Context
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sportsmates.databinding.CoachListItemBinding
import com.example.sportsmates.ext.inflater

class CoachAdapter(private val coachList: List<Coach>, private val context: FragmentActivity?) :
    RecyclerView.Adapter<CoachAdapter.ViewHolder>() {
    var onItemClick: ((Coach) -> Unit)? = null


    inner class ViewHolder(private val binding: CoachListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(coachItem: Coach) {
            binding.coachName.text = coachItem.name
            binding.coachSport.text = coachItem.sportName
            binding.coachAddress.text = coachItem.address
            binding.pricePerHour.text = coachItem.pricePerHour + "/hour"
            Glide.with(context!!)
                .load(coachItem.imageList[0])
                .into(binding.coachImage)
            itemView.setOnClickListener { onItemClick?.invoke(coachItem) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(CoachListItemBinding.inflate(parent.context.inflater, parent, false))
    }

    override fun getItemCount(): Int = coachList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(coachList[position])
    }


}
