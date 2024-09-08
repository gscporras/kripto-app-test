package com.kripto.android.ui.detail.viewmodel

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
class AppDetailViewModel @Inject constructor(
    private val appUseCase: AppUseCase
) : ViewModel() {

    private val _state = MutableLiveData<AppDetailState>(AppDetailState.Idle)
    val state: LiveData<AppDetailState> = _state

    fun deleteApp(appId: Int) = viewModelScope.launch(Dispatchers.IO) {
        runCatching {
            _state.postValue(AppDetailState.Loading)
            appUseCase.deleteApp(appId)
        }.onSuccess {
            _state.postValue(AppDetailState.Success)
        }.onFailure {
            _state.postValue(AppDetailState.Fail(it.message ?: "Ha ocurrido un error"))
        }
    }
}

sealed class AppDetailState {
    data object Idle : AppDetailState()
    data object Loading : AppDetailState()
    data object Success : AppDetailState()
    data class Fail(val message: String) : AppDetailState()
}