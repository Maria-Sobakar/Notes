package com.marias.android.notes.utils

class Event<T>(private val content: T) {
    private var hasBeenHandled = false

    fun getIdIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }
}