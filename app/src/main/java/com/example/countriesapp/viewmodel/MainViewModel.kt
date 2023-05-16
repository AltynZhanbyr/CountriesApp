package com.example.countriesapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.countriesapp.api.CountriesApi
import com.example.countriesapp.domain.CountryList
import com.example.countriesapp.domain.CountryListItem
import com.example.countriesapp.domain.RegionListItem
import kotlinx.coroutines.launch
import retrofit2.Retrofit

class MainViewModel(private var response: CountriesApi): ViewModel() {

    val countryList = MutableLiveData<MutableMap<String,List<CountryListItem>>>()
    val regionList = MutableLiveData<List<RegionListItem>>()

    fun getAll(){

    }

    fun getRegions(){
        viewModelScope.launch {
            val regions = HashSet<RegionListItem>()
            val result = response.getAllRegions()
            if(result.isSuccessful){
                result.body()?.forEach {
                    regions.add(it)
                }

                regionList.postValue(regions.toList())
            }
        }
    }

    fun getCountriesByRegion(list:List<RegionListItem>){
        viewModelScope.launch{
            val map = mutableMapOf<String,List<CountryListItem>>()
            list.forEach {
                val result = response.getCountriesByRegion(it.region)
                if(result.isSuccessful){
                    map[it.region] = result.body()!!
                }
            }
            countryList.postValue(map)
        }
    }

    class ViewModelFactory(private var response: CountriesApi):ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(MainViewModel::class.java)){
                return MainViewModel(response) as T
            }
            else
                throw Exception("My Error")
        }
    }
}