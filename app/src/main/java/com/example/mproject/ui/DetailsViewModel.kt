package com.example.mproject.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mproject.data.repository.ApiRepository
import com.example.mproject.data.response.GameDetailsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject
constructor(
    private val apiRepository: ApiRepository):
    ViewModel() {

    val gamesDetailsList = MutableLiveData<GameDetailsResponse>()
    val loading = MutableLiveData<Boolean>()
    fun loadGameDetails(id: Int) = viewModelScope.launch {
        loading.postValue(true)
        val response = apiRepository.getGameDetails(id)
        if (response.isSuccessful) {
            gamesDetailsList.postValue(response.body())
        }
        loading.postValue(false)
    }

}