package com.example.sportsmates.chat.adapters
import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sportsmates.chat.model.Chat
import com.example.sportsmates.databinding.ChatItemLeftBinding
import com.example.sportsmates.databinding.ChatItemRightBinding
import com.example.sportsmates.ext.inflater
import com.example.sportsmates.utils.Constants.MESSAGE_TYPE_LEFT
import com.example.sportsmates.utils.Constants.MESSAGE_TYPE_RIGHT
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class ChatAdapter(private val listOfChat: List<Chat>?, private val context: Context?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var firebaseUser: FirebaseUser? = null

    inner class ViewHolder1(private val binding: ChatItemLeftBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(chat: Chat) {
            binding.tvMessage.text = chat.message
            binding.tvTime.text = chat.time
        }
    }

    inner class ViewHolder2(private val binding: ChatItemRightBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(chat: Chat) {
            binding.tvMessage.text = chat.message
            binding.tvTime.text = chat.time
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == MESSAGE_TYPE_LEFT) {
            ViewHolder1(ChatItemLeftBinding.inflate(parent.context.inflater, parent, false))
        } else {
            ViewHolder2(ChatItemRightBinding.inflate(parent.context.inflater, parent, false))
        }
    }

    override fun getItemCount(): Int {
        return listOfChat!!.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType== MESSAGE_TYPE_LEFT) {
            listOfChat?.get(position)?.let { (holder as ViewHolder1).bind(it) }
        }else
        {
            listOfChat?.get(position)?.let { (holder as ViewHolder2).bind(it) }
        }
    }

    override fun getItemViewType(position: Int): Int {
        firebaseUser = FirebaseAuth.getInstance().currentUser
        if (listOfChat?.get(position)?.senderId == firebaseUser!!.uid) {
            return MESSAGE_TYPE_RIGHT
        } else {
            return MESSAGE_TYPE_LEFT
        }
    }
}