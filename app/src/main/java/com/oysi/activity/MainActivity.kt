package com.oysi.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.oysi.R
import com.oysi.fragment.FragmentListWorld
import com.oysi.fragment.FragmentMyCountry
import com.oysi.fragment.FragmentRanking
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var fragmentList = ArrayList<Fragment>()
        fragmentList.add(FragmentListWorld())
        fragmentList.add(FragmentMyCountry())
        fragmentList.add(FragmentRanking())

        spaceTabLayout.initialize(viewPager,supportFragmentManager,fragmentList,savedInstanceState)
        spaceTabLayout.setTabOneIcon(R.drawable.ic_list)
        spaceTabLayout.setTabOneText(R.string.List)
        spaceTabLayout.setTabTwoIcon(R.drawable.ic_vietnam)
        spaceTabLayout.setTabTwoText(R.string.VietNam)
        spaceTabLayout.setTabThreeIcon(R.drawable.ic_ranking)
        spaceTabLayout.setTabThreeText(R.string.Rank)

    }
}
