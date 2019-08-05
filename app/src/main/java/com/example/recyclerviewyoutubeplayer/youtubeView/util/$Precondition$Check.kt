package com.example.recyclerviewyoutubeplayer.youtubeView.util

object `$Precondition$Check` {

    fun checkArgument(expression: Boolean, errorMessage: Any) {
        if (!expression) {
            throw IllegalArgumentException(errorMessage.toString())
        }
    }
}
