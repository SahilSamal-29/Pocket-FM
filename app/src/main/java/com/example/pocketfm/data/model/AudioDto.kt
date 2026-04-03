package com.example.pocketfm.data.model

data class AudioDto(
    val id: String,
    val title_original: String,
    val description_original: String,
    val image: String,
    val audio: String,
    val audio_length_sec: Int
)