package com.example.restaurantreview

open class Event<out T>(private val content: T) {

    @Suppress("MemberVisibilityCanBePrivate")
    var hasBeenHandle = false
        private set

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandle){
            null
        } else {
            hasBeenHandle = true
            content
        }
    }

    fun peekContent(): T = content
}