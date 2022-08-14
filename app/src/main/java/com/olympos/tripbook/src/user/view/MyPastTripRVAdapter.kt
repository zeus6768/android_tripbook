package com.olympos.tripbook.src.user.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.olympos.tripbook.databinding.ItemUserMyPastTripBinding
import com.olympos.tripbook.src.trip.model.Trip

class MyPastTripRVAdapter(private val tripList : ArrayList<Trip>): RecyclerView.Adapter<MyPastTripRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemUserMyPastTripBinding = ItemUserMyPastTripBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tripList[position])
    }

    override fun getItemCount(): Int {
        return tripList.size
    }

    inner class ViewHolder(private val binding: ItemUserMyPastTripBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(trip: Trip) {
            binding.mypasttripDateTv.text = trip.departureDate
            binding.mypasttripTitleTv.text = trip.tripTitle
        }
    }
}