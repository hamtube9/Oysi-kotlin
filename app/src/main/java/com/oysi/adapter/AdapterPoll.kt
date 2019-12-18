package com.oysi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import com.oysi.R
import com.oysi.model.Poll.CityPoll
import kotlinx.android.synthetic.main.item_poll.view.*

class AdapterPoll(var context: Context, var listPoll: ArrayList<CityPoll>,var onCheck : onCheckItemLisnter) :
    RecyclerView.Adapter<AdapterPoll.ViewHolder>() {
    interface onCheckItemLisnter{
        fun onCheck(position : Int, cityPoll: CityPoll)
        fun onUnCheck(position : Int, cityPoll: CityPoll)

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_poll, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listPoll.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val a = listPoll[position]
        val dbCity = FirebaseDatabase.getInstance().getReference("poll")
        holder.itemView.tvSoLuotBinhChon.text = a.care.toString()
        holder.itemView.tvCityPoll.text = a.city + ", " + a.country

        holder.itemView.checkChon.setOnClickListener {
            when (holder.itemView.checkChon.isChecked) {
                true -> {
                    onCheck.onCheck(position,listPoll[position])
                    holder.itemView.checkChon.isChecked=true
                    var share = context.getSharedPreferences("check",Context.MODE_PRIVATE)
                }
                false -> {
                 onCheck.onUnCheck(position,listPoll[position])
                    holder.itemView.checkChon.isChecked=false
                }
            }
        }

    }

}