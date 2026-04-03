package com.example.pocketfm.Repository

import android.util.Log
import com.example.pocketfm.RetrofitClient
import com.example.pocketfm.data.mapper.toAudioItem
import com.example.pocketfm.data.model.AudioItem



class AudioRepository {

    suspend fun getAudios(): List<AudioItem> {
        val response = RetrofitClient.api.searchPodcasts()
        Log.d("API_DEBUG", "Size = ${response.results.size}")
        return response.results.map { dto ->
            dto.toAudioItem()
        }
    }
}