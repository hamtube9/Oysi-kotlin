package com.oysi.mvp.presenter

import android.util.Log
import com.oysi.model.district.DistrictResponse
import com.oysi.mvp.Presenter
import com.oysi.mvp.View
import com.oysi.mvp.ViewPresenter.CityHCMViewPresenter
import com.oysi.service.RetrofitService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CityHCMPresenter : Presenter {
    var presenter : CityHCMViewPresenter?=null
    val compositeDisposable = CompositeDisposable()

    override fun attachView(view: View) {
        presenter = view as CityHCMViewPresenter
    }

    override fun compositeDisposable() {
        compositeDisposable.dispose()
    }

     fun getHCM(country : String,state : String, city: String, key: String){
        compositeDisposable.add(RetrofitService.getAPIService().getHCM(country,state,city,key)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::getData){t-> getDataFail(t,"Get Data Ho Chi Minh Fail")})
    }

    private fun getData(response: DistrictResponse){
        presenter!!.loadHCMSuccess(response)
    }

    private fun getDataFail(t:Throwable,error:String){
        presenter!!.showError(error)
        Log.d("ErrorHCM",t.localizedMessage)
    }
}