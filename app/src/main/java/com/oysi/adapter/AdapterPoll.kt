package com.oysi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.daimajia.swipe.SwipeLayout
import com.oysi.R
import com.oysi.model.Poll.CityPoll
import kotlinx.android.synthetic.main.item_poll.view.*

class AdapterPoll(var context: Context, var listPoll: ArrayList<CityPoll>,var onCheck : OnCheckItemLisnter) :
    RecyclerView.Adapter<AdapterPoll.ViewHolder>() {
    interface OnCheckItemLisnter{
        fun shareButtonOnClick(position: Int)
        fun onPlus(position : Int, cityPoll: CityPoll)
        fun onMinus(position : Int, cityPoll: CityPoll)

    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_poll, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listPoll.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val a = listPoll[position]
        holder.itemView.tvSoLuotBinhChon.text = a.care.toString()
        holder.itemView.tvCityPoll.text = a.city + ", " + a.state
        holder.itemView.tvCountryPoll.text = a.country
        holder.itemView.imgPlus.setOnClickListener {
            onCheck.onPlus(position,a)
            holder.itemView.imgPlus.visibility = View.INVISIBLE
            holder.itemView.imgMinus.visibility = View.VISIBLE

        }

        holder.itemView.imgMinus.setOnClickListener {
            onCheck.onMinus(position,a)
            holder.itemView.imgPlus.visibility = View.VISIBLE
            holder.itemView.imgMinus.visibility = View.INVISIBLE
        }

        holder.itemView.swipe_item.showMode = SwipeLayout.ShowMode.PullOut
        holder.itemView.swipe_item.addDrag(SwipeLayout.DragEdge.Right, holder.itemView.swipe_item.findViewById(R.id.ll_item_hide))
        holder.itemView.img_item_share.setOnClickListener {
            onCheck.shareButtonOnClick(position)
        }

    }


    fun filterList(flist : ArrayList<CityPoll>){
        this.listPoll = flist
        notifyDataSetChanged()
    }

}