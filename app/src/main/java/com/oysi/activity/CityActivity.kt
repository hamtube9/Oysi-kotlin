package com.oysi.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.idocnet.inbrand.utils.custom.GridSpacingItemDecoration
import com.oysi.R
import com.oysi.adapter.AdapterCity
import com.oysi.base.Constant
import com.oysi.base.TinyDB
import com.oysi.model.city.CityResponse
import com.oysi.model.city.Data
import com.oysi.model.district.DistrictResponse
import com.oysi.mvp.ViewPresenter.CityViewPresenter
import com.oysi.mvp.ViewPresenter.InfoCityViewPresenter
import com.oysi.mvp.presenter.CityPresenter
import com.oysi.mvp.presenter.InfoCityPresenter
import kotlinx.android.synthetic.main.activity_city.*

class CityActivity : AppCompatActivity() , CityViewPresenter, InfoCityViewPresenter {
    private lateinit var presenter: CityPresenter
    private lateinit var presenterInfoCity: InfoCityPresenter
    private var state =""
    private var country=""
    private var key = ""
    private var listData: ArrayList<Data> = ArrayList()
    private var listCity: ArrayList<Data> = ArrayList()
    private lateinit var adapter: AdapterCity
    private var tinyDB : TinyDB?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city)
        presenter = CityPresenter()
        presenter.attachView(this)

        presenterInfoCity = InfoCityPresenter()
        presenterInfoCity.attachView(this)
        tinyDB = TinyDB(this)
        progress_dialogCity.visibility = View.VISIBLE
        llInfoCity.visibility = View.INVISIBLE

        key = tinyDB!!.getString(Constant.KEY_API).toString()

        state = tinyDB!!.getString(Constant.STATE).toString()
        country = tinyDB!!.getString(Constant.COUNTRY).toString()
        getCity(state.toString(), country.toString(), key)
        adapter =
            AdapterCity(this, listCity, object : AdapterCity.onItemSelect {
                override fun onclickListener(position: Int) {
                    llInfoCity.visibility = View.INVISIBLE
                    tvShowInfo.visibility = View.INVISIBLE
                    progress_dialogToolbar.visibility = View.VISIBLE
                    val city : Data = listCity[position]
                    tinyDB!!.putString(Constant.CITY,city.city)
                    getInfoCity(country.toString(), state.toString(), city.city, key)
                }
            })

        rcCity.adapter = adapter
        rcCity.layoutManager = GridLayoutManager(this, 2)
        rcCity.addItemDecoration(GridSpacingItemDecoration(2,14,false))
        adapter.notifyDataSetChanged()
    }

    /*-------------- Event  Presenter Listener-----------*/

    private fun getInfoCity(country: String, state: String, city: String, key: String) {
        presenterInfoCity.getDataInfoCity(country, state, city, key)
    }

    private fun getCity(state: String, country: String, key: String) {
        presenter.getCityResponse(state, country, key)
    }

    override fun getDataCityResponse(response: CityResponse) {
        if (response.status == "success") {
            progress_dialogCity.visibility = View.INVISIBLE
            listData.addAll(response.data)
            listCity.clear()
            listCity.addAll(listData)
            adapter.notifyDataSetChanged()
        }
    }

    override fun getDataInfoCitySuccess(response: DistrictResponse) {
        if (response.status == "success") {
            progress_dialogToolbar.visibility = View.INVISIBLE
            llInfoCity.visibility = View.VISIBLE
            val aqi = response.data.current.pollution.aqius
            val thoitiet = response.data.current.weather.ic
            val nhietdo = response.data.current.weather.tp
            tvCity.text = response.data.city
            tvAQICity.text = aqi.toString()
            tvNhietDoCityToolbar.text = nhietdo.toString()

            when (thoitiet) {
                "01d" -> {
                    imgThoiTietCityToolbar.setImageResource(R.drawable.ic_01d)
                }
                "01n" -> {
                    imgThoiTietCityToolbar.setImageResource(R.drawable.ic_01n)
                }
                "02d" -> {
                    imgThoiTietCityToolbar.setImageResource(R.drawable.ic_02d)
                }
                "02n" -> {
                    imgThoiTietCityToolbar.setImageResource(R.drawable.ic_02n)
                }
                "03d" -> {
                    imgThoiTietCityToolbar.setImageResource(R.drawable.ic_03d)
                }
                "04d" -> {
                    imgThoiTietCityToolbar.setImageResource(R.drawable.ic_04d)
                }
                "09d" -> {
                    imgThoiTietCityToolbar.setImageResource(R.drawable.ic_09d)
                }
                "10d" -> {
                    imgThoiTietCityToolbar.setImageResource(R.drawable.ic_10d)
                }
                "10n" -> {
                    imgThoiTietCityToolbar.setImageResource(R.drawable.ic_10n)
                }
                "11d" -> {
                    imgThoiTietCityToolbar.setImageResource(R.drawable.ic_11d)
                }
                "13d" -> {
                    imgThoiTietCityToolbar.setImageResource(R.drawable.ic_13d)
                }
                "50d" -> {
                    imgThoiTietCityToolbar.setImageResource(R.drawable.ic_50d)
                }
            }

            when {
                aqi in 1..49 -> {
                    imgEmotionCity.setImageResource(R.drawable.ic_happy)
                }
                aqi in 50..99 -> {
                    imgEmotionCity.setImageResource(R.drawable.ic_normal)
                }
                aqi in 100..149 -> {
                    imgEmotionCity.setImageResource(R.drawable.ic_sick)
                }
                aqi in 150..199 -> {
                    imgEmotionCity.setImageResource(R.drawable.ic_sad)
                }
                aqi in 200..249 -> {
                    imgEmotionCity.setImageResource(R.drawable.ic_surprise)
                }
                aqi>=250 -> {
                    imgEmotionCity.setImageResource(R.drawable.ic_danger)
                }
            }

        }
    }

    override fun showError(error: String) {

    }

}
