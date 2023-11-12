package com.example.mproject.api

import com.example.mproject.data.response.GameCategoryResponse
import com.example.mproject.data.response.GameDetailsResponse
import com.example.mproject.data.response.GameListResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("games")
    suspend fun getAllGames(): Response<List<GameListResponse>>

    @GET("game")
    suspend fun getGameDetails(@Query("id") id: Int): Response<GameDetailsResponse>

    @GET("games")
    suspend fun getGameCategory(@Query("category") category: String): Response<List<GameListResponse>>


}