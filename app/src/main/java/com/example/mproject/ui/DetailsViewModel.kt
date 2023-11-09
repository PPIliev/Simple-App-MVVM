package com.example.mproject.ui

import androidx.lifecycle.LiveData
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

    private val _gamesDetailsListResponse = MutableLiveData<GameDetailsResponse>()
    private val _loading = MutableLiveData<Boolean>()

    val gamesDetailsList: LiveData<GameDetailsResponse>
            get() = _gamesDetailsListResponse
    val loading: LiveData<Boolean>
        get() = _loading

    fun loadGameDetails(id: Int) = viewModelScope.launch {
        _loading.postValue(true)

        apiRepository.getGameDetails(id).let { response ->
            if (response.isSuccessful) {
                _gamesDetailsListResponse.postValue(response.body())
            }
            _loading.postValue(false)
        }
    }

}