package com.example.horos_exercise.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.horos_exercise.R
import com.example.horos_exercise.SessionManager
import com.example.horos_exercise.ZodiacList
import org.w3c.dom.Text

class ZodiacViewHolder (view: View): RecyclerView.ViewHolder(view){

    val textName: TextView = view.findViewById<TextView>(R.id.ivName)
    val textDate: TextView = view.findViewById<TextView>(R.id.ivDate)
    val iconImg: ImageView = view.findViewById<ImageView>(R.id.ivIcon)
    val favoriteImg: ImageView = view.findViewById<ImageView>(R.id.ivFavorite)

    fun render(zodiacList: ZodiacList){
        textName.setText(zodiacList.name)
        textDate.setText(zodiacList.dates)
        iconImg.setImageResource(zodiacList.icon)

        val session = SessionManager(itemView.context)
        if (session.isFavorite(zodiacList.id)) {
            favoriteImg.visibility = View.VISIBLE
        } else {
            favoriteImg.visibility = View.GONE
        }

    }
}