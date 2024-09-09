package com.kripto.android.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kripto.android.R
import com.kripto.android.databinding.ItemAppBinding
import com.kripto.android.domain.model.Application

class AppsAdapter(
    private val onClick: (Application) -> Unit
) : ListAdapter<Application, AppsAdapter.AppViewHolder>(ApplicationDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_app, parent, false)
        return AppViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private var fullList = listOf<Application>()

    override fun submitList(list: List<Application>?) {
        if (fullList.isEmpty()) {
            fullList = list ?: emptyList()
        }
        super.submitList(list)
    }

    fun filter(query: String) {
        val filteredList = if (query.isEmpty()) {
            fullList  // Restaurar lista completa cuando la búsqueda esté vacía
        } else {
            fullList.filter { it.name.contains(query, ignoreCase = true) }
        }
        super.submitList(filteredList)
    }

    inner class AppViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ItemAppBinding.bind(view)

        fun bind(app: Application) = with(binding) {
            tvAppName.text = app.name
            tvAppMemoryUsage.text = "${app.memoryUsage}MB"
            itemView.setOnClickListener { onClick(app) }
        }
    }
}