package com.oysi.activity

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.oysi.R
import com.oysi.fragment.FragmentDialog
import com.oysi.model.district.DistrictResponse
import com.oysi.mvp.ViewPresenter.CityCauGiayViewPresenter
import com.oysi.mvp.ViewPresenter.CityHaNoiViewPresenter
import com.oysi.mvp.ViewPresenter.CityTayHoViewPresenter
import com.oysi.mvp.presenter.CityCauGiayPresenter
import com.oysi.mvp.presenter.CityHanoiPresenter
import com.oysi.mvp.presenter.CityTayHoPresenter
import kotlinx.android.synthetic.main.activity_maps.*


class MapsActivity : AppCompatActivity(), OnMapReadyCallback, CityHaNoiViewPresenter,
    CityCauGiayViewPresenter,CityTayHoViewPresenter {
    private val PERMISSION_REQUEST = 100
    private val key = "3564653d-5190-4ee6-9236-7cb733f6f27c"
    private var latitude = 0.toDouble()
    private var longitude = 0.toDouble()

    private lateinit var mMap: GoogleMap
    private lateinit var mLastLocation: Location
    private var mMarker: Marker? = null

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback

    private var showDialog = false

    private lateinit var presenterLangHa: CityHanoiPresenter
    private lateinit var presenterCauGiay: CityCauGiayPresenter
    private lateinit var presenterTayHo : CityTayHoPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        presenterTayHo = CityTayHoPresenter()
        presenterTayHo.attachView(this)

        presenterLangHa = CityHanoiPresenter()
        presenterLangHa.attachView(this)

        presenterCauGiay = CityCauGiayPresenter()
        presenterCauGiay.attachView(this)

        frame_dialog.visibility = View.INVISIBLE
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkLocationPermission()) {
                buildLocationRequest()
                buildLocationCallback()

                fusedLocationProviderClient =
                    LocationServices.getFusedLocationProviderClient(this)
                fusedLocationProviderClient.requestLocationUpdates(
                    locationRequest, locationCallback,
                    Looper.myLooper()
                )
            }
        } else {
            buildLocationRequest()
            buildLocationCallback()

            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest, locationCallback,
                Looper.myLooper()
            )
        }
        buttonOnclick()
    }


    /*-----------------Event Onclick ---------------------*/

    private fun buttonOnclick() {
        fab.setOnClickListener {
            if (showDialog == false) {
                val handler = Handler()
                progress_dialog.visibility = View.VISIBLE
                handler.postDelayed(Runnable {
                    frame_dialog.visibility = View.VISIBLE
                    val fm = supportFragmentManager.beginTransaction()
                    val dialog = FragmentDialog()
                    fm.replace(R.id.frame_dialog, dialog)
                    val bundle = Bundle()
                    bundle.putString("latitude", latitude.toString())
                    bundle.putString("longitude", longitude.toString())
                    dialog.arguments = bundle
                    fm.commit()
                    progress_dialog.visibility = View.INVISIBLE
                }, 2000)

                showDialog = true
            } else {
                showDialog = false
                progress_dialog.visibility = View.INVISIBLE
                frame_dialog.visibility = View.INVISIBLE
            }

        }
    }
/*-----------------Build location ---------------------*/

    private fun buildLocationRequest() {
        locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 5000
        locationRequest.fastestInterval = 3000
        locationRequest.smallestDisplacement = 10f
    }

    private fun buildLocationCallback() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult?) {
                super.onLocationResult(p0)
                mLastLocation = p0!!.locations.get(p0.locations.size - 1)
                if (mMarker != null) {
                    mMarker!!.remove()
                }
                latitude = mLastLocation.latitude
                longitude = mLastLocation.longitude
                var latLng = LatLng(latitude, longitude)
                val markerOptions =
                    MarkerOptions().position(latLng).title(getString(R.string.current_location))
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                mMarker = mMap.addMarker(markerOptions)
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                mMap.animateCamera(CameraUpdateFactory.zoomTo(12f))
            }
        }
    }


    /*-----------------Check permision ---------------------*/
    private fun checkLocationPermission(): Boolean {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSION_REQUEST
                )
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSION_REQUEST
                )
            }
            return false

        } else
            return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        if (checkLocationPermission()) {
                            buildLocationRequest()
                            buildLocationCallback()

                            fusedLocationProviderClient =
                                LocationServices.getFusedLocationProviderClient(this)
                            fusedLocationProviderClient.requestLocationUpdates(
                                locationRequest, locationCallback,
                                Looper.myLooper()
                            )
                            mMap.isMyLocationEnabled
                        }
                    } else {
                        Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }


