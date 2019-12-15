package com.oysi.mvp.presenter

import android.util.Log
import com.oysi.model.country.CountryResponse
import com.oysi.mvp.Presenter
import com.oysi.mvp.View
import com.oysi.mvp.ViewPresenter.CountryViewPresenter
import com.oysi.service.RetrofitService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CountryPresenter : Presenter {

    private var presenter: CountryViewPresenter? = null
    private var compositeDisposable = CompositeDisposable()

    override fun attachView(view: View) {
        this.presenter = view as CountryViewPresenter
    }

    override fun compositeDisposable() {
        compositeDisposable.dispose()
    }

    fun getCountry(key: String) {
        compositeDisposable.add(RetrofitService.getAPIService().getContries(key)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::getCountrySuccess) { t ->
                getCountryFailed(
                    t,
                    "Load Country Failed"
                )
            })
    }

    private fun getCountrySuccess(response: CountryResponse) {
        presenter!!.onLoadCountrySuccess(response)
    }

    private fun getCountryFailed(t: Throwable, error: String) {
        Log.d("ErrorCountry", t.localizedMessage)
        presenter!!.showError(error)
    }
}