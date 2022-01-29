package com.example.sportsmates.ext

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object Events {
    private val _events = MutableSharedFlow<Event>()
    val event get() = _events.asSharedFlow()

    suspend fun invokeEvent(event: Event) {
        _events.emit(event)
    }
}

interface Event