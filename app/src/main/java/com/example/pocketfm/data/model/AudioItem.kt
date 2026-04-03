package com.example.pocketfm.data.model

data class AudioItem(
    val id: String,
    val title: String,
    val description: String,
    val image: String,
    val audioUrl: String,
    val duration: Int
)