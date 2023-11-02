package com.example.mproject.data.repository

import com.example.mproject.api.ApiService
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private val apiService: ApiService
){
    suspend fun getAllGames() = apiService.getAllGames()

    suspend fun getGameDetails(id: Int) = apiService.getGameDetails(id)


}