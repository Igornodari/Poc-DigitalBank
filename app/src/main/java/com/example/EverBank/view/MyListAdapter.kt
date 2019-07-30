package com.example.EverBank.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.EverBank.utils.Tarefas
import com.example.poceveris_beacon.R


class MyListAdapter(val models: ArrayList<Tarefas>) : RecyclerView.Adapter<MyListAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindItems(models[position])
    }

    override fun getItemCount(): Int {
        return models.size
    }

    @Suppress("CAST_NEVER_SUCCEEDS")
    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(model: Tarefas) {
            val txtTitle = itemView.findViewById(R.id.card_view_image_title) as TextView
            val txtDate = itemView.findViewById<TextView>(R.id.card_view_image) as ImageView
            txtTitle.text = model.name
            txtDate.setImageResource(model.url)
        }
    }
}




