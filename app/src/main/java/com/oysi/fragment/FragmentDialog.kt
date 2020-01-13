package com.oysi.fragment

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context.NOTIFICATION_SERVICE
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.oysi.R
import com.oysi.base.BaseFragment
import com.oysi.base.Constant
import com.oysi.model.nearestcity.NearestCityResponse
import com.oysi.mvp.ViewPresenter.NearestCityViewPresenter
import com.oysi.mvp.presenter.NearestCityPresenter
import kotlinx.android.synthetic.main.fragment_dialog.*

class FragmentDialog : BaseFragment(), NearestCityViewPresenter {
    private var latitude: String? = null
    private var longitude: String? = null
    private lateinit var presenter: NearestCityPresenter
    private  var key = ""
    private val CHANNEL_ID = "aqi notification"

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
        key = tinyDB.getString(Constant.KEY_API).toString()
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
            var new_aqi = aqi

            when {
                new_aqi != aqi -> {
                    pushNotifi(response.data.city,new_aqi,response.data.current.weather.tp)
                }
                new_aqi == aqi ->{
                    pushNotifi(response.data.city,aqi,response.data.current.weather.tp)

                }
            }

            dialog_AQI.text = aqi.toString()
            val thoitietNearstCity = response.data.current.weather.ic
            Log.d("thoitietCity",thoitietNearstCity)
            when (thoitietNearstCity) {
                "01d" -> {
                    dialog_imgthoitiet.setImageResource(R.drawable.ic_01d)
                }
                "01n" -> {
                    dialog_imgthoitiet.setImageResource(R.drawable.ic_01n)
                }
                "02d" -> {
                    dialog_imgthoitiet.setImageResource(R.drawable.ic_02d)
                }
                "02n" -> {
                    dialog_imgthoitiet.setImageResource(R.drawable.ic_02n)
                }
                "03d" -> {
                    dialog_imgthoitiet.setImageResource(R.drawable.ic_03d)
                }
                "04d" -> {
                    dialog_imgthoitiet.setImageResource(R.drawable.ic_04d)
                }
                "04n" -> {
                    dialog_imgthoitiet.setImageResource(R.drawable.ic_04d)
                }
                "09d" -> {
                    dialog_imgthoitiet.setImageResource(R.drawable.ic_09d)
                }
                "10d" -> {
                    dialog_imgthoitiet.setImageResource(R.drawable.ic_10d)
                }
                "10n" -> {
                    dialog_imgthoitiet.setImageResource(R.drawable.ic_10n)
                }
                "11d" -> {
                    dialog_imgthoitiet.setImageResource(R.drawable.ic_11d)
                }
                "13d" -> {
                    dialog_imgthoitiet.setImageResource(R.drawable.ic_13d)
                }
                "50d" -> {
                    dialog_imgthoitiet.setImageResource(R.drawable.ic_50d)
                }
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


      fun pushNotifi(city : String,aqi : Int,nhietdo : Int){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel
            displayNotification(city,aqi,nhietdo)
            val nCity = city
            val nAqi = aqi
            val nNhietdo = nhietdo
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel(CHANNEL_ID, nCity, importance)
            mChannel.description = "Độ ô nhiễm : " +nAqi.toString() + " và nhiệt độ : "+nNhietdo.toString()
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            val notificationManager = activity!!.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }
    }

    fun displayNotification(city : String,aqi : Int,nhietdo : Int){
        val nCity = city
        val nAqi = aqi
        val nNhietdo = nhietdo
      val builder =  NotificationCompat.Builder(activity)
        builder.setSmallIcon(R.drawable.com_facebook_button_icon)
        builder.setContentTitle("Oysi")
        builder.setContentText( "Thành phố "+nCity+" Đang có độ ô nhiễm : " +nAqi.toString() + " và nhiệt độ : "+nNhietdo.toString())
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT)

        var notiManager =NotificationManagerCompat.from((activity!!))
        notiManager.notify(1,builder.build())
        }
}