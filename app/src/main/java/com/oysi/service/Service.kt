package com.oysi.service

import com.oysi.model.city.CityResponse
import com.oysi.model.country.CountryResponse
import com.oysi.model.district.DistrictResponse
import com.oysi.model.nearestcity.NearestCityResponse
import com.oysi.model.state.StateResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface Service {
    //data country
    @GET("v2/countries")
    fun getContries(@Query("key") key: String): Observable<CountryResponse>

    //data state
    @GET("v2/states")
    fun getState(
        @Query("country") country: String,
        @Query("key") key: String
    ): Observable<StateResponse>

    //data Hanoi
    @GET("v2/city")
    fun getHanoi(
        @Query("country") country: String, @Query("state") state: String, @Query("city") city: String,
        @Query("key") key: String
    ): Observable<DistrictResponse>

    //data CauGiay
    @GET("v2/city")
    fun getCauGiay(
        @Query("country") country: String, @Query("state") state: String, @Query("city") city: String,
        @Query("key") key: String
    ): Observable<DistrictResponse>


    //data TayHo
    @GET("v2/city")
    fun getTayHo(
        @Query("country") country: String, @Query("state") state: String, @Query("city") city: String,
        @Query("key") key: String
    ): Observable<DistrictResponse>



    //data HCM
    @GET("v2/city")
    fun getHCM(
        @Query("country") country: String, @Query("state") state: String, @Query("city") city: String,
        @Query("key") key: String
    ): Observable<DistrictResponse>


    //  data location
    @GET("v2/nearest_city")
    fun getCurrentLocation(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("key") key: String
    ): Observable<NearestCityResponse>

    // "api.airvisual.com/v2/cities?state=New%20York&country=USA&key={{YOUR_API_KEY}}"
    @GET("v2/cities")
    fun getCities(@Query("state")state:String,@Query("country") country: String,
        @Query("key") key: String): Observable<CityResponse>
}