/*-----------------Map ---------------------*/


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                mMap.isMyLocationEnabled = true
            }
        } else {
            mMap.isMyLocationEnabled = true

            mMap.uiSettings.isZoomControlsEnabled = true

        }
        getCauGiay(key)
        getLangHa(key)
        getTayHo(key)
    }

    private fun getTayHo(key: String) {
        presenterTayHo.getDataTayHo(key)
    }

    /*------------------Event function -------------*/

    private fun getCauGiay(key: String) {
        presenterCauGiay.getDataCauGiay( key)
    }

    private fun getLangHa(key: String) {
        presenterLangHa.loadDataDistrict( key)
    }

    override fun onStop() {
        super.onStop()
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }


    /*------------------Event View Presenter -------------*/
    override fun loadDistrictSuccess(response: DistrictResponse) {
        if (response.status == "success") {
            val longLangha = response.data.location.coordinates[0]
            val latLangha = response.data.location.coordinates[1]
            val aqi = response.data.current.pollution.aqius
            val thoitiet = response.data.current.weather.ic
            val nhietdo = response.data.current.weather.tp
            mMap.addMarker(
                MarkerOptions().position(
                    LatLng(
                        latLangha,
                        longLangha
                    )
                ).title("Láng Hạ \n 68")
            ).showInfoWindow()
            if (aqi > 0 && aqi <= 49) {
                mMap.addCircle(
                    CircleOptions().center(LatLng(latLangha, longLangha))
                        .radius(1500.0).strokeWidth(1f).fillColor(Color.parseColor(getString(R.string.good)))
                )
            } else if (aqi >= 50 && aqi <= 99) {
                mMap.addCircle(
                    CircleOptions().center(LatLng(latLangha, longLangha))
                        .radius(1500.0).strokeWidth(1f).fillColor(Color.parseColor(getString(R.string.normal)))
                )
            } else if (aqi >= 100 && aqi <= 149) {
                mMap.addCircle(
                    CircleOptions().center(LatLng(latLangha, longLangha))
                        .radius(1500.0).strokeWidth(1f).fillColor(Color.parseColor(getString(R.string.notgood)))
                )
            } else if (aqi >= 150 && aqi <= 199) {
                mMap.addCircle(
                    CircleOptions().center(LatLng(latLangha, longLangha))
                        .radius(1500.0).strokeWidth(1f).fillColor(Color.parseColor(getString(R.string.danger)))
                )
            } else if (aqi >= 200 && aqi <= 249) {
                mMap.addCircle(
                    CircleOptions().center(LatLng(latLangha, longLangha))
                        .radius(1500.0).strokeWidth(1f).fillColor(Color.parseColor(getString(R.string.soimportant)))
                )
            } else if (aqi >= 250) {
                mMap.addCircle(
                    CircleOptions().center(LatLng(latLangha, longLangha))
                        .radius(1500.0).strokeWidth(1f).fillColor(Color.parseColor(getString(R.string.careful)))
                )
            }
        }
    }

    override fun getDataCauGiaySuccess(response: DistrictResponse) {
        if (response.status == "success") {
            val longCauGIay = response.data.location.coordinates[0]
            val latCauGiay = response.data.location.coordinates[1]
            val aqi = response.data.current.pollution.aqius
            val thoitiet = response.data.current.weather.ic
            val nhietdo = response.data.current.weather.tp
            mMap.addMarker(
                MarkerOptions().position(
                    LatLng(
                        latCauGiay,
                        longCauGIay
                    )
                ).title("Cầu Giấy")
            ).showInfoWindow()
            if (aqi > 0 && aqi <= 49) {
                mMap.addCircle(
                    CircleOptions().center(LatLng(latCauGiay, longCauGIay))
                        .radius(1500.0).strokeWidth(1f).fillColor(Color.parseColor(getString(R.string.good)))
                )
            } else if (aqi >= 50 && aqi <= 99) {
                mMap.addCircle(
                    CircleOptions().center(LatLng(latCauGiay, longCauGIay))
                        .radius(1500.0).strokeWidth(1f).fillColor(Color.parseColor(getString(R.string.normal)))
                )
            } else if (aqi >= 100 && aqi <= 149) {
                mMap.addCircle(
                    CircleOptions().center(LatLng(latCauGiay, longCauGIay))
                        .radius(1500.0).strokeWidth(1f).fillColor(Color.parseColor(getString(R.string.notgood)))
                )
            } else if (aqi >= 150 && aqi <= 199) {
                mMap.addCircle(
                    CircleOptions().center(LatLng(latCauGiay, longCauGIay))
                        .radius(1500.0).strokeWidth(1f).fillColor(Color.parseColor(getString(R.string.danger)))
                )
            } else if (aqi >= 200 && aqi <= 249) {
                mMap.addCircle(
                    CircleOptions().center(LatLng(latCauGiay, longCauGIay))
                        .radius(1500.0).strokeWidth(1f).fillColor(Color.parseColor(getString(R.string.soimportant)))
                )
            } else if (aqi >= 250) {
                mMap.addCircle(
                    CircleOptions().center(LatLng(latCauGiay, longCauGIay))
                        .radius(1500.0).strokeWidth(1f).fillColor(Color.parseColor(getString(R.string.careful)))
                )
            }
        }

    }

    override fun getDataTayHoSuccess(response: DistrictResponse) {
        if (response.status == "success") {
            val longTayHo = response.data.location.coordinates[0]
            val latTayHo = response.data.location.coordinates[1]
            val aqi = response.data.current.pollution.aqius
            val thoitiet = response.data.current.weather.ic
            val nhietdo = response.data.current.weather.tp
            mMap.addMarker(
                MarkerOptions().position(
                    LatLng(
                        latTayHo,
                        longTayHo
                    )
                ).title("Tây Hồ")
            ).showInfoWindow()
            if (aqi > 0 && aqi <= 49) {
                mMap.addCircle(
                    CircleOptions().center(LatLng(latTayHo, longTayHo))
                        .radius(1500.0).strokeWidth(1f).fillColor(Color.parseColor(getString(R.string.good)))
                )
            } else if (aqi >= 50 && aqi <= 99) {
                mMap.addCircle(
                    CircleOptions().center(LatLng(latTayHo, longTayHo))
                        .radius(1500.0).strokeWidth(1f).fillColor(Color.parseColor(getString(R.string.normal)))
                )
            } else if (aqi >= 100 && aqi <= 149) {
                mMap.addCircle(
                    CircleOptions().center(LatLng(latTayHo, longTayHo))
                        .radius(1500.0).strokeWidth(1f).fillColor(Color.parseColor(getString(R.string.notgood)))
                )
            } else if (aqi >= 150 && aqi <= 199) {
                mMap.addCircle(
                    CircleOptions().center(LatLng(latTayHo, longTayHo))
                        .radius(1500.0).strokeWidth(1f).fillColor(Color.parseColor(getString(R.string.danger)))
                )
            } else if (aqi >= 200 && aqi <= 249) {
                mMap.addCircle(
                    CircleOptions().center(LatLng(latTayHo, longTayHo))
                        .radius(1500.0).strokeWidth(1f).fillColor(Color.parseColor(getString(R.string.soimportant)))
                )
            } else if (aqi >= 250) {
                mMap.addCircle(
                    CircleOptions().center(LatLng(latTayHo, longTayHo))
                        .radius(1500.0).strokeWidth(1f).fillColor(Color.parseColor(getString(R.string.careful)))
                )
            }
        }
    }

    override fun showError(error: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
