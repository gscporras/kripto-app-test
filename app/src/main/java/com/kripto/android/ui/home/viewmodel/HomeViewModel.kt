package com.kripto.android.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kripto.android.data.database.entities.AppEntity
import com.kripto.android.domain.model.Application
import com.kripto.android.domain.usecases.AppUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val appUseCase: AppUseCase
) : ViewModel() {

    private val _state = MutableLiveData<HomeState>(HomeState.Idle)
    val state: LiveData<HomeState> = _state

    fun getApps() = viewModelScope.launch(Dispatchers.IO) {
        runCatching {
            _state.postValue(HomeState.Loading)
            appUseCase.getApps()
        }.onSuccess { apps ->
            _state.postValue(HomeState.Success(apps))
        }.onFailure {
            _state.postValue(HomeState.Fail(it.message ?: "Ha ocurrido un error"))
        }
    }
}

sealed class HomeState {
    data object Idle : HomeState()
    data object Loading : HomeState()
    data class Fail(val message: String) : HomeState()
    data class Success(val list: List<Application>) : HomeState()
}