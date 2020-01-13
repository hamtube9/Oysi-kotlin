package com.oysi.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.idocnet.inbrand.utils.custom.GridSpacingItemDecoration
import com.oysi.R
import com.oysi.activity.StateActivity
import com.oysi.adapter.AdapterCountry
import com.oysi.base.BaseFragment
import com.oysi.base.Constant
import com.oysi.base.TinyDB
import com.oysi.model.country.CountryResponse
import com.oysi.model.country.Data
import com.oysi.mvp.ViewPresenter.CountryViewPresenter
import com.oysi.mvp.presenter.CountryPresenter
import kotlinx.android.synthetic.main.fragment_country.*
import java.util.*
import kotlin.collections.ArrayList

class FragmentCountry : BaseFragment(), CountryViewPresenter {
    var key = ""
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
        data.clear()
        list.clear()
        return inflater.inflate(R.layout.fragment_country, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val tinyDB = TinyDB(activity!!.applicationContext)
        key = tinyDB.getString(Constant.KEY_API).toString()
        Log.d("key_api",key)
        progress_dialogCountry.visibility = View.VISIBLE
        adapter = AdapterCountry(
            activity!!.applicationContext,
            list,
            object : AdapterCountry.onItemSelect {
                override fun onclickListener(position: Int) {

                    val country = list.get(position).country
                    tinyDB.putString(Constant.COUNTRY, country)

                    startActivity(Intent(activity, StateActivity::class.java))

                }

            })
        rcCountry.adapter = adapter
        rcCountry.layoutManager = GridLayoutManager(activity, 2)
        rcCountry.addItemDecoration(GridSpacingItemDecoration(2, 14, false))
        getCountry(key)
        adapter.notifyDataSetChanged()
        ListenerOnclick()
    }

    /*-------------  Event Listener --------------*/
    fun filter(c: String) {
        var charText: String = c
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


    fun getCountry(key: String) {
        presenter.getCountry(key)
    }

    /*---------------- Event onclick  ----------------*/
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

    /*------------- View Event Listener--------------*/
    override fun onLoadCountrySuccess(response: CountryResponse) {
        if (response.status == "success") {
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