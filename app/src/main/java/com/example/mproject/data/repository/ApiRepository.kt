package com.example.mproject.data.repository

import com.example.mproject.api.ApiService
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class ApiRepository @Inject constructor(
    private val apiService: ApiService
){
    fun getAllGames() = apiService.getAllGames()

    fun getGameDetails(id: Int) = apiService.getGameDetails(id)


}