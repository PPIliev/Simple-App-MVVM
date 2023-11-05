package com.example.mproject.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mproject.data.repository.ApiRepository
import com.example.mproject.data.response.GameDetailsResponse
import com.example.mproject.data.response.GameListResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApiViewModel @Inject
constructor(
    private val apiRepository: ApiRepository):
    ViewModel(){

    val gamesList = MutableLiveData<List<GameListResponse>>()
    val loading = MutableLiveData<Boolean>()

    fun loadAllGamesList() = viewModelScope.launch {
        val response = apiRepository.getAllGames()
        if (response.isSuccessful) {
            gamesList.postValue(response.body())
        }
        loading.postValue(false)
    }


}