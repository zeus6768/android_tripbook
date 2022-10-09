package com.olympos.tripbook.src.home

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.olympos.tripbook.config.BaseFragment
import com.olympos.tripbook.databinding.FragmentMainHomeBinding
import com.olympos.tripbook.src.home.view.HomeRVAdapter
import com.olympos.tripbook.src.trip.model.Trip
import com.olympos.tripbook.src.tripcourse.model.TripCourse
import com.olympos.tripbook.src.tripcourse.view.GetTripCoursesView
import com.olympos.tripbook.utils.ApplicationClass.Companion.DateUnit
import com.olympos.tripbook.utils.ApplicationClass.Companion.parseDateToKorean

class HomeFragment(val trip: Trip) : BaseFragment() {

    lateinit var binding: FragmentMainHomeBinding

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainHomeBinding.inflate(inflater, container, false)

        binding.mainPeriodTv.text = parseDateToKorean(trip.departureDate, DateUnit.DAY) + " ~ " + parseDateToKorean(trip.departureDate, DateUnit.DAY)
        binding.mainTitleTv.text = trip.tripTitle

        return binding.root
    }

}