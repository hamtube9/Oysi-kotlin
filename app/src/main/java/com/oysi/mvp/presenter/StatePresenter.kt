package com.oysi.mvp.presenter

import android.util.Log
import com.oysi.model.state.StateResponse
import com.oysi.mvp.Presenter
import com.oysi.mvp.View
import com.oysi.mvp.ViewPresenter.StateViewPresenter
import com.oysi.service.RetrofitService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class StatePresenter : Presenter {
   private var presenter : StateViewPresenter?=null
    private val compositeDisposable = CompositeDisposable()
    override fun attachView(view: View) {
        presenter = view as StateViewPresenter
    }

    override fun compositeDisposable() {
        compositeDisposable.dispose()
    }

    fun loadState(country : String, key : String){
        compositeDisposable.add(RetrofitService.getAPIService().getState(country, key).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe (this::onLoadState){t-> onLoadStateFail(t,"Load State Failed")  })
    }

    private fun onLoadState(response: StateResponse){
        presenter!!.onLoadStateSuccess(response)
    }

    private fun onLoadStateFail(t:Throwable,error:String){
        Log.d("ErrorState",t.localizedMessage)
        presenter!!.showError(error)
    }
}