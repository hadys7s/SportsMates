package com.example.sportsmates.chat

import android.content.Context
import android.text.Editable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.example.sportsmates.chat.adapters.ChatAdapter
import com.example.sportsmates.chat.model.Chat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ChatViewModel : ViewModel() {
    var retriveChatSuceess = MutableLiveData<List<Chat>?>()
    var retriveChatErorr = MutableLiveData<String?>()

    fun sendMessage(receiverId: String?, message: String?) {
        val firebaseUser = FirebaseAuth.getInstance().currentUser
        val reference: DatabaseReference? = FirebaseDatabase.getInstance().reference
        val hashMap: HashMap<String, String>? = HashMap()
        hashMap?.put("senderId", firebaseUser!!.uid)
        hashMap?.put("receiverId", receiverId!!)
        hashMap?.put("message", message!!)
        reference?.child("Chat")?.push()?.setValue(hashMap)
    }

    fun readMessage(receiverId: String?,context: Context,recyclerView: RecyclerView) {
        val listOfChat: MutableList<Chat>? = mutableListOf()
        val firebaseUser = FirebaseAuth.getInstance().currentUser
        val reference: DatabaseReference? = FirebaseDatabase.getInstance().getReference("Chat")
        reference!!.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for (data in snapshot.children) {
                    val chat = data.getValue(Chat::class.java)
                      if (chat!!.senderId.equals(firebaseUser!!.uid) && chat.receiverId.equals(receiverId)
                          ||chat.senderId.equals(receiverId) && chat.receiverId.equals(firebaseUser.uid)){
                          listOfChat!!.add(chat)
                      }
                }
                val adapter=ChatAdapter(listOfChat,context)
                recyclerView.adapter=adapter
            }

        })
    }
}