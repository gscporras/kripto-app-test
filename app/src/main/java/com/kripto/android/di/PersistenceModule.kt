package com.kripto.android.di

import android.content.Context
import androidx.room.Room
import com.kripto.android.data.database.KriptoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {

    private const val KRIPTO_DATABASE_NAME = "kripto_database"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context = context, KriptoDatabase::class.java, name = KRIPTO_DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideAppDao(db: KriptoDatabase) = db.getAppDao()
}