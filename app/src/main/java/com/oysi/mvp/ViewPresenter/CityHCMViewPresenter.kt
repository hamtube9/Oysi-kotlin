package com.oysi.mvp.ViewPresenter

import com.oysi.model.district.DistrictResponse
import com.oysi.mvp.View


interface CityHCMViewPresenter : View {
    fun loadHCMSuccess(response : DistrictResponse)
}