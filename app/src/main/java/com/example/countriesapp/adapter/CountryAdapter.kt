package com.example.countriesapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.countriesapp.R
import com.example.countriesapp.databinding.CountryItemBinding
import com.example.countriesapp.domain.CountryListItem

class CountryAdapter(private val context:Context,private val onArrowClickListener: ArrowClickListener): RecyclerView.Adapter<CountryAdapter.CountryViewHolder>(){

    interface ArrowClickListener{
        fun onArrowClickListener(linerLayout:LinearLayout,imageView: ImageView)
    }

    private var countryList:List<CountryListItem> = listOf()

    class CountryViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val binding = CountryItemBinding.bind(view)

        fun bind(country: CountryListItem, context: Context, onArrowClickListener: ArrowClickListener){
            binding.countryName.text = country.name.common
            binding.countryCapital.text = country?.capital?.get(0)?:"None"
            binding.area.text = country.area.toString()
            binding.population.text = country.population.toString()

            binding.hideButton.setOnClickListener {
                onArrowClickListener.onArrowClickListener(binding.moreInfoView,binding.hideButton)
            }

            Glide.with(context)
                .load(country.flags.png)
                .into(binding.countryFlag)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.country_item, parent,false)
        return CountryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val country = countryList[position]
        holder.bind(country,context, onArrowClickListener)
    }

    fun setCountryList(list:List<CountryListItem>){
        this.countryList = list
        notifyDataSetChanged()
    }
}
