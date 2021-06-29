package com.example.sportsmates.chatbot

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sportsmates.R
import com.example.sportsmates.chat.adapters.ChatAdapter
import com.example.sportsmates.chatbot.model.FoodItemUIModel
import com.example.sportsmates.databinding.ActivityMessagesBinding
import com.example.sportsmates.ext.changeStatusBarColor
import com.example.sportsmates.networking.Status
import org.koin.android.viewmodel.ext.android.viewModel

class ChatBotActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMessagesBinding
    private val viewModel: ChatBotViewModel by viewModel()
    private lateinit var adapter: ChatAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessagesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        changeStatusBarColor(R.color.main_green)
        setupAdapter()
        setupList()
        attachClickListeners()
        setupAdapter()
        setMessage("Hey, How can I assist you ?")
    }

    private fun setupList() {
        binding.messageList.run {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

    }

    private fun setMessage(chatBotResponse: String, foodImageUrl: String? = "") {
        foodImageUrl?.let { adapter.addResponse(chatBotResponse, it) }
        adapter.notifyDataSetChanged()
        binding.messageList.smoothScrollToPosition(adapter.itemCount - 1)
    }

    private fun setupAdapter() {
        adapter = ChatAdapter(context = this)
        binding.messageList.adapter = adapter
    }

    private fun attachClickListeners() {
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
        binding.sendBtn.setOnClickListener {
            setupQuery()
            binding.edMessage.text.clear()
        }
    }

    fun setupQuery() {
        adapter.addQuery(binding.edMessage.text.toString())
        adapter.notifyDataSetChanged()
        binding.messageList.smoothScrollToPosition(adapter.itemCount - 1)
        sendQuery(binding.edMessage.text.toString())
    }

    private fun sendQuery(query: String) {
        viewModel.getNutroInfo(query).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let { nutro ->
                            setMessage(mapToUiModel(nutro)[0], nutro[0].image)
                        }
                    }
                    Status.ERROR -> {
                        setMessage("Sorry I couldn't recognize the food")
                    }
                    Status.LOADING -> {

                    }
                }
            }
        })

    }

    private fun mapToUiModel(food: List<FoodItemUIModel>): List<String> {
        return food.map { food ->
            ("The number of " + food.foodName + " is " + food.servingQuantity + "\n" + "The total Number of Calories is " + food.calories + " Kcal" + "\n" + "Each " + food.servingUnit + " of " + food.foodName + " Contains " + (food.calories?.div(
                food.servingQuantity
            )) + " Kcal" + '\n' + "The total Number of Protein = " +
                    food.protein + "G\n" + " The total Number of Carbohydrate = " + food.carbohydrates + "G\n" + "The total Number of Sugars = " +
                    food.sugar)
        }
    }
}