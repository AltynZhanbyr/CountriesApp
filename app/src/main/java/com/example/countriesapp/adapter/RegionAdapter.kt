package com.example.countriesapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.countriesapp.R
import com.example.countriesapp.databinding.RegionItemBinding
import com.example.countriesapp.domain.CountryListItem

class RegionAdapter(private val context:Context,private val onArrowClickListener: CountryAdapter.ArrowClickListener):RecyclerView.Adapter<RegionAdapter.RegionViewHolder>(){

    private var regionList:List<String> = listOf()
    private var map = mutableMapOf<String,List<CountryListItem>>()

    class RegionViewHolder(view:View):ViewHolder(view){
        private val binding = RegionItemBinding.bind(view)

        fun bind(region: String,map: MutableMap<String,List<CountryListItem>>,context:Context,onArrowClickListener: CountryAdapter.ArrowClickListener){
            var localList = mutableListOf<CountryListItem>()
            binding.regionName.text = region

            val adapter = CountryAdapter(context,onArrowClickListener)
            binding.countryList.adapter = adapter

            map.forEach {map->
                if(map.key == region){
                    adapter.setCountryList(map.value)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.region_item, parent,false)
        return RegionViewHolder(view)
    }

    override fun getItemCount(): Int {
        return regionList.size
    }

    override fun onBindViewHolder(holder: RegionViewHolder, position: Int) {
        val region = regionList[position]
        holder.bind(region,map,context, onArrowClickListener)
    }

    fun setList(list:List<String>){
        this.regionList = list
        notifyDataSetChanged()
    }

    fun setMap(myMap:MutableMap<String,List<CountryListItem>>){
        this.map = myMap
        notifyDataSetChanged()
    }
}
