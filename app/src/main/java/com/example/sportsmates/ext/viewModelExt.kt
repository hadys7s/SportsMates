package com.example.sportsmates.ext

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.*

fun ViewModel.getCurrentUserID(): String = FirebaseAuth.getInstance().currentUser.uid
fun ViewModel.getCurrentTime(): String = SimpleDateFormat("hh.mm aa ").format(Date())
