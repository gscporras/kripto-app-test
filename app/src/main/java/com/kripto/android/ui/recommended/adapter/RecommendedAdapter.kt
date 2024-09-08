package com.kripto.android.ui.recommended.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kripto.android.R
import com.kripto.android.databinding.HeaderItemLayoutBinding
import com.kripto.android.databinding.ImageItemLayoutBinding
import com.kripto.android.databinding.TextItemLayoutBinding

class RecommendedAdapter(
    private val items: MutableList<String> = mutableListOf()
) : RecyclerView.Adapter<RecommendedAdapter.TextViewHolder>() {

    inner class TextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = TextItemLayoutBinding.bind(itemView)

        fun bind(item: String) = with(binding) {
            // Bind data to views
            textView.text = item
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecommendedAdapter.TextViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.text_item_layout, parent, false)
        return TextViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecommendedAdapter.TextViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    fun addAll(recommendedList: List<String>) {
        this.items.clear()
        this.items.addAll(recommendedList)
        notifyDataSetChanged()
    }
}