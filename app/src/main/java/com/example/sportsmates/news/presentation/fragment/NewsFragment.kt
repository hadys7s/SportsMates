package com.example.sportsmates.news.presentation.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sportsmates.databinding.NewsFragmentBinding
import com.example.sportsmates.networking.Status
import com.example.sportsmates.news.presentation.adapter.SmallNewsAdapter
import com.example.sportsmates.news.presentation.adapter.TallNewsAdapter
import com.example.sportsmates.news.presentation.model.NewsItemUIModel
import com.example.sportsmates.news.presentation.viewmodel.NewsViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class NewsFragment : Fragment() {


    private val viewModel: NewsViewModel by viewModel()
    private var _binding: NewsFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var smallNewsAdapter: SmallNewsAdapter
    private lateinit var tallNewsAdapter: TallNewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = NewsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupObservers()
        setupList()
    }

    private fun setupObservers() {
        viewModel.getTrendingNews().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let { news -> setTrendingNews(news) }
                    }
                    Status.ERROR -> {
                        Log.v("News", it.message.toString())
                    }
                    Status.LOADING -> {

                    }
                }
            }
        })

        viewModel.getRecommendedNews().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let { news -> setForYouNews(news) }
                    }
                    Status.ERROR -> {
                        Log.v("News", it.message.toString())
                    }
                    Status.LOADING -> {

                    }
                }
            }
        })
    }

    private fun setupList() {
        binding.trendingNewsList.run {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
        binding.forYouNewsList.run {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun setTrendingNews(newsList: List<NewsItemUIModel>?) {
        smallNewsAdapter = SmallNewsAdapter(newsList, activity)
        binding.trendingNewsList.adapter = smallNewsAdapter
        smallNewsAdapter.onItemClick = { news ->
        }
    }


    private fun setForYouNews(newsList: List<NewsItemUIModel>?) {
        tallNewsAdapter = TallNewsAdapter(newsList, activity)
        binding.forYouNewsList.adapter = tallNewsAdapter
        tallNewsAdapter.onItemClick = { news ->
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    companion object {
        fun newInstance() = NewsFragment()
    }

}