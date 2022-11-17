package com.olympos.tripbook.src.home

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.olympos.tripbook.config.BaseFragment
import com.olympos.tripbook.databinding.FragmentMainHomeBinding
import com.olympos.tripbook.src.home.view.HomeRVAdapter
import com.olympos.tripbook.src.trip.model.Trip
import com.olympos.tripbook.src.tripcourse.controller.TripCourseApiController
import com.olympos.tripbook.src.tripcourse.model.TripCourse
import com.olympos.tripbook.src.tripcourse.view.GetTripCoursesView
import com.olympos.tripbook.utils.ApplicationClass.Companion.DateUnit
import com.olympos.tripbook.utils.ApplicationClass.Companion.dateToKorean
import com.olympos.tripbook.utils.ApplicationClass.Companion.generatePeriod

class HomeFragment(val trip: Trip) : BaseFragment(), GetTripCoursesView {

    private val tripCourseApiController = TripCourseApiController()

    lateinit var binding: FragmentMainHomeBinding
    lateinit var mainActivity: MainActivity

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMainHomeBinding.inflate(inflater, container, false)

        binding.mainPeriodTv.text = generatePeriod(trip.departureDate, trip.arrivalDate)
        binding.mainTitleTv.text = trip.tripTitle

        tripCourseApiController.setTripCoursesView(this)
        tripCourseApiController.getTripCourses(trip.tripIdx)

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    private fun initRecyclerView(tripCourses: ArrayList<TripCourse>) {
        val homeRVAdapter = HomeRVAdapter(mainActivity, tripCourses)
        binding.mainCourseRv.adapter = homeRVAdapter
        binding.mainCourseRv.layoutManager = LinearLayoutManager(mainActivity)
    }

    override fun onGetTripCoursesSuccess(result: ArrayList<TripCourse>) {
        Log.d("HomeFragment", "onGetTripCoursesSuccess()")
        initRecyclerView(result)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onGetTripCoursesFailure(code: Int, message: String) {
        Log.e("HomeFragment", "onGetTripCoursesFailure() status code $code")
        val emptyTripCourse = tripCourseApiController.generateEmptyTripCourseList()
        initRecyclerView(emptyTripCourse)
    }


}