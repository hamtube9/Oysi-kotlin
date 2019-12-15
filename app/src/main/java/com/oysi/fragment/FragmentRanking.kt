package com.oysi.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.oysi.R
import com.oysi.adapter.AdapterRanking
import com.oysi.mvp.ViewPresenter.RankingViewPresenter
import kotlinx.android.synthetic.main.fragment_ranking.*

class FragmentRanking : Fragment(){
    var list = ArrayList<String>()
    lateinit var adapter : AdapterRanking
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_ranking,container,false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        for (i in 0..100){
            list.add(i.toString()+"")
        }
        adapter= AdapterRanking(activity!!.applicationContext,list)
        rcRanking.adapter=adapter
        rcRanking.layoutManager = LinearLayoutManager(activity)

    }


}