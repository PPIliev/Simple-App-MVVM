package com.example.mproject.data.repository

import com.example.mproject.api.ApiService
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private val apiService: ApiService
){
    suspend fun getAllGames() = apiService.getAllGames()

    suspend fun getGameDetails(id: Int) = apiService.getGameDetails(id)

    suspend fun getAllGamesFlow() = flow {
        val result = apiService.getAllGames()
        if (result.isSuccessful) {
            emit(result.body())
        }
    }.flowOn(Dispatchers.IO)


}