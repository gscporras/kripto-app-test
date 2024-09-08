package com.kripto.android.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.kripto.android.domain.model.Application
import com.kripto.android.domain.usecases.AppUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    appUseCase: AppUseCase
) : ViewModel() {

    val apps: LiveData<List<Application>> = appUseCase.getApps()
}