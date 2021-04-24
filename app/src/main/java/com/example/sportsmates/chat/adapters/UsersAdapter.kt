package com.example.sportsmates.chat.adapters

import android.content.Context
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.sportsmates.chat.model.MessageModel
import com.example.sportsmates.databinding.ChatUsersListItemBinding
import com.example.sportsmates.ext.inflater

class UsersAdapter(private val userList: List<MessageModel>?, private val context: Context?) :
    RecyclerView.Adapter<UsersAdapter.ViewHolder>() {
    var onItemClick: ((MessageModel, ImageView) -> Unit)? = null

    inner class ViewHolder(private val binding: ChatUsersListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(messageItem: MessageModel) {
            binding.userName.text = messageItem.userName
            binding.lastImage.text = messageItem.message
            Glide.with(context!!)
                .load(messageItem.userImage)
                .circleCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.userImage)
            itemView.setOnClickListener { onItemClick?.invoke(messageItem, binding.userImage) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ChatUsersListItemBinding.inflate(parent.context.inflater, parent, false))
    }

    override fun getItemCount(): Int = userList!!.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        userList?.get(position)?.let { holder.bind(it) }
    }
}