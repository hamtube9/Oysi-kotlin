package com.oysi.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.oysi.R

class FragmentListWorld:Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list_world,container,false)
        val transaction = activity!!.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_layout,FragmentCountry())
        transaction.commit()
        return view
    }
}