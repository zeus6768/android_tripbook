package com.olympos.tripbook.src.home.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.olympos.tripbook.src.home.HomeFragment
import com.olympos.tripbook.src.trip.model.Trip

class MainVPAdapter(fragmentActivity: FragmentActivity, private val tripList: ArrayList<Trip>): FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = tripList.size
    override fun createFragment(position: Int): Fragment = HomeFragment(tripList[position])
}