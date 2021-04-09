package com.example.sportsmates.discover
import android.app.Activity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.sportsmates.coach.GlideApp
import com.example.sportsmates.databinding.ContactsListItemBinding
import com.example.sportsmates.ext.inflater
import com.example.sportsmates.signUp.data.model.User

class ContactsAdapter(private val userList: List<User>?, private val context: FragmentActivity?) :
    RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {
    var onItemClick: ((User) -> Unit)? = null

    inner class ViewHolder(private val binding: ContactsListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(userItem: User) {
            binding.userName.text = userItem.name
            binding.useraAddress.text = userItem.city
            binding.userAge.text=userItem.age+" "+"years"
            Glide.with(context!!)
                .load(userItem.userImage)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.userImage)
            itemView.setOnClickListener { onItemClick?.invoke(userItem)}

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ContactsListItemBinding.inflate(parent.context.inflater, parent, false))
    }

    override fun getItemCount(): Int = userList!!.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        userList?.get(position)?.let { holder.bind(it) }
    }

}