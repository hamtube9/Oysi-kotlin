package com.oysi.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.daimajia.swipe.SwipeLayout
import com.oysi.R
import com.oysi.base.BaseFragment
import com.oysi.model.district.DistrictResponse
import com.oysi.mvp.ViewPresenter.DistrictViewPresenter
import com.oysi.mvp.presenter.DistrictPresenter
import kotlinx.android.synthetic.main.fragment_my_country.*

class FragmentMyCountry:BaseFragment() , DistrictViewPresenter {
    val key = "3564653d-5190-4ee6-9236-7cb733f6f27c"
    lateinit var presenter: DistrictPresenter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_my_country,container,false)
        presenter = DistrictPresenter()
        presenter.attachView(this)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        llAdvertise.visibility = View.VISIBLE
        swipe.showMode= SwipeLayout.ShowMode.PullOut
        swipe.addDrag(SwipeLayout.DragEdge.Left,swipe.findViewById(R.id.llHide))

        swipe2.showMode= SwipeLayout.ShowMode.PullOut
        swipe2.addDrag(SwipeLayout.DragEdge.Left,swipe.findViewById(R.id.llHide2))

        onClickListener()
        getHanoi(key)
    }

    fun onClickListener(){
        imgClose.setOnClickListener {
            llAdvertise.visibility = View.GONE
        }
    }

    private fun getHanoi(key:String) {
        presenter.loadDataDistrict("Vietnam","Hanoi","Hanoi",key)
    }

    /*-------------      ------------*/
    override fun loadDistrictSuccess(response: DistrictResponse) {
        if (response.status=="success"){
            var nhietdoHanoi = response.data.current.weather.tp
            var aqiHanoi = response.data.current.pollution.aqius
            var cityHanoi = response.data.city
            var thoitietHanoi = response.data.current.weather.ic
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
                imgEmotionHanoi.setImageResource(R.drawable.ic_sad)
            }else   if (aqiHanoi>=150 && aqiHanoi<=199){
                imgEmotionHanoi.setImageResource(R.drawable.ic_sick)
            }else   if (aqiHanoi>=200 && aqiHanoi <=249){
                imgEmotionHanoi.setImageResource(R.drawable.ic_surprise)
            }else   if (aqiHanoi>=250){
                imgEmotionHanoi.setImageResource(R.drawable.ic_danger)
            }

        }
    }

    override fun showError(error: String) {

    }
}