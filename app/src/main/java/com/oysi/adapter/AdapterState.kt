package com.oysi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.oysi.R
import com.oysi.filter.FilterHelpState
import com.oysi.model.state.Data
import kotlinx.android.synthetic.main.item_state.view.*

class AdapterState(var context: Context,var list: ArrayList<Data>,var itemSelectOnClick: ItemSelectOnClick):
    RecyclerView.Adapter<AdapterState.ViewHolder>() ,Filterable{

    interface ItemSelectOnClick{
        fun onclickListener(position: Int)
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_state,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
       return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tvState.text = list[position].state
        holder.itemView.cardViewState.setOnClickListener {
            itemSelectOnClick.onclickListener(position)
        }
    }


    fun setStateCraft(filterStateCraft : ArrayList<Data>?){
        this.list = filterStateCraft!!
    }

    override fun getFilter(): Filter {
        val filterList : ArrayList<Data> = list
        return FilterHelpState.instanceCountry(filterList,this)
    }
}