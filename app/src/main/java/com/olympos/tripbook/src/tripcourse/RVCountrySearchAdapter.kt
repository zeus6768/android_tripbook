package com.olympos.tripbook.src.tripcourse

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.olympos.tripbook.databinding.ItemTripcourseCardBaseEmptyBinding
import com.olympos.tripbook.databinding.ItemTripcourseSelectCountryListsBinding
import com.olympos.tripbook.src.tripcourse.model.Country

class RVCountrySearchAdapter() : RecyclerView.Adapter<RVCountrySearchAdapter.CountryViewHolder>() {

    private var searchResult = ArrayList<Country>()
    private lateinit var myItemClickListener : MyItemClickListener

    interface MyItemClickListener{
        fun onItemClick(country: Country)
    }

    fun setCountryClickListener(itemClickListener : MyItemClickListener) {
        myItemClickListener = itemClickListener
    }

    inner class CountryViewHolder(val binding : ItemTripcourseSelectCountryListsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(country: Country) {
            //도시 바인딩
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : CountryViewHolder{
        val binding = ItemTripcourseSelectCountryListsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CountryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RVCountrySearchAdapter.CountryViewHolder, position: Int) {
        holder.bind(searchResult[position])
        holder.itemView.setOnClickListener {
            myItemClickListener.onItemClick(searchResult[position])
        }
    }

    override fun getItemCount(): Int = searchResult.size

    fun setSearchList(countrys : ArrayList<Country>) {
        searchResult =  countrys
    }
}