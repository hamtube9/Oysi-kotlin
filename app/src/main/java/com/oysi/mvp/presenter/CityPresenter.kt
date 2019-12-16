package com.oysi.mvp.presenter

import android.util.Log
import com.oysi.model.city.CityResponse
import com.oysi.mvp.Presenter
import com.oysi.mvp.View
import com.oysi.mvp.ViewPresenter.CityViewPresenter
import com.oysi.service.RetrofitService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit

class CityPresenter :Presenter{
    var presenter : CityViewPresenter?=null
    val compositeDisposable = CompositeDisposable()
    override fun attachView(view: View) {
        presenter=view as CityViewPresenter
    }

    override fun compositeDisposable() {
        compositeDisposable.dispose()
    }

    fun getCityResponse(state:String,country:String,key:String){
        compositeDisposable.add(RetrofitService.getAPIService().getCities(state,country,key)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::getCitySuccess){t->getCityFail(t,"Load City Failed")})
    }

    private fun getCitySuccess(response : CityResponse){
        presenter!!.getDataCityResponse(response)
    }

    private fun getCityFail(t:Throwable,error:String){
        presenter!!.showError(error)
        Log.d("errorCity",t.localizedMessage)
    }

}