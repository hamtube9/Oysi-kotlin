package com.oysi.mvp.presenter

import android.util.Log
import com.oysi.model.district.DistrictResponse
import com.oysi.mvp.Presenter
import com.oysi.mvp.View
import com.oysi.mvp.ViewPresenter.CityCauGiayViewPresenter
import com.oysi.service.RetrofitService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CityCauGiayPresenter : Presenter {
    private var presenter : CityCauGiayViewPresenter?=null
    private var compositeDisposable = CompositeDisposable()
    override fun attachView(view: View) {
        presenter = view as CityCauGiayViewPresenter
    }

    override fun compositeDisposable() {
        compositeDisposable.dispose()
    }

    fun getDataCauGiay(key:String){
        compositeDisposable.add(
            RetrofitService.getAPIService().getHanoi("Vietnam","Hanoi","Cau Giay",key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe (this::loadDataCauGiaySuccess){t-> loadDataCauGiayFail(t,"Load District Fail")  }
        )
    }

    private fun loadDataCauGiayFail(t: Throwable, error: String) {
    presenter!!.showError(error)
        Log.d("errorCauGiay",t.localizedMessage)
    }

    private fun loadDataCauGiaySuccess(response: DistrictResponse ){
        presenter!!.getDataCauGiaySuccess(response)
    }
}