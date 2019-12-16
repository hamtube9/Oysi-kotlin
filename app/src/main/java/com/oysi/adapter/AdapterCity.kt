package com.oysi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oysi.R
import com.oysi.model.city.Data
import kotlinx.android.synthetic.main.item_city.view.*

class AdapterCity(private var context:Context,private var list : ArrayList<Data>,private var itemSelect : onItemSelect) : RecyclerView.Adapter<AdapterCity.ViewHolder>() {
    interface onItemSelect{
        fun onclickListener(position: Int)
    }

    class ViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterCity.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_city,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: AdapterCity.ViewHolder, position: Int) {
        holder.itemView.itemcity_tvCity.text = list[position].city
        holder.itemView.cardViewCity.setOnClickListener {
            itemSelect.onclickListener(position)
        }
    }
}