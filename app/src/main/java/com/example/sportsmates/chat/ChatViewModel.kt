package com.example.sportsmates.chat

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportsmates.chat.model.Chat
import com.example.sportsmates.chat.model.MessageUIModel
import com.example.sportsmates.chat.model.toMessageModel
import com.example.sportsmates.chat.model.toMessageUiModel
import com.example.sportsmates.signUp.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class ChatViewModel : ViewModel() {
    var retriveChatSuceess = MutableLiveData<List<Chat>?>()
    var retriveChatErorr = MutableLiveData<String?>()
    val firebaseUserId = FirebaseAuth.getInstance().currentUser.uid
    val listOfMessagesHistory: MutableList<MessageUIModel>? = mutableListOf()
    val listOfChat: MutableList<MessageUIModel>? = mutableListOf()

    fun sendMessage(receiverId: String?, message: String?) {
        val reference: DatabaseReference? = FirebaseDatabase.getInstance().reference
        val sdf = SimpleDateFormat("hh.mm aa ")
        val currentTime = sdf.format(Date())
        val hashMap: HashMap<String, String>? = HashMap()

        hashMap?.put("receiverId", receiverId!!)
        hashMap?.put("senderId", receiverId!!)
        hashMap?.put("message", message!!)
        hashMap?.put("time", currentTime.toString())
        reference?.child("Chat")?.push()?.child(calculateChatId(receiverId))?.setValue(hashMap)
    }

    init {
        viewModelScope.launch {
            val users = getUserContactsData(getUserChatHistory())
            users?.forEach { user ->
                user.userImage = retrievePhoto(user.userId)
            }
            print("hello")
        }
    }

    fun calculateChatId(receiverId: String?): String {
        return if (firebaseUserId.toInt() > receiverId!!.toInt()) {
            receiverId + firebaseUserId
        } else {
            firebaseUserId + receiverId
        }
    }

    private suspend fun getUserChatHistory(): MutableList<MessageUIModel>? {
        FirebaseDatabase.getInstance().getReference("Chat").get().addOnSuccessListener { chat ->
            val chats = chat.children
            for (data in chats) {
                val chat = data.getValue(Chat::class.java)
                if (listOfMessagesHistory?.size!! > 0) {
                    listOfMessagesHistory?.forEach { message ->
                        if (!(message.userId == chat?.senderId || message.userId == chat?.receiverId)) {
                            if (chat!!.senderId!! == firebaseUserId) {
                                listOfMessagesHistory.add(chat.toMessageModel(chat.receiverId!!))
                            } else if (chat!!.receiverId!! == firebaseUserId) {
                                listOfMessagesHistory.add(chat.toMessageModel(chat.senderId!!))
                            }
                        }
                    }
                } else {
                    if (chat!!.senderId!! == firebaseUserId) {
                        listOfMessagesHistory.add(chat.toMessageModel(chat.receiverId!!))
                    } else if (chat!!.receiverId!! == firebaseUserId) {
                        listOfMessagesHistory.add(chat.toMessageModel(chat.senderId!!))
                    }
                }
            }
        }.await()
        return listOfMessagesHistory
    }

    private suspend fun getUserContactsData(listOfMessages: MutableList<MessageUIModel>?): MutableList<MessageUIModel>? {
        listOfMessages?.forEach { message ->
            FirebaseDatabase.getInstance().getReference("Users")
                .child(message.userId!!).get().addOnCompleteListener { task ->
                    val contactUser: User? = task.result?.getValue(User::class.java)
                    listOfChat?.add(
                        message.toMessageUiModel(
                            userName = contactUser?.name!!
                        )
                    )

                }.await()
        }
        return listOfChat
    }

    private suspend fun retrievePhoto(userId: String?): Uri? {
        var imageUrl: Uri? = null
        val storageReference =
            FirebaseStorage.getInstance().reference.child("images/$userId")
        storageReference.downloadUrl.addOnSuccessListener { imageUri ->
            imageUrl = imageUri
        }
            .addOnFailureListener {

            }.await()
        return imageUrl
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