package com.oysi.mvp.presenter

import com.oysi.model.district.DistrictResponse
import com.oysi.mvp.Presenter
import com.oysi.mvp.View
import com.oysi.mvp.ViewPresenter.CityHaNoiViewPresenter
import com.oysi.service.RetrofitService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CitiyHanoiPresenter : Presenter{
    private var presenter : CityHaNoiViewPresenter?=null
    private var compositeDisposable = CompositeDisposable()
    override fun attachView(view: View) {
       presenter=view as CityHaNoiViewPresenter
    }

    override fun compositeDisposable() {
      compositeDisposable.dispose()
    }

    fun loadDataDistrict(county : String,state : String, city: String, key: String){
        compositeDisposable.add(RetrofitService.getAPIService().getHanoi(county,state,city,key)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe (this::loadDataDistrictSuccess){t-> loadDataDistrictFail(t,"Load District Fail")  }
        )
    }

    private fun loadDataDistrictSuccess(response: DistrictResponse){
        presenter!!.loadDistrictSuccess(response)
    }

    private fun loadDataDistrictFail(t:Throwable,error:String){
        presenter!!.showError(error)
    }

}