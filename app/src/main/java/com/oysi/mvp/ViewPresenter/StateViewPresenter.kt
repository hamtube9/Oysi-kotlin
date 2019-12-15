package com.oysi.mvp.ViewPresenter

import com.oysi.model.state.StateResponse
import com.oysi.mvp.View

interface StateViewPresenter : View {
    fun onLoadStateSuccess(response: StateResponse)
}