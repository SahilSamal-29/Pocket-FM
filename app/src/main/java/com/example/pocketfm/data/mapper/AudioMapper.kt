package com.example.pocketfm.data.mapper

import com.example.pocketfm.data.model.AudioDto
import com.example.pocketfm.data.model.AudioItem


fun AudioDto.toAudioItem(): AudioItem {
    return AudioItem(
        id = id,
        title = title_original,
        description = description_original,
        image = image,
        audioUrl = audio,
        duration = audio_length_sec
    )
}