package com.kripto.android.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kripto.android.data.database.dao.AppDao
import com.kripto.android.data.database.entities.AppEntity

@Database(
    entities = [AppEntity::class],
    version = 1
)
abstract class KriptoDatabase : RoomDatabase() {

    abstract fun getAppDao(): AppDao

}