package com.kripto.android.domain.model

import com.kripto.android.data.database.entities.AppEntity
import java.io.Serializable

data class Application(
    val id: Int = 0,
    val name: String,
    val cpuUsage: Float,
    val memoryUsage: Float,
    val usageFrequency: String,  // Ej: "diaria", "semanal", "mensual"
    val status: String,  // Ej: "activo", "obsoleto"
    val functionality: String,  // Ej: "CRM", "gestión de inventario"
    val userCount: Int
): Serializable

fun AppEntity.toDomain() = Application(
    id = id,
    name = name,
    cpuUsage = cpuUsage,
    memoryUsage = memoryUsage,
    usageFrequency = usageFrequency,
    status = status,
    functionality = functionality,
    userCount = userCount
)
