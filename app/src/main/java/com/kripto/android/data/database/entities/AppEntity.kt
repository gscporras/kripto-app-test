package com.kripto.android.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kripto.android.domain.model.Application
import kotlin.random.Random

@Entity(tableName = "app_table")
data class AppEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "cpuUsage") val cpuUsage: Float,
    @ColumnInfo(name = "memoryUsage") val memoryUsage: Float,
    @ColumnInfo(name = "usageFrequency") val usageFrequency: String,
    @ColumnInfo(name = "status") val status: String,
    @ColumnInfo(name = "functionality") val functionality: String,
    @ColumnInfo(name = "userCount") val userCount: Int
)

fun Application.toDatabase() = AppEntity(
    name = name,
    cpuUsage = cpuUsage,
    memoryUsage = memoryUsage,
    usageFrequency = usageFrequency,
    status = status,
    functionality = functionality,
    userCount = userCount
)
