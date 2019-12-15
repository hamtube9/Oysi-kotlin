package com.oysi.mvp.ViewPresenter

import com.oysi.model.country.CountryResponse
import com.oysi.mvp.View

interface CountryViewPresenter : View {
    fun onLoadCountrySuccess(response : CountryResponse)
}