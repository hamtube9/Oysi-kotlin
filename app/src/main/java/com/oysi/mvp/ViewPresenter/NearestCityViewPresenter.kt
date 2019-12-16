package com.oysi.mvp.ViewPresenter

import com.oysi.model.nearestcity.NearestCityResponse
import com.oysi.mvp.View

interface NearestCityViewPresenter: View {
    fun getDataNearestCitySuccess(response : NearestCityResponse)
}