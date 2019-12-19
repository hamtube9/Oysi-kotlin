package com.oysi.activity

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.oysi.R
import com.oysi.fragment.FragmentListWorld
import com.oysi.fragment.FragmentMyCountry
import com.oysi.fragment.FragmentRanking
import com.oysi.fragment.FragmentUser
import com.pushbots.push.Pushbots
import kotlinx.android.synthetic.main.activity_main.*
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        Pushbots.sharedInstance().registerForRemoteNotifications()

        val fragmentList = ArrayList<Fragment>()
        fragmentList.add(FragmentListWorld())
        fragmentList.add(FragmentMyCountry())
        fragmentList.add(FragmentRanking())
        fragmentList.add(FragmentUser())

        spaceTabLayout.initialize(viewPager,supportFragmentManager,fragmentList,savedInstanceState)
        spaceTabLayout.setTabOneIcon(R.drawable.ic_list)
        spaceTabLayout.setTabTwoIcon(R.drawable.ic_vietnam)
        spaceTabLayout.setTabThreeIcon(R.drawable.ic_ranking)
        spaceTabLayout.setTabFourIcon(R.drawable.ic_facebook)

        printHashKey(this)
    }

    fun printHashKey(pContext: Context) {
        try {
            val info: PackageInfo = pContext.packageManager
                .getPackageInfo(pContext.packageName, PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val hashKey = String(Base64.encode(md.digest(), 0))
                Log.d("keyHash", "printHashKey() Hash Key: $hashKey")
            }
        } catch (e: NoSuchAlgorithmException) {
            Log.e("keyHash", "printHashKey()", e)
        } catch (e: Exception) {
            Log.e("keyHash", "printHashKey()", e)
        }
    }
}
