package com.oysi.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.oysi.R
import com.oysi.base.Constant
import com.oysi.base.TinyDB
import com.oysi.fragment.FragmentListWorld
import com.oysi.fragment.FragmentMyCountry
import com.oysi.fragment.FragmentRanking
import com.oysi.fragment.FragmentUser
import com.pushbots.push.Pushbots
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private val fm = supportFragmentManager
    private var fmlistWord = FragmentListWorld()
    private var fmMyCountry = FragmentMyCountry()
    private var fmRank = FragmentRanking()
    private var fmUser = FragmentUser()
    private var tinyDB: TinyDB? = null
    private var active: Fragment = fmRank

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tinyDB = TinyDB(this)
        Pushbots.sharedInstance().registerForRemoteNotifications()
        tinyDB!!.putString(Constant.KEY_API, "3dff9938-fb12-49f5-bbd3-8fc361d0407b")
        loadFragment(active)
        initBottomNav()

    }

    private fun initBottomNav() {


        fm.beginTransaction().add(R.id.flContent, fmlistWord, "4").hide(fmlistWord)
            .commit()
        fm.beginTransaction().add(R.id.flContent, fmMyCountry, "3").hide(fmMyCountry)
            .commit()
        fm.beginTransaction().add(R.id.flContent, fmUser, "2").hide(fmUser)
            .commit()
        fm.beginTransaction().add(R.id.flContent,fmRank , "1").commit()

        bnvMain.itemIconTintList = null

        bnvMain.setOnNavigationItemSelectedListener(object :
            BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(p0: MenuItem): Boolean {
                when (p0.itemId) {
                    R.id.menu_listworld -> {
                        loadFragment(fmlistWord)
                        return true
                    }

                    R.id.menu_vietnam -> {
                        loadFragment(fmMyCountry)
                        return true
                    }
                    R.id.menu_ranking -> {
                        loadFragment(fmRank)
                        return true
                    }
                    R.id.menu_facebook -> {
                        loadFragment(fmUser)
                        return true
                    }

                }
                return false
            }

        })
    }

    private fun loadFragment(fragment: Fragment) {
        fm.beginTransaction().hide(active).show(fragment).commit()
        active = fragment
    }

}
