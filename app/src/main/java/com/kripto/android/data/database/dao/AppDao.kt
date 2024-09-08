package com.kripto.android.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kripto.android.data.database.entities.AppEntity

@Dao
interface AppDao {

    @Query("SELECT * FROM app_table")
    suspend fun getAllApps(): List<AppEntity>?

    @Query("SELECT * FROM app_table WHERE status = 'obsoleto'")
    suspend fun getObsoleteApps(): List<AppEntity>?

    @Query("SELECT * FROM app_table WHERE cpuUsage < 10 AND memoryUsage < 10")
    suspend fun getUnderutilizedApps(): List<AppEntity>?

    @Query("SELECT * FROM app_table WHERE usageFrequency = 'mensual'")
    suspend fun getLowFrequencyApps(): List<AppEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertApp(app: AppEntity)

    @Query("DELETE FROM app_table WHERE id = :appId")
    suspend fun deleteApp(appId: Int)

    @Query("DELETE FROM app_table")
    suspend fun deleteAllApps()
}