package com.oysi.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.oysi.R
import com.oysi.activity.LoginActivity
import com.oysi.adapter.AdapterPoll
import com.oysi.base.BaseFragment
import com.oysi.model.Poll.CityPoll
import kotlinx.android.synthetic.main.fab_layout.*
import kotlinx.android.synthetic.main.fragment_user.*


class FragmentUser : BaseFragment() {
    private lateinit var adapter: AdapterPoll
    private var listPoll: ArrayList<CityPoll> = ArrayList()
    private lateinit var ref: DatabaseReference
    private var closeRotate: Animation? = null
    private var openRotate: Animation? = null
    private var open: Animation? = null
    private var close: Animation? = null
    private var show = false
    private var fabshow = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        progress_poll.visibility = View.VISIBLE
        cardViewFrame.visibility = View.INVISIBLE
        ref = FirebaseDatabase.getInstance().getReference("poll")

        initView()
        onClick()
        adapterSetting()

        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }
            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    listPoll.clear()
                    for (i in p0.children) {
                        val city = i.getValue(CityPoll::class.java)
                        listPoll.add(city!!)
                        adapter.notifyDataSetChanged()
                        progress_poll.visibility = View.INVISIBLE
                        tvEmpty.visibility = View.INVISIBLE
                    }
                } else {
                    tvEmpty.visibility = View.VISIBLE
                    progress_poll.visibility = View.INVISIBLE
                }
            }
        })

    }
    /*-------------- Event Onclick Lisnter-----------*/

    private fun onClick() {
        fabCityPoll.setOnClickListener {
           if (fabshow == false){
               fabshow = true
               fabAdd.show()
               fabLogout.show()
               fabCityPoll.setImageResource(R.drawable.ic_x)
           }else{
               fabshow = false
               fabAdd.hide()
               fabLogout.hide()
               fabCityPoll.setImageResource(R.drawable.ic_arrow_upward_black_24dp)
           }

        }

        fabAdd.setOnClickListener {
            openFragmentAdd()
        }

        edtSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filter(s.toString())
            }

        })

        fabLogout.setOnClickListener {
            logOut()
        }
    }

    private fun openFragmentAdd() {
        if (show == false) {
            show = true
            open = AnimationUtils.loadAnimation(activity!!.applicationContext, R.anim.open)
            open!!.duration = 100
            cardViewFrame.visibility = View.VISIBLE
            cardViewFrame.animation = open
        } else {
            show = false
            close = AnimationUtils.loadAnimation(activity!!.applicationContext, R.anim.close)
            close!!.duration = 100
            cardViewFrame.visibility = View.INVISIBLE
            cardViewFrame.animation = close
        }
    }

    private fun logOut() {
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        startActivity(Intent(activity,LoginActivity::class.java))
    }

    fun filter(s: String) {
        val flist = ArrayList<CityPoll>()
        for (item: CityPoll in listPoll) {
            if (item.city!!.toLowerCase().contains(s.toLowerCase())) {
                flist.add(item)
            }
        }
        adapter.filterList(flist)
    }

    /*-------------- Initialized-----------*/
    private fun adapterSetting() {
        adapter = AdapterPoll(
            activity!!.applicationContext,
            listPoll, object : AdapterPoll.onCheckItemLisnter {
                override fun onPlus(position: Int, cityPoll: CityPoll) {
                    val care = listPoll[position].care!!
                    val up = care + 1
                    val updateCity = CityPoll(cityPoll.city, cityPoll.state, cityPoll.country, up)
                    ref.child(cityPoll.city.toString()).setValue(updateCity)
                }
                override fun onMinus(position: Int, cityPoll: CityPoll) {
                    val care = listPoll[position].care!!
                    val down = care - 1
                    val city = CityPoll(cityPoll.city, cityPoll.state, cityPoll.country, down)
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

        val fm = activity!!.supportFragmentManager.beginTransaction()
        val fragment = FragmentAddCity()
        fm.replace(R.id.frame_add, fragment)
        fm.commit()
    }


}