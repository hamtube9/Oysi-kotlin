package com.oysi.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.oysi.R
import com.oysi.adapter.AdapterState
import com.oysi.base.BaseFragment
import com.oysi.model.state.Data
import com.oysi.model.state.StateResponse
import com.oysi.mvp.ViewPresenter.StateViewPresenter
import com.oysi.mvp.presenter.StatePresenter
import kotlinx.android.synthetic.main.fragment_state.*

class FragmentState : BaseFragment(),StateViewPresenter {
    var list : ArrayList<Data> = ArrayList()
    var data : ArrayList<Data> = ArrayList()
    var country :String?=null
    lateinit var adapter : AdapterState
    lateinit var preesenter : StatePresenter
    val key = "3564653d-5190-4ee6-9236-7cb733f6f27c"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view =inflater.inflate(R.layout.fragment_state,container,false)
        preesenter = StatePresenter()
        preesenter.attachView(this)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var b = arguments
        country= b!!.getString("country")
        adapter = AdapterState(activity!!.applicationContext,list,object : AdapterState.ItemSelectOnClick{
            override fun onclickListener(position: Int) {

            }
        })

        rcState.adapter = adapter
        rcState.layoutManager = GridLayoutManager(activity!!.applicationContext,2)
        adapter.notifyDataSetChanged()

        getState(country.toString(),key)
    }

    /*--------------- Event Function ---------------*/
    private fun getState(country:String,key:String) {
        preesenter.loadState(country,key)
    }


    /*-------------- Event View Presenter-----------*/
    override fun onLoadStateSuccess(response: StateResponse) {
      if (response.status=="success"){
          data.addAll(response.data)
          list.clear()
          list.addAll(data)
          adapter.notifyDataSetChanged()
      }
    }

    override fun showError(error: String) {
        Toast.makeText(activity!!.applicationContext,"Error : "+error,Toast.LENGTH_SHORT).show()
    }


}