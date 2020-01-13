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


    class ViewHolder(itemView : View):RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_country,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setCityCraft(filterCountryCraft : ArrayList<Data>?){
        if (filterCountryCraft != null) {
            this.list = filterCountryCraft
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tvCountry.text = list[position].country
        holder.itemView.cardViewCountry.setOnClickListener {
            onclick.onclickListener(position)
        }

        when (holder.itemView.tvCountry.text) {
            "Afghanistan" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.afghanistan)
            }
            "Algeria" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.algeria)
            }
            "Andorra" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.andorra)
            }
            "Algola" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.angola)
            }
            "Argentina" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.argentina)
            }
            "Armenia" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.armenia)
            }
            "Australia" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.australia)
            }
            "Austria" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.austria)
            }
            "Bahamas" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.bahamas)
            }
            "Bahrain" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.bahrain)
            }
            "Bangladest" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.bangladesh)
            }
            "Belgium" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.belgium)
            }
            "Bovilia" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.bolivia)
            }
            "Bosnia Herzegovina" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.bosnia)
            }
            "Brazil" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.brazil)
            }
            "Brunei" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.brunei)
            }
            "Bulgaria" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.bulgaria)
            }
            "Canada" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.canada)
            }
            "Chile" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.chile)
            }
            "China" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.china)
            }
            "Colombia" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.colombia)
            }
            "Croatia" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.croatia)
            }
            "Cyprus" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.cyrus)
            }
            "Czech Republic" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.czech)
            }
            "Democratic Republic of the Congo" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.democratic)
            }
            "Denmark" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.denmark)
            }
            "Ecuador" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.ecuador)
            }
            "Ethiopia" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.ethiopia)
            }
            "Finland" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.finland)
            }
            "France" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.france)
            }
            "Germany" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.germany)
            }
            "Ghana" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.bulgaria)
            }
            "Greece" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.greece)
            }
            "Guatemala" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.guatemala)
            }
            "Haiti" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.haiti)
            }
            "Hong Kong SAR" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.hongkong)
            }
            "Hungary" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.hungary)
            }
            "Iceland" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.iceland)
            }
            "India" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.india)
            }
            "Indonesia" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.indonesia)
            }
            "Iran" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.iran)
            }
            "Iraq" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.iraq)
            }
            "Ireland" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.ireland)
            }
            "Israel" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.israel)
            }
            "Italy" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.italy)
            }
            "Ivory Coast" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.ivory_coast)
            }
            "Japan" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.japan)
            }
            "Jordan" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.jordan)
            }
            "Kazakhstan" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.kazakhstan)
            }
            "Kosovo" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.kosovo)
            }
            "Kuwait" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.kuwait)
            }
            "Kyrgyzstan" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.kyrgyzstan)
            }
            "Laos" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.laos)
            }
            "Latvia" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.latvia)
            }
            "Lithuania" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.lithuania)
            }
            "Luxembourg" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.luxembourg)
            }
            "Macao" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.macao)
            }
            "Macedonia" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.macedonia)
            }
            "Malaysia" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.malaysia)
            }
            "Malta" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.malta)
            }
            "Mexico" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.mexico)
            }
            "Mongolia" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.mongolia)
            }
            "Myanmar" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.myanmar)
            }
            "Nepal" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.nepal)
            }
            "Netherlands" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.netherlands)
            }
            "New Zealand" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.newzealand)
            }
            "Nigeria" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.nigeria)
            }
            "Norway" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.norway)
            }
            "Pakistan" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.pakistan)
            }
            "Peru" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.peru)
            }
            "Philippines" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.philippines)
            }
            "Poland" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.poland)
            }
            "Portugal" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.portugal)
            }
            "Puerto Rico" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.puertorico)
            }
            "Romania" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.romania)
            }
            "Russia" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.russia)
            }
            "San Marino" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.sanmarino)
            }
            "Saudi Arabia" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.saudiarabia)
            }
            "Serbia" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.serbia)
            }
            "Singapore" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.singapore)
            }
            "Slovakia" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.slovakia)
            }
            "Slovenia" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.slovenia)
            }
            "South Africa" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.southafrica)
            }
            "South Korea" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.southkorea)
            }
            "Spain" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.spain)
            }
            "Sri Lanka" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.srilanka)
            }
            "Sweden" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.sweden)
            }
            "Switzerland" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.switzerland)
            }
            "Taiwan" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.taiwan)
            }
            "Thailand" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.thailand)
            }
            "Uganda" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.uganda)
            }
            "Ukraine" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.ukraine)
            }
            "United Arab Emirates" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.unitedarabemirates)
            }
            "United Kingdom" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.unitedkingdom)
            }
            "USA" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.unitedstates)
            }
            "Uzbekistan" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.uzbekistan)
            }
            "Vietnam" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.vietnam)
            }
            "Yemen" -> {
                holder.itemView.imgCountry.setImageResource(R.drawable.yemen)
            }
        }


    }

    override fun getFilter(): Filter {
        val filterList : ArrayList<Data> = list
        return FilterHelpCountry.instanceCountry(filterList,this)
    }
}