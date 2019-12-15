package com.oysi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oysi.R
import kotlinx.android.synthetic.main.item_ranking.view.*

class AdapterRanking(var context: Context, var list: ArrayList<String>):
    RecyclerView.Adapter<AdapterRanking.ViewHolder>() {
    class ViewHolder(itemView : View):RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterRanking.ViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.item_ranking,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: AdapterRanking.ViewHolder, position: Int) {
        var c = list.get(position)
        holder.itemView.tvRanking.text = c
    }
}