package com.example.sportsmates.chat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sportsmates.UserPreferences
import com.example.sportsmates.chat.model.Chat
import com.example.sportsmates.chat.model.MessageModel
import com.example.sportsmates.ext.getCurrentTime
import com.example.sportsmates.ext.getCurrentUserID
import com.google.firebase.database.*

class ChatViewModel(private val userPreferences: UserPreferences) : ViewModel() {
    var retriveChatSuceess = MutableLiveData<List<Chat>?>()
    var retriveChatErorr = MutableLiveData<String?>()
    var listOfChat = MutableLiveData<MutableList<MessageModel>?>()
    val reference: DatabaseReference = FirebaseDatabase.getInstance().reference

    fun getUserListOfChat() {
        val listOfUserChat: MutableList<MessageModel>? = mutableListOf()
        val reference: DatabaseReference? =
            FirebaseDatabase.getInstance().getReference("UserChatList").child(getCurrentUserID())
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


    fun sendMessage(
        messageModel: MessageModel
    ) {
        val reference: DatabaseReference? = FirebaseDatabase.getInstance().reference
        val hashMap: HashMap<String, String>? = HashMap()
        hashMap?.put("receiverId", messageModel.userId!!)
        hashMap?.put("senderId", getCurrentUserID())
        hashMap?.put("message", messageModel.message!!)
        hashMap?.put("time", getCurrentTime())
        reference?.child("Chat")?.child(calculateChatId(messageModel.userId))?.push()
            ?.setValue(hashMap)
        updateUserChatList(messageModel)
        updateOtherUserChatList(messageModel)
    }

    private fun updateUserChatList(messageModel: MessageModel) {
        val hashMap: HashMap<String, String> = HashMap()
        hashMap["message"] = messageModel.message!!
        hashMap["time"] = getCurrentTime()
        hashMap["userId"] = messageModel.userId!!
        hashMap["userName"] = messageModel.userName!!
        hashMap["userImage"] = messageModel.userImage.toString()

        FirebaseDatabase.getInstance().getReference("UserChatList")
            .child(getCurrentUserID()).child(messageModel.userId!!)
            .addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        dataSnapshot.child("message").ref.setValue(messageModel.message!!)
                        dataSnapshot.child("time").ref.setValue(getCurrentTime())
                    } else {
                        reference.child("UserChatList").child(getCurrentUserID())
                            .child(messageModel.userId!!).setValue(hashMap)
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                }
            })
    }

    private fun updateOtherUserChatList(messageModel: MessageModel) {
        val receiverValues: HashMap<String, String> = HashMap()
        receiverValues["message"] = messageModel.message!!
        receiverValues["time"] = getCurrentTime()
        receiverValues["userId"] = getCurrentUserID()
        receiverValues["userName"] = userPreferences.name!!
        receiverValues["userImage"] = userPreferences.image!!

        FirebaseDatabase.getInstance().getReference("UserChatList")
            .child(messageModel.userId!!).child(getCurrentUserID())
            .addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        dataSnapshot.child("message").ref.setValue(messageModel.message!!)
                        dataSnapshot.child("time").ref.setValue(getCurrentTime())
                    } else {
                        reference.child("UserChatList").child(messageModel.userId!!)
                            .child(getCurrentUserID()).setValue(receiverValues)
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {

                }

            }
            )
    }


    private fun calculateChatId(receiverId: String?): String {
        return if (getCurrentUserID() > receiverId!!) {
            receiverId + getCurrentUserID()
        } else {
            getCurrentUserID() + receiverId
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