package com.kripto.android.ui.recommended.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kripto.android.domain.usecases.AppUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecommendedViewModel @Inject constructor(
    private val appUseCase: AppUseCase
) : ViewModel() {

    private val _state = MutableLiveData<RecommendedState>(RecommendedState.Idle)
    val state: LiveData<RecommendedState> = _state

    fun getRecommended() = viewModelScope.launch(Dispatchers.IO) {
        runCatching {
            _state.postValue(RecommendedState.Loading)
            appUseCase.getRecommendations()
        }.onSuccess {
            _state.postValue(RecommendedState.Success(it))
        }.onFailure {
            _state.postValue(RecommendedState.Fail(it.message ?: "Ha ocurrido un error"))
        }
    }
}

sealed class RecommendedState {
    data object Idle : RecommendedState()
    data object Loading : RecommendedState()
    data class Fail(val message: String) : RecommendedState()
    data class Success(val recommendedList: List<String>) : RecommendedState()
}