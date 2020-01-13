package com.oysi.filter

import android.widget.Filter
import com.oysi.adapter.AdapterState
import com.oysi.model.state.Data

class FilterHelpState : Filter() {
    companion object {
        var listFilter: ArrayList<Data>? = null
        lateinit var adapter: AdapterState
        fun instanceCountry(
            filterList: ArrayList<Data>,
            adapterState: AdapterState
        ): FilterHelpCountry {
            this.listFilter = filterList
            this.adapter = adapterState
            return FilterHelpCountry()
        }
    }


    override fun performFiltering(constraint: CharSequence?): FilterResults {
        val result = FilterResults()
        if (constraint != null || constraint!!.length > 0) {
            var c = constraint.toString().toUpperCase()

            val foundList: ArrayList<Data> = ArrayList()
            var state: Data
            for (i in 0..listFilter!!.size) {
                state = listFilter!!.get(i)
                if (state.state.toUpperCase().contains(constraint)) {
                    foundList.add(state)
                }
            }
            result.count = foundList.size
            result.values = foundList

        }
        return result
    }

    override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
        adapter.setStateCraft(results?.values as ArrayList<Data>?)
        adapter.notifyDataSetChanged()
    }

}