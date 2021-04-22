package com.example.sportsmates.chat.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sportsmates.R
import com.example.sportsmates.chat.model.Chat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class ChatAdapter(private val listOfChat: List<Chat>?, private val context: Context?) :
    RecyclerView.Adapter<ChatAdapter.ViewHolder>() {
    private val MESSAGE_TYPE_LEFT = 0
    private val MESSAGE_TYPE_RIGHT = 1
    var firebaseUser: FirebaseUser? = null

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val message: TextView = view.findViewById(R.id.tv_message)
        val time: TextView = view.findViewById(R.id.tv_time)
        fun bind(chat: Chat) {
            message.text = chat.message
            time.text=chat.time
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatAdapter.ViewHolder {
        if (viewType == MESSAGE_TYPE_LEFT) {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.chat_item_left, parent, false)
            return ViewHolder(view)
        } else {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.chat_item_right, parent, false)
            return ViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return listOfChat!!.size
    }

    override fun onBindViewHolder(holder: ChatAdapter.ViewHolder, position: Int) {
        listOfChat?.get(position)?.let { holder.bind(it) }
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