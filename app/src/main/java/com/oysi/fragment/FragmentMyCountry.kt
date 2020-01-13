package com.oysi.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.daimajia.swipe.SwipeLayout
import com.facebook.FacebookSdk
import com.facebook.share.model.ShareHashtag
import com.facebook.share.model.ShareLinkContent
import com.facebook.share.widget.ShareDialog
import com.oysi.R
import com.oysi.activity.MapsActivity
import com.oysi.base.BaseFragment
import com.oysi.base.Constant
import com.oysi.model.district.DistrictResponse
import com.oysi.mvp.ViewPresenter.CityHCMViewPresenter
import com.oysi.mvp.ViewPresenter.CityHaNoiViewPresenter
import com.oysi.mvp.presenter.CityHCMPresenter
import com.oysi.mvp.presenter.CityHanoiPresenter
import kotlinx.android.synthetic.main.fragment_my_country.*

class FragmentMyCountry : BaseFragment(), CityHaNoiViewPresenter, CityHCMViewPresenter {
    private var key = ""
    private val url = "https://www.airvisual.com/vietnam/hanoi"
    private lateinit var presenterHanoi: CityHanoiPresenter
    private lateinit var presenterHCM: CityHCMPresenter
    private lateinit var shareDialog : ShareDialog
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_my_country, container, false)
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
        swipe.showMode = SwipeLayout.ShowMode.PullOut
        swipe.addDrag(SwipeLayout.DragEdge.Right, swipe.findViewById(R.id.llHide))
        swipe2.showMode = SwipeLayout.ShowMode.PullOut
        swipe2.addDrag(SwipeLayout.DragEdge.Left, swipe.findViewById(R.id.llHide2))

        onClickListener()
        getHanoi(key)
        getHCM(key)
    }

    private fun initView() {
        key = tinyDB.getString(Constant.KEY_API).toString()

        tvLink.movementMethod = LinkMovementMethod.getInstance()
        FacebookSdk.sdkInitialize(activity)
        shareDialog = ShareDialog(this)
    }

    /*------------- Onclick Listener--------------*/
    private fun onClickListener() {
        imgClose.setOnClickListener {
            llAdvertise.visibility = View.GONE
        }
        btnMap.setOnClickListener {
            startActivity(Intent(activity, MapsActivity::class.java))
        }
        imgShareHanoi.setOnClickListener {
            val mess = "Mức ô nhiễm thành phố Hà Nội hiện đang là : " + tvAQIHanoi.text.toString()
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT, mess)
            intent.type = "text/plain"
            startActivity(Intent.createChooser(intent, "Share to : "))
        }
        imgShareFacebookHanoi.setOnClickListener {
            val aqi = tvAQIHanoi.text.toString()
            val nhietdo = tvNhietDoHaNoi.text.toString()
            val messHN = "Hà nội đang có mức ô nhiễm : "+aqi+". Nhiệt độ lúc này đang là : "+nhietdo+". Mọi người ra đường cẩn thận nhé"
            val content = ShareLinkContent.Builder()
                .setQuote(messHN)
                .setContentUrl(Uri.parse(url))
                .setShareHashtag(ShareHashtag.Builder().setHashtag("#AirQuality").build())
                .build()
            if (ShareDialog.canShow(ShareLinkContent::class.java)) {
                shareDialog.show(content)
            }
        }

            imgShareHCM.setOnClickListener {
                val mess = "Mức ô nhiễm thành phố Hồ Chí Minh hiện đang là : " + tvAQIHanoi.text.toString()
                val intent = Intent()
                intent.action = Intent.ACTION_SEND
                intent.putExtra(Intent.EXTRA_TEXT, mess)
                intent.type = "text/plain"
                startActivity(Intent.createChooser(intent, "Share to : "))
            }
        imgShareFacebookHCM.setOnClickListener {
            val aqiHCM = tvAQIHanoi.text.toString()
            val nhietdoHCM = tvNhietDoHaNoi.text.toString()
            val messHCM = "Hà nội đang có mức ô nhiễm : "+aqiHCM+". Nhiệt độ lúc này đang là : "+nhietdoHCM+". Mọi người ra đường cẩn thận nhé"
            val content = ShareLinkContent.Builder()
                .setQuote(messHCM)
                .setContentUrl(Uri.parse(url))
                .setShareHashtag(ShareHashtag.Builder().setHashtag("#AirQuality").build())
                .build()
            if (ShareDialog.canShow(ShareLinkContent::class.java)) {
                shareDialog.show(content)
            }
        }
    }


    /*------------- Event Listener--------------*/

    private fun getHanoi(key: String) {
        presenterHanoi.loadDataDistrict(key)
    }

    private fun getHCM(key: String) {
        presenterHCM.getHCM("Vietnam", "Ho Chi Minh City", "Ho Chi Minh City", key)
    }


    /*------------- View Event Listener--------------*/
    override fun loadDistrictSuccess(response: DistrictResponse) {
        if (response.status == "success") {
            val nhietdoHanoi = response.data.current.weather.tp
            val aqiHanoi = response.data.current.pollution.aqius
            val cityHanoi = response.data.city
            val thoitietHanoi = response.data.current.weather.ic
            tvNhietDoHaNoi.text = nhietdoHanoi.toString()
            tvAQIHanoi.text = aqiHanoi.toString()
            tvCityHanoi.text = cityHanoi
            when (thoitietHanoi) {
                "01d" -> {
                    imgThoiTietHanoi.setImageResource(R.drawable.ic_01d)
                }
                "01n" -> {
                    imgThoiTietHanoi.setImageResource(R.drawable.ic_01n)
                }
                "02d" -> {
                    imgThoiTietHanoi.setImageResource(R.drawable.ic_02d)
                }
                "02n" -> {
                    imgThoiTietHanoi.setImageResource(R.drawable.ic_02n)
                }
                "03d" -> {
                    imgThoiTietHanoi.setImageResource(R.drawable.ic_03d)
                }
                "04d" -> {
                    imgThoiTietHanoi.setImageResource(R.drawable.ic_04d)
                }
                "09d" -> {
                    imgThoiTietHanoi.setImageResource(R.drawable.ic_09d)
                }
                "10d" -> {
                    imgThoiTietHanoi.setImageResource(R.drawable.ic_10d)
                }
                "10n" -> {
                    imgThoiTietHanoi.setImageResource(R.drawable.ic_10n)
                }
                "11d" -> {
                    imgThoiTietHanoi.setImageResource(R.drawable.ic_11d)
                }
                "13d" -> {
                    imgThoiTietHanoi.setImageResource(R.drawable.ic_13d)
                }
                "50d" -> {
                    imgThoiTietHanoi.setImageResource(R.drawable.ic_50d)
                }
            }

            when {
                aqiHanoi in 1..49 -> {
                    imgEmotionHanoi.setImageResource(R.drawable.ic_happy)
                }
                aqiHanoi in 50..99 -> {
                    imgEmotionHanoi.setImageResource(R.drawable.ic_normal)
                }
                aqiHanoi in 100..149 -> {
                    imgEmotionHanoi.setImageResource(R.drawable.ic_sick)
                }
                aqiHanoi in 150..199 -> {
                    imgEmotionHanoi.setImageResource(R.drawable.ic_sad)
                }
                aqiHanoi in 200..249 -> {
                    imgEmotionHanoi.setImageResource(R.drawable.ic_surprise)
                }
                aqiHanoi >= 250 -> {
                    imgEmotionHanoi.setImageResource(R.drawable.ic_danger)
                }
            }

        }
    }

    override fun loadHCMSuccess(response: DistrictResponse) {
        if (response.status == "success") {
            val nhietdoHCM = response.data.current.weather.tp
            val aqiHCM = response.data.current.pollution.aqius
            val cityHCM = response.data.city
            val thoitietHCM = response.data.current.weather.ic
            tvNhietDoHCM.text = nhietdoHCM.toString()
            tvAQIHCM.text = aqiHCM.toString()
            tvCityHCM.text = cityHCM
            when (thoitietHCM) {
                "01d" -> {
                    imgThoiTietHCM.setImageResource(R.drawable.ic_01d)
                }
                "01n" -> {
                    imgThoiTietHCM.setImageResource(R.drawable.ic_01n)
                }
                "02d" -> {
                    imgThoiTietHCM.setImageResource(R.drawable.ic_02d)
                }
                "02n" -> {
                    imgThoiTietHCM.setImageResource(R.drawable.ic_02n)
                }
                "03d" -> {
                    imgThoiTietHCM.setImageResource(R.drawable.ic_03d)
                }
                "04d" -> {
                    imgThoiTietHCM.setImageResource(R.drawable.ic_04d)
                }
                "09d" -> {
                    imgThoiTietHCM.setImageResource(R.drawable.ic_09d)
                }
                "10d" -> {
                    imgThoiTietHCM.setImageResource(R.drawable.ic_10d)
                }
                "10n" -> {
                    imgThoiTietHCM.setImageResource(R.drawable.ic_10n)
                }
                "11d" -> {
                    imgThoiTietHCM.setImageResource(R.drawable.ic_11d)
                }
                "13d" -> {
                    imgThoiTietHCM.setImageResource(R.drawable.ic_13d)
                }
                "50d" -> {
                    imgThoiTietHCM.setImageResource(R.drawable.ic_50d)
                }
            }

            when {
                aqiHCM in 1..49 -> {
                    imgEmotionHCM.setImageResource(R.drawable.ic_happy)
                }
                aqiHCM in 50..99 -> {
                    imgEmotionHCM.setImageResource(R.drawable.ic_normal)
                }
                aqiHCM in 100..149 -> {
                    imgEmotionHCM.setImageResource(R.drawable.ic_sick)
                }
                aqiHCM in 150..199 -> {
                    imgEmotionHCM.setImageResource(R.drawable.ic_sad)
                }
                aqiHCM in 200..249 -> {
                    imgEmotionHCM.setImageResource(R.drawable.ic_surprise)
                }
                aqiHCM >= 250 -> {
                    imgEmotionHCM.setImageResource(R.drawable.ic_danger)
                }
            }

        }
    }

    override fun showError(error: String) {

    }
}