package com.oysi.mvp.presenter

import android.util.Log
import com.oysi.model.ranking.RankingResponse
import com.oysi.mvp.Presenter
import com.oysi.mvp.View
import com.oysi.mvp.ViewPresenter.RankingViewPresenter
import com.oysi.service.RetrofitService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class RankingPresenter :Presenter{
    var presenter: RankingViewPresenter? = null
    var compositeDisposable = CompositeDisposable()
    override fun attachView(view: View) {
        presenter = view as RankingViewPresenter
    }

    override fun compositeDisposable() {
        compositeDisposable.dispose()
    }

    fun getDataRanking(key : String){
        compositeDisposable.add(RetrofitService.getAPIService().getRanking(key).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::getDataSuccess){t-> getDataFail(t,"Load Data Ranking Fail")})
    }

    fun getDataSuccess(response: RankingResponse){
        presenter!!.onLoadRankingSucces(response)
    }

    fun getDataFail(t:Throwable,error:String){
        presenter!!.showError(error)
        Log.d("errorRanking",t.localizedMessage)
    }
}