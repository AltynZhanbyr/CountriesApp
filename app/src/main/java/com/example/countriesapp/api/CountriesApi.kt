package com.example.countriesapp.api

import com.example.countriesapp.domain.CountryListItem
import com.example.countriesapp.domain.RegionListItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CountriesApi {
    @GET("all?fields=region")
    suspend fun getAllRegions():Response<List<RegionListItem>>

    @GET("region/{region}")
    suspend fun getCountriesByRegion(@Path ("region") region:String):Response<List<CountryListItem>>
}