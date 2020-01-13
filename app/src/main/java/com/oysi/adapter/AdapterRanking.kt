package com.oysi.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oysi.R
import com.oysi.model.ranking.Data
import kotlinx.android.synthetic.main.item_ranking.view.*

class AdapterRanking(var context: Context, var list: ArrayList<Data>):
    RecyclerView.Adapter<AdapterRanking.ViewHolder>() {
    class ViewHolder(itemView : View):RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_ranking,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val c: Data = list[position]
        val aqi = c.ranking.current_aqi
        holder.itemView.tvRankingCity.text = c.city+", "+c.country
        holder.itemView.tvRankingAQI.text =aqi.toString()
        when {
            aqi in 1..49 -> {
               holder.itemView.llRanking.setBackgroundColor(Color.parseColor("#93e627"))
            }
            aqi in 50..99 -> {
                holder.itemView.llRanking.setBackgroundColor(Color.parseColor("#ffcc00"))
            }
            aqi in 100..149 -> {
                holder.itemView.llRanking.setBackgroundColor(Color.parseColor("#f97f2c"))
            }
            aqi in 150..199 -> {
                holder.itemView.llRanking.setBackgroundColor(Color.parseColor("#ff4645"))
            }
            aqi in 200..249 -> {
                holder.itemView.llRanking.setBackgroundColor(Color.parseColor("#940045"))
            }
            aqi in 250..299 -> {
                holder.itemView.llRanking.setBackgroundColor(Color.parseColor("#84002c"))
            }
            aqi >=300->{
                holder.itemView.llRanking.setBackgroundColor(Color.parseColor("#a87383"))
            }
        }

        holder.itemView.tvRankingSTT.text = (position+1).toString()


    }
}