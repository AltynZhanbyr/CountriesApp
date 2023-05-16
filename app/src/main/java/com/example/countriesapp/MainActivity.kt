package com.example.countriesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.countriesapp.adapter.CountryAdapter
import com.example.countriesapp.adapter.RegionAdapter
import com.example.countriesapp.api.CountriesApi
import com.example.countriesapp.api.RetrofitHelper
import com.example.countriesapp.databinding.ActivityMainBinding
import com.example.countriesapp.viewmodel.MainViewModel

class MainActivity : AppCompatActivity(), CountryAdapter.ArrowClickListener {

    private var binding:ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)


        val response = RetrofitHelper.getInstance().create(CountriesApi::class.java)
        val viewModelProvider = MainViewModel.ViewModelFactory(response)
        val viewModel = ViewModelProvider(this, viewModelProvider)[MainViewModel::class.java]

        val adapter = RegionAdapter(this,this)
        binding?.list?.adapter = adapter

        viewModel.getRegions()

        viewModel.regionList.observe(this){
            if(it!=null){
                viewModel.getCountriesByRegion(it)
            }
        }

        viewModel?.countryList?.observe(this){
            if(it!=null){
                val regionList = mutableListOf<String>()
                adapter.setList(it.keys.toList())
                adapter.setMap(it)
            }
        }
    }

    override fun onResume() {
        super.onResume()


    }

    override fun onArrowClickListener(linerLayout: LinearLayout,imageView: ImageView) {
        if(linerLayout.isVisible) {
            linerLayout.visibility = View.GONE
            imageView.setImageResource(R.drawable.arrow_down_icon)
        }
        else {
            linerLayout.visibility = View.VISIBLE
            imageView.setImageResource(R.drawable.arrow_up_icon)
        }
    }
}