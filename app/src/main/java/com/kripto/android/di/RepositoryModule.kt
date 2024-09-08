package com.kripto.android.di

import com.kripto.android.data.AppRepository
import com.kripto.android.data.database.dao.AppDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideAppRepository(
        appDao: AppDao
    ): AppRepository {
        return AppRepository(appDao)
    }
}