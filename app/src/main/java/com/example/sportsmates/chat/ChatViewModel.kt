package com.example.sportsmates.chat

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sportsmates.chat.model.Chat
import com.example.sportsmates.chat.model.MessageModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class ChatViewModel : ViewModel() {
    var retriveChatSuceess = MutableLiveData<List<Chat>?>()
    var retriveChatErorr = MutableLiveData<String?>()
    val currentUserId = FirebaseAuth.getInstance().currentUser.uid
    var listOfChat = MutableLiveData<MutableList<MessageModel>?>()

    fun getUserListOfChat() {
        val listOfUserChat: MutableList<MessageModel>? = mutableListOf()
        val reference: DatabaseReference? =
            FirebaseDatabase.getInstance().getReference("UserChatList").child(currentUserId)
        reference!!.addValueEventListener(object : ValueEventListener {

            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                listOfUserChat!!.clear()
                for (data in snapshot.children) {
                    val chatRow = data.getValue(MessageModel::class.java)
                    listOfUserChat.add(chatRow!!)
                }
                listOfChat.value = listOfUserChat
            }

        })
    }


    fun sendMessage(messageModel: MessageModel) {
        val reference: DatabaseReference? = FirebaseDatabase.getInstance().reference
        val sdf = SimpleDateFormat("hh.mm aa ")
        val currentTime = sdf.format(Date())
        val hashMap: HashMap<String, String>? = HashMap()

        hashMap?.put("receiverId", messageModel.userId!!)
        hashMap?.put("senderId", currentUserId)
        hashMap?.put("message", messageModel.message!!)
        hashMap?.put("time", currentTime.toString())
        reference?.child("Chat")?.child(calculateChatId(messageModel.userId))?.push()
            ?.setValue(hashMap)
        updateUserChatList(messageModel, currentTime.toString())
    }

    private fun updateUserChatList(messageModel: MessageModel, currentTime: String) {
        val reference: DatabaseReference? = FirebaseDatabase.getInstance().reference
        val hashMap: HashMap<String, String>? = HashMap()
        hashMap?.put("message", messageModel.message!!)
        hashMap?.put("time", currentTime)
        hashMap?.put("userId", messageModel.userId!!)
        hashMap?.put("userName", messageModel.userName!!)
        hashMap?.put("userImage", messageModel.userImage.toString())

        FirebaseDatabase.getInstance().getReference("UserChatList")
            .child(currentUserId).child(messageModel.userId!!)
            .addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        dataSnapshot.child("message").ref.setValue(messageModel.message!!)
                        dataSnapshot.child("time").ref.setValue(currentTime)
                    } else {
                        reference?.child("UserChatList")?.child(currentUserId)
                            ?.child(messageModel.userId!!)
                            ?.setValue(hashMap)
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {

                }

            }
            )
    }


    private fun calculateChatId(receiverId: String?): String {
        return if (currentUserId > receiverId!!) {
            receiverId + currentUserId
        } else {
            currentUserId + receiverId
        }
    }

    fun readMessage(receiverId: String?) {
        val listOfChat: MutableList<Chat>? = mutableListOf()
        val reference: DatabaseReference? =
            FirebaseDatabase.getInstance().getReference("Chat").child(calculateChatId(receiverId))
        reference!!.addValueEventListener(object : ValueEventListener {

            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                listOfChat!!.clear()
                for (data in snapshot.children) {
                    val chat = data.getValue(Chat::class.java)
                    listOfChat.add(chat!!)
                }
                retriveChatSuceess.postValue(listOfChat)
            }

        })
    }
}