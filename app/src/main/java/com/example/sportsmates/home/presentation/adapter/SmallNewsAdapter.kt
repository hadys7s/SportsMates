package com.example.sportsmates.home.presentation.adapter

import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sportsmates.databinding.SmallNewsItemBinding
import com.example.sportsmates.ext.inflater
import com.example.sportsmates.home.domain.entities.NewsItem

class SmallNewsAdapter(
    private val newsList: List<NewsItem>?,
    private val context: FragmentActivity?
) :
    RecyclerView.Adapter<SmallNewsAdapter.ViewHolder>() {
    var onItemClick: ((NewsItem, ImageView) -> Unit)? = null


    inner class ViewHolder(private val binding: SmallNewsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(newsItem: NewsItem) {
            binding.newTitle.text = newsItem.title
            Glide.with(context!!)
                .load(newsItem.imageUrl)
                .into(binding.newsImage)
            itemView.setOnClickListener { onItemClick?.invoke(newsItem,binding.newsImage) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(SmallNewsItemBinding.inflate(parent.context.inflater, parent, false))
    }

    override fun getItemCount(): Int = newsList!!.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        newsList?.get(position)?.let { holder.bind(it) }
    }

}
