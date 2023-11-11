package com.example.mproject.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mproject.data.repository.ApiRepository
import com.example.mproject.data.response.GameDetailsResponse
import com.example.mproject.data.response.GameListResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApiViewModel @Inject
constructor(
    private val apiRepository: ApiRepository):
    ViewModel(){

    private val _gamesListResponse = MutableLiveData<List<GameListResponse>>()
    private val _loading = MutableLiveData<Boolean>()

    val gamesList: LiveData<List<GameListResponse>>
        get() = _gamesListResponse
    val loading: LiveData<Boolean>
        get() = _loading

    fun loadAllGamesList() = viewModelScope.launch {
        apiRepository.getAllGames().let { response ->
            if (response.isSuccessful) {
                _gamesListResponse.postValue(response.body())
            }
            _loading.postValue(false)
        }
    }

    fun getAllGamesList() = viewModelScope.launch {
        apiRepository.getAllGamesFlow().collect {
            _gamesListResponse.value = it
        }
    }

    private val _gamesResponse = MutableStateFlow<List<GameListResponse>?>(null)
    private val _loadingState = MutableStateFlow<Boolean>(false)

    val gameListResponse = _gamesResponse.asStateFlow()
    val loadingState = _loadingState.asStateFlow()

    fun loadGamesList() = viewModelScope.launch {
        _loadingState.value = true
        apiRepository.getAllGames().let { response ->
            if (response.isSuccessful) {
                _gamesResponse.emit(response.body())
            }
            _loadingState.value = false
        }
    }


}