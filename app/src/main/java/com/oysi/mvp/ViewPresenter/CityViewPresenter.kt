package com.oysi.mvp.ViewPresenter

import com.oysi.model.city.CityResponse
import com.oysi.mvp.View

interface CityViewPresenter : View{
    fun getDataCityResponse(response : CityResponse)
}