package com.kripto.android.ui.home.adapter

import androidx.recyclerview.widget.DiffUtil
import com.kripto.android.domain.model.Application

class ApplicationDiffCallback : DiffUtil.ItemCallback<Application>() {
    override fun areItemsTheSame(oldItem: Application, newItem: Application): Boolean {
        return oldItem.name == newItem.name  // Cambia esta lógica si tienes un ID único
    }

    override fun areContentsTheSame(oldItem: Application, newItem: Application): Boolean {
        return oldItem == newItem
    }
}
