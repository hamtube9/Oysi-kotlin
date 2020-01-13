package com.oysi.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.idocnet.inbrand.utils.custom.GridSpacingItemDecoration
import com.oysi.R
import com.oysi.adapter.AdapterState
import com.oysi.base.Constant
import com.oysi.base.TinyDB
import com.oysi.model.state.Data
import com.oysi.model.state.StateResponse
import com.oysi.mvp.ViewPresenter.StateViewPresenter
import com.oysi.mvp.presenter.StatePresenter
import kotlinx.android.synthetic.main.activity_state.*
import java.util.*
import kotlin.collections.ArrayList

class StateActivity : AppCompatActivity(), StateViewPresenter {
    private var list : ArrayList<Data> = ArrayList()
    private var data : ArrayList<Data> = ArrayList()
    private var tinyDB :TinyDB?=null

    private var country =""
    private lateinit var adapter : AdapterState
    private  lateinit var preesenter : StatePresenter
    private var key =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_state)
        preesenter = StatePresenter()
        preesenter.attachView(this)
        tinyDB = TinyDB(this)
        key = tinyDB!!.getString(Constant.KEY_API).toString()
        country= tinyDB!!.getString(Constant.COUNTRY).toString()
        adapter = AdapterState(this,list,object : AdapterState.ItemSelectOnClick{
            override fun onclickListener(position: Int) {
                tinyDB!!.putString(Constant.STATE,list[position].state)
                startActivity(Intent(this@StateActivity,CityActivity::class.java))
            }
        })
        rcState.adapter = adapter
        rcState.layoutManager = GridLayoutManager(this,2)
        rcState.addItemDecoration(GridSpacingItemDecoration(2,14,false))
        adapter.notifyDataSetChanged()

        getState(country.toString(),key)

        searchView()
    }

    /*--------------- Event Function ---------------*/
    private fun getState(country:String,key:String) {
        preesenter.loadState(country,key)
    }

    /*-------------- function Onclick Listener-----------*/
    fun searchView(){
        svState.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
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

    /*-------------- function listener-----------*/

    private fun filter(newText: String) {
        var charText: String = newText
        charText = svState.query.toString().toLowerCase(Locale.getDefault())
        list.clear()
        if (charText.length == 0) {
            list.addAll(data)
        } else {
            for (state in data) {
                if (state.state.toLowerCase().contains(charText)) {
                    list.add(state)
                }
            }
        }
        adapter.notifyDataSetChanged()
    }

    /*-------------- Event View Presenter-----------*/

    override fun onLoadStateSuccess(response: StateResponse) {
        if (response.status=="success"){
            data.addAll(response.data)
            list.clear()
            list.addAll(data)
            adapter.notifyDataSetChanged()
            progress_dialogState.visibility= View.INVISIBLE
        }
    }

    override fun showError(error: String) {
    }
}
