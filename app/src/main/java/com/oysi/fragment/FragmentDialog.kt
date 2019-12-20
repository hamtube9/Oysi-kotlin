package com.oysi.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.oysi.R
import com.oysi.base.BaseFragment
import com.oysi.model.nearestcity.NearestCityResponse
import com.oysi.mvp.ViewPresenter.NearestCityViewPresenter
import com.oysi.mvp.presenter.NearestCityPresenter
import kotlinx.android.synthetic.main.fragment_dialog.*

class FragmentDialog : BaseFragment(), NearestCityViewPresenter {
    private var latitude: String? = null
    private var longitude: String? = null
    private lateinit var presenter: NearestCityPresenter
    private  val key = "3564653d-5190-4ee6-9236-7cb733f6f27c"


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dialog, container, false)
        presenter = NearestCityPresenter()
        presenter.attachView(this)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val bundle = arguments
        latitude = bundle!!.getString("latitude")
        longitude = bundle.getString("longitude")
        Log.d("getLatln", latitude + " " + longitude)
        getNearestCity(latitude.toString(), longitude.toString(), key)
    }


    /*-----------------Event Presenter Listener ---------------------*/

    private fun getNearestCity(lat: String, lon: String, key: String) {
        presenter.getNearestCity(lat, lon, key)
    }

    /*-----------------Event View Presenter---------------------*/

    override fun getDataNearestCitySuccess(response: NearestCityResponse) {
        if (response.status == "success") {
            dialog_tvCity.text = response.data.city
            dialog_tvNhietDo.text = response.data.current.weather.tp.toString()
            val aqi = response.data.current.pollution.aqius
            dialog_AQI.text = aqi.toString()
            val thoitietNearstCity = response.data.current.weather.ic
            if (thoitietNearstCity == "01d") {
                dialog_imgthoitiet.setImageResource(R.drawable.ic_01d)
            } else if (thoitietNearstCity == "01n") {
                dialog_imgthoitiet.setImageResource(R.drawable.ic_01n)
            } else if (thoitietNearstCity == "02d") {
                dialog_imgthoitiet.setImageResource(R.drawable.ic_02d)
            } else if (thoitietNearstCity == "02n") {
                dialog_imgthoitiet.setImageResource(R.drawable.ic_02n)
            } else if (thoitietNearstCity == "03d") {
                dialog_imgthoitiet.setImageResource(R.drawable.ic_03d)
            } else if (thoitietNearstCity == "04d") {
                dialog_imgthoitiet.setImageResource(R.drawable.ic_04d)
            } else if (thoitietNearstCity == "09d") {
                dialog_imgthoitiet.setImageResource(R.drawable.ic_09d)
            } else if (thoitietNearstCity == "10d") {
                dialog_imgthoitiet.setImageResource(R.drawable.ic_10d)
            } else if (thoitietNearstCity == "10n") {
                dialog_imgthoitiet.setImageResource(R.drawable.ic_10n)
            } else if (thoitietNearstCity == "11d") {
                dialog_imgthoitiet.setImageResource(R.drawable.ic_11d)
            } else if (thoitietNearstCity == "13d") {
                dialog_imgthoitiet.setImageResource(R.drawable.ic_13d)
            } else if (thoitietNearstCity == "50d") {
                dialog_imgthoitiet.setImageResource(R.drawable.ic_50d)
            }

            if (aqi in 1..49) {
                dialog_emotion.setImageResource(R.drawable.ic_happy)
            } else if (aqi in 50..99) {
                dialog_emotion.setImageResource(R.drawable.ic_normal)
            } else if (aqi in 100..149) {
                dialog_emotion.setImageResource(R.drawable.ic_sick)
            } else if (aqi in 150..199) {
                dialog_emotion.setImageResource(R.drawable.ic_sad)
            } else if (aqi in 200..249) {
                dialog_emotion.setImageResource(R.drawable.ic_surprise)
            } else if (aqi >= 250) {
                dialog_emotion.setImageResource(R.drawable.ic_danger)
            }
        }
    }

    override fun showError(error: String) {
        Log.d("ErrorNearestCity", error)
    }


}