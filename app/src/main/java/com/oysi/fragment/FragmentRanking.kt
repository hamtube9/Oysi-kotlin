package com.oysi.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.oysi.R
import com.oysi.adapter.AdapterRanking
import com.oysi.base.Constant
import com.oysi.base.TinyDB
import com.oysi.model.ranking.Data
import com.oysi.model.ranking.RankingResponse
import com.oysi.mvp.ViewPresenter.RankingViewPresenter
import com.oysi.mvp.presenter.RankingPresenter
import kotlinx.android.synthetic.main.fragment_ranking.*

class FragmentRanking : Fragment(), RankingViewPresenter {
    private var list = ArrayList<Data>()
    private var data = ArrayList<Data>()
    private lateinit var adapter: AdapterRanking
    private lateinit var presenterRanking: RankingPresenter
    private var tinyDB : TinyDB?=null
    private var key = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_ranking, container, false)
        presenterRanking = RankingPresenter()
        presenterRanking.attachView(this)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        tinyDB = TinyDB(activity!!)
        key = tinyDB!!.getString(Constant.KEY_API).toString()

        adapter = AdapterRanking(activity!!.applicationContext, list)
        rcRanking.adapter = adapter
        rcRanking.layoutManager = LinearLayoutManager(activity)
        getRanking(key)
    }

    /*------------- Event Presenter Listener--------------*/


    private fun getRanking(key: String) {
        presenterRanking.getDataRanking(key)
    }
    /*-------------Event View  Presenter --------------*/
    override fun onLoadRankingSucces(response: RankingResponse) {
        if (response.status == "success") {
            data.clear()
            data.addAll(response.data)
            list.clear()
            list.addAll(data)
            adapter.notifyDataSetChanged()
        }
    }

    override fun showError(error: String) {
    }


}