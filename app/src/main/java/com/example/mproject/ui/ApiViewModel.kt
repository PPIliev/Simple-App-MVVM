package com.example.mproject.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mproject.data.repository.ApiRepository
import com.example.mproject.data.response.GameCategoryResponse
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

            //LiveData
//    private val _gamesListResponse = MutableLiveData<List<GameListResponse>>()
//    private val _loading = MutableLiveData<Boolean>()
//
//    val gamesList: LiveData<List<GameListResponse>>
//        get() = _gamesListResponse
//    val loading: LiveData<Boolean>
//        get() = _loading
//
//    fun loadAllGamesList() = viewModelScope.launch {
//        apiRepository.getAllGames().let { response ->
//            if (response.isSuccessful) {
//                _gamesListResponse.postValue(response.body())
//            }
//            _loading.postValue(false)
//        }
//    }
//
//    fun getAllGamesList() = viewModelScope.launch {
//        apiRepository.getAllGamesFlow().collect {
//            _gamesListResponse.value = it
//        }
//    }

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

    fun loadCategoriesList(category: String) = viewModelScope.launch {
        _loadingState.value = true
        apiRepository.getCategories(category).let { response ->
            if (response.isSuccessful) {
                _gamesResponse.emit(response.body())
            }
            _loadingState.value = false
        }
    }

    val categories = listOf(
        GameCategoryResponse("See All"),
        GameCategoryResponse("mmorpg"),
        GameCategoryResponse("shooter"),
        GameCategoryResponse("strategy"),
        GameCategoryResponse("moba"),
        GameCategoryResponse("racing"),
        GameCategoryResponse("sports"),
        GameCategoryResponse("social"),
        GameCategoryResponse("sandbox"),
        GameCategoryResponse("open-world"),
        GameCategoryResponse("survival"),
        GameCategoryResponse("pvp"),
        GameCategoryResponse("pve"),
        GameCategoryResponse("pixel"),
        GameCategoryResponse("voxel"),
        GameCategoryResponse("zombie"),
        GameCategoryResponse("turn-based"),
        GameCategoryResponse("first-person"),
        GameCategoryResponse("third-Person"),
        GameCategoryResponse("top-down"),
        GameCategoryResponse("tank"),
        GameCategoryResponse("space"),
        GameCategoryResponse("sailing"),
        GameCategoryResponse("side-scroller"),
        GameCategoryResponse("superhero"),
        GameCategoryResponse("permadeath"),
        GameCategoryResponse("card"),
        GameCategoryResponse("battle-royale"),
        GameCategoryResponse("mmo"),
        GameCategoryResponse("mmofps"),
        GameCategoryResponse("mmotps"),
        GameCategoryResponse("3d"),
        GameCategoryResponse("2d"),
        GameCategoryResponse("anime"),
        GameCategoryResponse("fantasy"),
        GameCategoryResponse("sci-fi"),
        GameCategoryResponse("fighting"),
        GameCategoryResponse("action-rpg"),
        GameCategoryResponse("action"),
        GameCategoryResponse("military"),
        GameCategoryResponse("martial-arts"),
        GameCategoryResponse("flight"),
        GameCategoryResponse("low-spec"),
        GameCategoryResponse("tower-defense"),
        GameCategoryResponse("horror"),
        GameCategoryResponse("mmorts")
    )


}