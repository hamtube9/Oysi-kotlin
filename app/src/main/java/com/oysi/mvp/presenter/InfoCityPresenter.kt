package com.oysi.mvp.presenter

import android.util.Log
import com.oysi.model.district.DistrictResponse
import com.oysi.mvp.Presenter
import com.oysi.mvp.View
import com.oysi.mvp.ViewPresenter.InfoCityViewPresenter
import com.oysi.service.RetrofitService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class InfoCityPresenter : Presenter {
    private var presenter: InfoCityViewPresenter? = null
    private val compositeDisposable = CompositeDisposable()
    override fun attachView(view: View) {
        presenter = view as InfoCityViewPresenter
    }

    override fun compositeDisposable() {
        compositeDisposable.dispose()
    }

    fun getDataInfoCity(country:String,state : String , city : String , key : String){
        compositeDisposable.add(RetrofitService.getAPIService().getInfoCity(country, state, city, key)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::getDataSuccess){t-> getDataFail(t,"Failed Load Info City")})
    }

    fun getDataSuccess(response : DistrictResponse){
        presenter!!.getDataInfoCitySuccess(response)
    }

    fun getDataFail(t:Throwable,error:String){
        Log.d("errorInfo",t.localizedMessage)
        presenter!!.showError(error)
    }

}