package com.example.sportsmates.home.presentation.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.sportsmates.databinding.ActivityNewsDetailsBinding
import com.example.sportsmates.ext.setFullScreenWithTransparentStatusBar
import com.example.sportsmates.home.domain.entities.NewsItem

class NewsDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewsDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        fetchArguments()
        setFullScreenWithTransparentStatusBar()
        attachCLickListeners()
    }

    private fun fetchArguments() {
        val newsItem: NewsItem? = intent.getParcelableExtra((NEWS_ITEM))
        bindData(newsItem)
    }

    private fun bindData(newsItem: NewsItem?) {
        binding.newsTitle.text = newsItem?.title
        Glide.with(this)
            .load(newsItem?.imageUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.newsImage)
        binding.sourceDate.text = newsItem?.website + newsItem?.publishedAt
        binding.details.text = newsItem?.content

    }

    private fun attachCLickListeners() {
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
    }


    companion object {
        private const val NEWS_ITEM = "newsItem"
        fun start(activity: FragmentActivity?, newsItem: NewsItem, options: Bundle?) {
            val intent = Intent(activity, NewsDetailsActivity::class.java)
            intent.putExtra(NEWS_ITEM, newsItem)
            activity?.startActivity(intent,options)
        }
    }
}
