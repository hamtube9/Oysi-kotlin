package com.oysi.filter

import android.widget.Filter
import com.oysi.adapter.AdapterCountry
import com.oysi.model.country.Data

open class FilterHelpCountry : Filter() {
    companion object{
        var listFilter: ArrayList<Data>? = null
        lateinit var adapter: AdapterCountry
        fun instanceCountry(
            filterList: ArrayList<Data>,
            adapterCountry: AdapterCountry
        ): FilterHelpCountry {
            this.listFilter = filterList
            this.adapter = adapterCountry
            return FilterHelpCountry()
        }
    }


    override fun performFiltering(constraint: CharSequence?): FilterResults {
        var result: FilterResults = FilterResults()
        if (constraint != null || constraint!!.length > 0) {
            var c = constraint.toString().toUpperCase()

            var foundList: ArrayList<Data> = ArrayList()
            var country: Data
            for (i in 0..listFilter!!.size) {
                country = listFilter!!.get(i)
                if (country.country.toUpperCase().contains(constraint)) {
                    foundList.add(country)
                }
            }
            result.count = foundList.size
            result.values = foundList

        }
            return result
    }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

                adapter.setCityCraft(results?.values as ArrayList<Data>?)

            adapter.notifyDataSetChanged()
        }

    }