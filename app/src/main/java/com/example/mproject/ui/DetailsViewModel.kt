package com.example.mproject.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mproject.data.repository.ApiRepository
import com.example.mproject.data.response.GameDetailsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject
constructor(
    private val apiRepository: ApiRepository):
    ViewModel() {


    // Flow
    private val _gamesDetailsResponse = MutableStateFlow<GameDetailsResponse?>(null)
    private val _loadingState = MutableStateFlow<Boolean>(false)

    val gameDetailsResponse = _gamesDetailsResponse.asStateFlow()
    val loadingState = _loadingState.asStateFlow()

    fun loadGameDetailss(id: Int) = viewModelScope.launch {
        _loadingState.value = true

        apiRepository.getGameDetails(id).let { response ->
            if (response.isSuccessful) {
                _gamesDetailsResponse.emit(response.body())
            }
            _loadingState
        }
        _loadingState.value = false

    }

    // LiveData
//    private val _gamesDetailsListResponse = MutableLiveData<GameDetailsResponse>()
//    private val _loading = MutableLiveData<Boolean>()
//    val gamesDetailsList: LiveData<GameDetailsResponse>
//            get() = _gamesDetailsListResponse
//    val loading: LiveData<Boolean>
//        get() = _loading
//
//    fun loadGameDetails(id: Int) = viewModelScope.launch {
//        _loading.postValue(true)
//
//        apiRepository.getGameDetails(id).let { response ->
//            if (response.isSuccessful) {
//                _gamesDetailsListResponse.postValue(response.body())
//            }
//            _loading.postValue(false)
//        }
//    }

}