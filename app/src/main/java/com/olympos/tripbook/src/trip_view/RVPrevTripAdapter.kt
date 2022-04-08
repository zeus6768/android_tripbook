package com.olympos.tripbook.src.trip_view

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.olympos.tripbook.databinding.ItemTripViewPrevTripBinding
import com.olympos.tripbook.src.tripcourse.model.Card

class RVPrevTripAdapter: RecyclerView.Adapter<RVPrevTripAdapter.TripViewHolder>() {

    interface TripClickListener {
        fun onItemClick(card: Card)
    }

    inner class TripViewHolder(val binding: ItemTripViewPrevTripBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind() {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}