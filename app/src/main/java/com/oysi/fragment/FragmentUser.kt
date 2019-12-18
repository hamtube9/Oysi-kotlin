package com.oysi.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.database.*
import com.oysi.R
import com.oysi.activity.AddCityPollActivity
import com.oysi.adapter.AdapterPoll
import com.oysi.base.BaseFragment
import com.oysi.base.TinyDB
import com.oysi.model.Poll.CityPoll
import kotlinx.android.synthetic.main.fragment_user.*
import kotlinx.android.synthetic.main.item_poll.*


class FragmentUser : BaseFragment() {
    private lateinit var adapter: AdapterPoll
    private var listPoll: ArrayList<CityPoll> = ArrayList()
    private lateinit var ref: DatabaseReference
    private lateinit var dataList: MutableList<CityPoll>
    private var checked = true
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user, container, false)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        progress_poll.visibility = View.VISIBLE
        ref = FirebaseDatabase.getInstance().getReference("poll")
        dataList = mutableListOf()
        initView()
        onClick()
        adapterSetting()

        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    for (i in p0.children) {
                        val city = i.getValue(CityPoll::class.java)
                        dataList.add(city!!)
                        listPoll.clear()
                        listPoll.add(city)
                        adapter.notifyDataSetChanged()
                        progress_poll.visibility = View.INVISIBLE
                    }

                }
            }
        })

    }

    private fun onClick() {
        fabAddCityPoll.setOnClickListener {
            startActivity(Intent(activity, AddCityPollActivity::class.java))
        }
    }

    private fun adapterSetting() {

        adapter = AdapterPoll(
            activity!!.applicationContext,
            listPoll,object :AdapterPoll.onCheckItemLisnter{
                override fun onCheck(position: Int, cityPoll: CityPoll) {
                 val up=  listPoll[position].care!! +1
                    val updateCity = CityPoll(cityPoll.city,cityPoll.country,up)
                    ref.child(cityPoll.city.toString()).setValue(updateCity)

                }

                override fun onUnCheck(position: Int, cityPoll: CityPoll) {
                    val city = CityPoll(cityPoll.city,cityPoll.country,cityPoll.care)
                    ref.child(cityPoll.city.toString()).setValue(city)
                }
            })

        rcPoll.adapter = adapter
        rcPoll.layoutManager = LinearLayoutManager(activity!!)
        adapter.notifyDataSetChanged()
    }

    private fun initView() {
        val sharedPreferences = activity!!.getSharedPreferences("facebook", Context.MODE_PRIVATE)
        val userid = sharedPreferences.getString("userid", "")
        val photoUrl = "https://graph.facebook.com/" + userid + "/picture?height=500"
        tvNameUser.text = sharedPreferences.getString("displayName", "")

        tvEmailUser.text = sharedPreferences.getString("email", "")
        Glide.with(activity!!.applicationContext).load(photoUrl).into(userAvatar)

    }


}