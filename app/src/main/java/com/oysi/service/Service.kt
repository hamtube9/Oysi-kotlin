package com.oysi.service

import com.oysi.model.country.CountryResponse
import com.oysi.model.district.DistrictResponse
import com.oysi.model.state.StateResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface Service {
    @GET("v2/countries")
    fun getContries(@Query("key")key:String): Observable<CountryResponse>

    @GET("v2/states")
    fun getState(@Query("country") country:String,
        @Query("key")key:String): Observable<StateResponse>

    //api.airvisual.com/v2/city?city=Hanoi&state=Hanoi&country=Vietnam&key=3564653d-5190-4ee6-9236-7cb733f6f27c

    @GET("v2/city")
    fun getDistrict(@Query("country") country:String, @Query("state")state : String, @Query("city")city : String,
                 @Query("key")key:String): Observable<DistrictResponse>
}