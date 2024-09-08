package com.kripto.android.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kripto.android.R
import com.kripto.android.databinding.ItemAppBinding
import com.kripto.android.domain.model.Application

class AppsAdapter(
    private val list: MutableList<Application> = mutableListOf(),
    private val onClick: (Application) -> Unit
) : RecyclerView.Adapter<AppsAdapter.AppViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_app, parent, false)
        return AppViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun addAll(list: List<Application>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
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