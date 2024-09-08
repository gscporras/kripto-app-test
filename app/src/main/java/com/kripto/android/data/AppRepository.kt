package com.kripto.android.data

import com.kripto.android.data.database.dao.AppDao
import com.kripto.android.data.database.entities.toDatabase
import com.kripto.android.domain.model.Application
import com.kripto.android.domain.model.toDomain
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val appDao: AppDao
) {

    suspend fun getApps() = appDao.getAllApps()?.map { it.toDomain() } ?: emptyList()

    suspend fun getObsoleteApps() = appDao.getObsoleteApps()?.map { it.toDomain() } ?: emptyList()
    suspend fun getUnderutilizedApps() =
        appDao.getUnderutilizedApps()?.map { it.toDomain() } ?: emptyList()

    suspend fun getLowFrequencyApps() =
        appDao.getLowFrequencyApps()?.map { it.toDomain() } ?: emptyList()

    suspend fun saveApp(app: Application) {
        appDao.insertApp(app = app.toDatabase())
    }

    suspend fun deleteApp(appId: Int) {
        appDao.deleteApp(appId)
    }
}