package com.example.helpers

interface Logger {
    fun e(tag: String, message: String)
}

class DefaultLogger : Logger {
    override fun e(tag: String, message: String) {
        println("[$tag]: $message")
    }
}