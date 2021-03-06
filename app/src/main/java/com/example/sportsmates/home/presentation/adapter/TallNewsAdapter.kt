package com.example.sportsmates.home.presentation.adapter

import android.content.Context
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.sportsmates.databinding.NewsTallItemBinding
import com.example.sportsmates.ext.inflater
import com.example.sportsmates.home.domain.entities.NewsItem

class TallNewsAdapter(
    private val newsList: List<NewsItem>?,
    private val context: Context
) :
    RecyclerView.Adapter<TallNewsAdapter.ViewHolder>() {
    var onItemClick: ((NewsItem, ImageView) -> Unit)? = null


    inner class ViewHolder(private val binding: NewsTallItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(newsItem: NewsItem) {
            binding.title.text = newsItem.title
            Glide.with(context)
                .load(newsItem.imageUrl)
                .transform(RoundedCorners(20))
                .into(binding.newsTallImage)
            itemView.setOnClickListener { onItemClick?.invoke(newsItem,binding.newsTallImage) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(NewsTallItemBinding.inflate(parent.context.inflater, parent, false))
    }

    override fun getItemCount(): Int = newsList!!.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        newsList?.get(position)?.let { holder.bind(it) }
    }

}
