package com.oysi.mvp.presenter

import android.util.Log
import com.oysi.model.district.DistrictResponse
import com.oysi.mvp.Presenter
import com.oysi.mvp.View
import com.oysi.mvp.ViewPresenter.CityTayHoViewPresenter
import com.oysi.service.RetrofitService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CityTayHoPresenter :Presenter{
    private var presenter : CityTayHoViewPresenter?=null
    private var compositeDisposable = CompositeDisposable()
    override fun attachView(view: View) {
        presenter = view as CityTayHoViewPresenter
    }

    override fun compositeDisposable() {
        compositeDisposable.dispose()
    }

    fun getDataTayHo(key:String){
        compositeDisposable.add(RetrofitService.getAPIService().getTayHo("Vietnam","Hanoi","Tay Ho",key)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::loadDataTayHoSuccess){t -> loadDataTayHoFail(t,"Failed Load Data Tay Ho")})
    }

    private fun loadDataTayHoSuccess(response: DistrictResponse){
        presenter!!.getDataTayHoSuccess(response)
    }

    private fun loadDataTayHoFail(t:Throwable,error:String){
        presenter!!.showError(error)
        Log.d("ErrorTayHo",t.localizedMessage)
    }

}