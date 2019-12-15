package com.oysi.mvp


interface Presenter  {
    fun attachView(view : View)
    fun compositeDisposable()
}