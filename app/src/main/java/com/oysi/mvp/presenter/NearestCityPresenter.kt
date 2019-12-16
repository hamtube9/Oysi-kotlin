package com.oysi.mvp.presenter

import android.util.Log
import com.oysi.model.nearestcity.NearestCityResponse
import com.oysi.mvp.Presenter
import com.oysi.mvp.View
import com.oysi.mvp.ViewPresenter.NearestCityViewPresenter
import com.oysi.service.RetrofitService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class NearestCityPresenter : Presenter {
    var presenter: NearestCityViewPresenter? = null
    var compositeDisposable = CompositeDisposable()
    override fun attachView(view: View) {
        presenter = view as NearestCityViewPresenter
    }

    override fun compositeDisposable() {
        compositeDisposable.dispose()
    }

    fun getNearestCity(lat: String, lon: String,key:String){
     compositeDisposable.add(RetrofitService.getAPIService().getCurrentLocation(lat,lon,key)
         .subscribeOn(Schedulers.io())
         .observeOn(AndroidSchedulers.mainThread())
         .subscribe(this::onGetDataNearestCitySuccess){t -> onGetDataFail(t,"Load Nearest City Failed")})
    }

    private fun onGetDataNearestCitySuccess(response: NearestCityResponse){
        presenter!!.getDataNearestCitySuccess(response)
    }

    private fun onGetDataFail(t:Throwable,error:String){
        Log.d("ErrorNearestCity",t.localizedMessage)
        presenter!!.showError(error)
    }

}