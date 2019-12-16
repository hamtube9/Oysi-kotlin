package com.oysi.fragment

import android.content.Intent
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.daimajia.swipe.SwipeLayout
import com.oysi.R
import com.oysi.activity.MapsActivity
import com.oysi.base.BaseFragment
import com.oysi.model.district.DistrictResponse
import com.oysi.mvp.ViewPresenter.CityHCMViewPresenter
import com.oysi.mvp.ViewPresenter.CityHaNoiViewPresenter
import com.oysi.mvp.presenter.CityHanoiPresenter
import com.oysi.mvp.presenter.CityHCMPresenter
import kotlinx.android.synthetic.main.fragment_my_country.*

class FragmentMyCountry:BaseFragment() , CityHaNoiViewPresenter, CityHCMViewPresenter {
    val key = "3564653d-5190-4ee6-9236-7cb733f6f27c"
    lateinit var presenterHanoi: CityHanoiPresenter
    lateinit var presenterHCM : CityHCMPresenter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_my_country,container,false)
        presenterHanoi = CityHanoiPresenter()
        presenterHanoi.attachView(this)

        presenterHCM = CityHCMPresenter()
        presenterHCM.attachView(this)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        llAdvertise.visibility = View.VISIBLE
        swipe.showMode= SwipeLayout.ShowMode.PullOut
        swipe.addDrag(SwipeLayout.DragEdge.Right,swipe.findViewById(R.id.llHide))
