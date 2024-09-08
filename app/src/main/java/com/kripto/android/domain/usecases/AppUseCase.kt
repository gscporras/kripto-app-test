package com.kripto.android.domain.usecases

import androidx.lifecycle.LiveData
import com.kripto.android.data.AppRepository
import com.kripto.android.domain.model.Application
import javax.inject.Inject

class AppUseCase @Inject constructor(
    private val repository: AppRepository
) {

    suspend fun getApps() = repository.getApps()
    suspend fun getRecommendations(): List<String> {
        val recommendations = mutableListOf<String>()

        // Obtener aplicaciones obsoletas
        val obsoleteApps = repository.getObsoleteApps()
        obsoleteApps.forEach { app ->
            recommendations.add("Considera eliminar la aplicación ${app.name} ya que está marcada como obsoleta.")
        }

        // Obtener aplicaciones subutilizadas
        val underutilizedApps = repository.getUnderutilizedApps()
        underutilizedApps.forEach { app ->
            recommendations.add("La aplicación ${app.name} está subutilizada. Considera reasignar recursos.")
        }

        // Obtener aplicaciones de baja frecuencia de uso
        val lowFrequencyApps = repository.getLowFrequencyApps()
        lowFrequencyApps.forEach { app ->
            recommendations.add("La aplicación ${app.name} tiene un uso poco frecuente. Considera si sigue siendo necesaria.")
        }

        // Si no hay recomendaciones específicas
        if (recommendations.isEmpty()) {
            recommendations.add("No se han encontrado recomendaciones de optimización en este momento.")
        }

        return recommendations
    }
    suspend fun addApp(app: Application) = repository.saveApp(app)
    suspend fun deleteApp(appId: Int) = repository.deleteApp(appId)
}