package com.oysi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.oysi.R
import com.oysi.filter.FilterHelpCountry
import com.oysi.model.country.Data
import kotlinx.android.synthetic.main.item_country.view.*

class AdapterCountry(var context: Context,var list : ArrayList<Data>,var onclick : onItemSelect) :
    RecyclerView.Adapter<AdapterCountry.ViewHolder>(),Filterable {

    interface onItemSelect{
        fun onclickListener(position: Int)
    }


    class ViewHolder(itemView : View):RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterCountry.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_country,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setCityCraft(filterCountryCraft : ArrayList<Data>){
        if (filterCountryCraft != null) {
            this.list = filterCountryCraft
        }
    }

    override fun onBindViewHolder(holder: AdapterCountry.ViewHolder, position: Int) {
        holder.itemView.tvCountry.text = list[position].country
        holder.itemView.cardViewCountry.setOnClickListener {
            onclick.onclickListener(position)
        }
    }

    override fun getFilter(): Filter {
        var filterList : ArrayList<Data> = list
        return FilterHelpCountry.instanceCountry(filterList,this)
    }
}