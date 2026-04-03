package com.example.pocketfm.data.remote

import com.example.pocketfm.data.model.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {

    @GET("search")
    @Headers("X-ListenAPI-Key: f0ad35fce99041c4b9fe0c97cea3f7c6")
    suspend fun searchPodcasts(
        @Query("q") query: String = "story",
        @Query("type") type: String = "episode",
        @Query("page_size") pageSize: Int = 20
    ): ApiResponse

}