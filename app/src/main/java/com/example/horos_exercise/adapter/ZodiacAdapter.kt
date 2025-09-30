package com.example.horos_exercise.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.horos_exercise.R
import com.example.horos_exercise.ZodiacList

class ZodiacAdapter(var items: List<ZodiacList>, val onClickListener: (Int) -> Unit): RecyclerView.Adapter<ZodiacViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ZodiacViewHolder {
        val vista = LayoutInflater.from(parent.context)
        return ZodiacViewHolder(vista.inflate(R.layout.items_zodiac, parent, false))
    }

    override fun onBindViewHolder(
        holder: ZodiacViewHolder,
        position: Int
    ) {
        val item = items[position]
        holder.render(item)
        holder.itemView.setOnClickListener {
            onClickListener(position)
        }
    }

    override fun getItemCount(): Int {
        return items.size // -> tamaño de la lista
    }

    fun updateItems(items: List<ZodiacList>) {
        this.items = items
        notifyDataSetChanged()
    }
}