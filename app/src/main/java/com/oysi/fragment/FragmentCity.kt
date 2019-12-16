package com.oysi.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.oysi.R
import com.oysi.adapter.AdapterCity
import com.oysi.base.BaseFragment
import com.oysi.model.city.CityResponse
import com.oysi.model.city.Data
import com.oysi.mvp.ViewPresenter.CityViewPresenter
import com.oysi.mvp.presenter.CityPresenter
import kotlinx.android.synthetic.main.fragment_city.*

class FragmentCity : BaseFragment(), CityViewPresenter {
    private lateinit var presenter: CityPresenter
    private var state: String? = null
    private var country : String?=null
    val key = "3564653d-5190-4ee6-9236-7cb733f6f27c"
    private var listData:ArrayList<Data> = ArrayList()
    private var listCity :ArrayList<Data> = ArrayList()
    private lateinit var adapter : AdapterCity
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_city, container, false)
        presenter= CityPresenter()
        presenter.attachView(this)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val b = arguments
        state = b!!.getString("state")
        country = b.getString("country")
        getCity(state.toString(),country.toString(),key)

        adapter = AdapterCity(activity!!.applicationContext,listCity,object :AdapterCity.onItemSelect{
            override fun onclickListener(position: Int) {
                tvShowInfo.visibility = View.INVISIBLE
                llInfoCity.visibility = View.VISIBLE
            }

        })

        rcCity.adapter = adapter
        rcCity.layoutManager = GridLayoutManager(activity!!.applicationContext,2)
        adapter.notifyDataSetChanged()
    }

    private fun getCity(state : String,country:String,key:String) {
        presenter.getCityResponse(state,country,key)
    }

    override fun getDataCityResponse(response: CityResponse) {
        if (response.status=="success"){
            listData.addAll(response.data)
            listCity.clear()
            listCity.addAll(listData)
            adapter.notifyDataSetChanged()
        }
    }

    override fun showError(error: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}