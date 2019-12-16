package com.oysi.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.oysi.R
import com.oysi.adapter.AdapterCountry
import com.oysi.base.BaseFragment
import com.oysi.model.country.CountryResponse
import com.oysi.model.country.Data
import com.oysi.mvp.ViewPresenter.CountryViewPresenter
import com.oysi.mvp.presenter.CountryPresenter
import kotlinx.android.synthetic.main.fragment_country.*
import java.util.*
import kotlin.collections.ArrayList

class FragmentCountry : BaseFragment(), CountryViewPresenter {
    val key = "3564653d-5190-4ee6-9236-7cb733f6f27c"
    var list = ArrayList<Data>()
    var data = ArrayList<Data>()
    lateinit var adapter: AdapterCountry
    lateinit var presenter: CountryPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        presenter = CountryPresenter()
        presenter.attachView(this)
        return inflater.inflate(R.layout.fragment_country, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        progress_dialogCountry.visibility = View.VISIBLE
        adapter = AdapterCountry(activity!!.applicationContext, list,object  : AdapterCountry.onItemSelect{
            override fun onclickListener(position: Int) {
                val transaction = activity!!.supportFragmentManager.beginTransaction()
                val fragmentState = FragmentState()
                transaction.replace(R.id.frame_layout,fragmentState)
                val country = list.get(position).country
                val b = Bundle()
                b.putString("country",country)
                fragmentState.arguments = b
                transaction.addToBackStack(null)
                transaction.commit()

            }

        })
        rcCountry.adapter = adapter
        rcCountry.layoutManager = GridLayoutManager(activity, 2)
        getCountry(key)
        adapter.notifyDataSetChanged()
        ListenerOnclick()
    }


    fun ListenerOnclick() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filter(newText!!)
                adapter.filter.filter(newText)
                return false
            }

        })
    }

    fun filter(charText: String) {
        var charText = charText
        charText = searchView.query.toString().toLowerCase(Locale.getDefault())
        list.clear()
        if (charText.length == 0) {
            list.addAll(data)
        } else {
            for (country in data) {
                if (country.country.toLowerCase().contains(charText)) {
                    list.add(country)
                }
            }
        }
        adapter.notifyDataSetChanged()
    }

    /*-------------  Event Listener --------------*/
    fun getCountry(key: String) {
        presenter.getCountry(key)

    }

    /*------------- View Event Listener--------------*/
    override fun onLoadCountrySuccess(response: CountryResponse) {

        if (response.status=="success"){
            data.addAll(response.data)
            list.addAll(data)
            adapter.notifyDataSetChanged()
            progress_dialogCountry.visibility = View.INVISIBLE

        }

    }

    override fun showError(error: String) {
        Toast.makeText(activity!!.applicationContext, error, Toast.LENGTH_LONG).show()
    }
}