//swipe:rightEdgeSwipeOffset="0dp"
        swipe2.showMode= SwipeLayout.ShowMode.PullOut
        swipe2.addDrag(SwipeLayout.DragEdge.Left,swipe.findViewById(R.id.llHide2))

        onClickListener()
        getHanoi(key)
        getHCM(key)
    }

    private fun initView() {
        tvLink.movementMethod = LinkMovementMethod.getInstance()

     }

    /*------------- Onclick Listener--------------*/
    fun onClickListener(){
        imgClose.setOnClickListener {
            llAdvertise.visibility = View.GONE
        }
        btnMap.setOnClickListener {
            startActivity(Intent(activity,MapsActivity::class.java))
        }
    }


    /*------------- Event Listener--------------*/

    private fun getHanoi(key:String) {
        presenterHanoi.loadDataDistrict(key)
    }

    private fun getHCM(key: String){
        presenterHCM.getHCM("Vietnam","Ho Chi Minh City","Ho Chi Minh City",key)
    }


    /*------------- View Event Listener--------------*/
    override fun loadDistrictSuccess(response: DistrictResponse) {
        if (response.status=="success"){
            val nhietdoHanoi = response.data.current.weather.tp
            val aqiHanoi = response.data.current.pollution.aqius
            val cityHanoi = response.data.city
            val thoitietHanoi = response.data.current.weather.ic
            tvNhietDoHaNoi.text = nhietdoHanoi.toString()
            tvAQIHanoi.text = aqiHanoi.toString()
            tvCityHanoi.text = cityHanoi
            if (thoitietHanoi=="01d"){
                imgThoiTietHanoi.setImageResource(R.drawable.ic_01d)
            }else if (thoitietHanoi=="01n"){
                imgThoiTietHanoi.setImageResource(R.drawable.ic_01n)
            }else if (thoitietHanoi=="02d"){
                imgThoiTietHanoi.setImageResource(R.drawable.ic_02d)
            }else if (thoitietHanoi=="02n"){
                imgThoiTietHanoi.setImageResource(R.drawable.ic_02n)
            }else if (thoitietHanoi=="03d"){
                imgThoiTietHanoi.setImageResource(R.drawable.ic_03d)
            }else if (thoitietHanoi=="04d"){
                imgThoiTietHanoi.setImageResource(R.drawable.ic_04d)
            }else if (thoitietHanoi=="09d"){
                imgThoiTietHanoi.setImageResource(R.drawable.ic_09d)
            }else if (thoitietHanoi=="10d"){
                imgThoiTietHanoi.setImageResource(R.drawable.ic_10d)
            }else if (thoitietHanoi=="10n"){
                imgThoiTietHanoi.setImageResource(R.drawable.ic_10n)
            }else if (thoitietHanoi=="11d"){
                imgThoiTietHanoi.setImageResource(R.drawable.ic_11d)
            }else if (thoitietHanoi=="13d"){
                imgThoiTietHanoi.setImageResource(R.drawable.ic_13d)
            }else if (thoitietHanoi == "50d"){
                imgThoiTietHanoi.setImageResource(R.drawable.ic_50d)
            }

            if (aqiHanoi>0 && aqiHanoi<=49){
                imgEmotionHanoi.setImageResource(R.drawable.ic_happy)
            }else   if (aqiHanoi>=50 && aqiHanoi<=99){
                imgEmotionHanoi.setImageResource(R.drawable.ic_normal)
            }else   if (aqiHanoi>=100 && aqiHanoi<=149){
                imgEmotionHanoi.setImageResource(R.drawable.ic_sick)
            }else   if (aqiHanoi>=150 && aqiHanoi<=199){
                imgEmotionHanoi.setImageResource(R.drawable.ic_sad)
            }else   if (aqiHanoi>=200 && aqiHanoi <=249){
                imgEmotionHanoi.setImageResource(R.drawable.ic_surprise)
            }else   if (aqiHanoi>=250){
                imgEmotionHanoi.setImageResource(R.drawable.ic_danger)
            }

        }
    }

    override fun loadHCMSuccess(response: DistrictResponse) {

        if (response.status=="success"){
            val nhietdoHCM = response.data.current.weather.tp
            val aqiHCM = response.data.current.pollution.aqius
            val cityHCM = response.data.city
            val thoitietHCM = response.data.current.weather.ic
            tvNhietDoHCM.text = nhietdoHCM.toString()
            tvAQIHCM.text = aqiHCM.toString()
            tvCityHCM.text = cityHCM
            if (thoitietHCM=="01d"){
                imgThoiTietHCM.setImageResource(R.drawable.ic_01d)
            }else if (thoitietHCM=="01n"){
                imgThoiTietHCM.setImageResource(R.drawable.ic_01n)
            }else if (thoitietHCM=="02d"){
                imgThoiTietHCM.setImageResource(R.drawable.ic_02d)
            }else if (thoitietHCM=="02n"){
                imgThoiTietHCM.setImageResource(R.drawable.ic_02n)
            }else if (thoitietHCM=="03d"){
                imgThoiTietHCM.setImageResource(R.drawable.ic_03d)
            }else if (thoitietHCM=="04d"){
                imgThoiTietHCM.setImageResource(R.drawable.ic_04d)
            }else if (thoitietHCM=="09d"){
                imgThoiTietHCM.setImageResource(R.drawable.ic_09d)
            }else if (thoitietHCM=="10d"){
                imgThoiTietHCM.setImageResource(R.drawable.ic_10d)
            }else if (thoitietHCM=="10n"){
                imgThoiTietHCM.setImageResource(R.drawable.ic_10n)
            }else if (thoitietHCM=="11d"){
                imgThoiTietHCM.setImageResource(R.drawable.ic_11d)
            }else if (thoitietHCM=="13d"){
                imgThoiTietHCM.setImageResource(R.drawable.ic_13d)
            }else if (thoitietHCM == "50d"){
                imgThoiTietHCM.setImageResource(R.drawable.ic_50d)
            }

            if (aqiHCM>0 && aqiHCM<=49){
                imgEmotionHCM.setImageResource(R.drawable.ic_happy)
            }else   if (aqiHCM>=50 && aqiHCM<=99){
                imgEmotionHCM.setImageResource(R.drawable.ic_normal)
            }else   if (aqiHCM>=100 && aqiHCM<=149){
                imgEmotionHCM.setImageResource(R.drawable.ic_sick)
            }else   if (aqiHCM>=150 && aqiHCM<=199){
                imgEmotionHCM.setImageResource(R.drawable.ic_sad)
            }else   if (aqiHCM>=200 && aqiHCM <=249){
                imgEmotionHCM.setImageResource(R.drawable.ic_surprise)
            }else   if (aqiHCM>=250){
                imgEmotionHCM.setImageResource(R.drawable.ic_danger)
            }

        }
    }

    override fun showError(error: String) {

    }
}