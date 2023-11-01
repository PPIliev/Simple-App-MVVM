package com.example.mproject.api

import com.example.mproject.data.response.GameDetailsResponse
import com.example.mproject.data.response.GameListResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("games")
    fun getAllGames(): Call<List<GameListResponse>>

    @GET("game")
   suspend fun getGameDetails(@Query("id") id: Int): Response<GameDetailsResponse>


}