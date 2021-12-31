package com.codingwithmitch.core

class Logger(
    private val tag: String,
    private val isDebug: Boolean = true
) {

    fun log(message: String) {
        if (!isDebug) {
            // Log into PROD ( Firebase )
        } else {
            printLogD(tag, message)
        }
    }

    companion object Factory {
        fun buildDebug(tag: String): Logger {
            return Logger(
                tag = tag,
                isDebug = true
            )
        }
        fun buildRelease(tag: String): Logger {
            return Logger(
                tag = tag,
                isDebug = false
            )
        }
    }

}

fun printLogD(tag: String, message: String) {
    println("$tag: $message")
}
