package com.oysi.mvp.ViewPresenter

import com.oysi.model.ranking.RankingResponse
import com.oysi.mvp.View

interface RankingViewPresenter : View{
    fun onLoadRankingSucces( response: RankingResponse)
}