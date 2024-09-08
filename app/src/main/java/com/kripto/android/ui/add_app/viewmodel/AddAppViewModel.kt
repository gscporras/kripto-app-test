package com.kripto.android.ui.add_app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kripto.android.domain.model.Application
import com.kripto.android.domain.usecases.AppUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddAppViewModel @Inject constructor(
    private val appUseCase: AppUseCase
) : ViewModel() {

    private val _state = MutableLiveData<AddAppState>(AddAppState.Idle)
    val state: LiveData<AddAppState> = _state

    fun addApp(app: Application) = viewModelScope.launch(Dispatchers.IO) {
        runCatching {
            _state.postValue(AddAppState.Loading)
            appUseCase.addApp(app)
        }.onSuccess {
            _state.postValue(AddAppState.Success)
        }.onFailure {
            _state.postValue(AddAppState.Fail(it.message ?: "Ha ocurrido un error"))
        }
    }
}

sealed class AddAppState {
    data object Idle : AddAppState()
    data object Success : AddAppState()
    data object Loading : AddAppState()
    data class Fail(val message: String) : AddAppState()
}