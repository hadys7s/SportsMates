package com.example.sportsmates.coach

import android.content.Context
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.example.sportsmates.databinding.CoachListItemBinding
import com.example.sportsmates.ext.inflater
import com.firebase.ui.storage.images.FirebaseImageLoader
import com.google.firebase.storage.StorageReference
import java.io.InputStream

class CoachAdapter(private val CoachList: List<Coach>?, private val context: FragmentActivity?) :
    RecyclerView.Adapter<CoachAdapter.ViewHolder>() {
    var onItemClick: ((Coach) -> Unit)? = null


    inner class ViewHolder(private val binding: CoachListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(coachItem: Coach) {
            binding.partnerName.text = coachItem.name
            binding.partnerSport.text = coachItem.sportName
            binding.partnerAddress.text = coachItem.address
            binding.pricePerHour.text = coachItem.pricePerHour + "/hour"
            GlideApp.with(context!!)
                .load(coachItem.imageList?.get(0))
                .into(binding.partnerImage)
            itemView.setOnClickListener { onItemClick?.invoke(coachItem) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(CoachListItemBinding.inflate(parent.context.inflater, parent, false))
    }

    override fun getItemCount(): Int = CoachList!!.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        CoachList?.get(position)?.let { holder.bind(it) }
    }


}

@GlideModule
class MyAppGlideModule : AppGlideModule() {
    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        // Register FirebaseImageLoader to handle StorageReference
        registry.append(
            StorageReference::class.java, InputStream::class.java,
            FirebaseImageLoader.Factory()
        )
    }
}