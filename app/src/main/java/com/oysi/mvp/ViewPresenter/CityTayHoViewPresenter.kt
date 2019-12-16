package com.oysi.mvp.ViewPresenter

import com.oysi.model.district.DistrictResponse
import com.oysi.mvp.View

interface CityTayHoViewPresenter:View{
    fun getDataTayHoSuccess(response: DistrictResponse)
}