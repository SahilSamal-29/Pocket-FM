package com.example.pocketfm.data.remote

import com.example.pocketfm.BuildConfig
import com.example.pocketfm.data.model.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {

    @GET("search")
    suspend fun searchPodcasts(
        @Header("X-ListenAPI-Key") apiKey: String = BuildConfig.API_KEY,
        @Query("q") query: String = "story",
        @Query("type") type: String = "episode",
        @Query("page_size") pageSize: Int = 20
    ): ApiResponse

}