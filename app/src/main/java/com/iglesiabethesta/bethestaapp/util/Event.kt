package com.iglesiabethesta.bethestaapp.util

/*clase generica para eventos en firebase que
* Cree*/
open class Event<out T> (private val content: T){

    var hasBeenHandled = false
        private set

    fun getContentIfNotHandle(): T? {
        return if (hasBeenHandled) {
            null
        }else {
            hasBeenHandled = true
            content
        }
    }

    fun getContent(): T? {
        return content
    }

    fun peekContent(): T = content

}