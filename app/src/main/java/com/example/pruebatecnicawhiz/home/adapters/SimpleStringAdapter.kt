package com.example.pruebatecnicawhiz.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pruebatecnicawhiz.home.data.model.Pokemon

class SimpleStringAdapter(private val onClickListener: (String) -> Unit) : RecyclerView.Adapter<SimpleStringAdapter.ViewHolder>() {

    private val items = mutableListOf<String>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(android.R.id.text1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.textView.text = item
        holder.itemView.setOnClickListener { onClickListener(item) }
    }

    fun updateData(newData: List<String>) {
        items.clear()
        items.addAll(newData)
        notifyDataSetChanged()
    }

    override fun getItemCount() = items.size
}
