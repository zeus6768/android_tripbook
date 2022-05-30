package com.olympos.tripbook.src.tripcourse_view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.gson.Gson
import com.olympos.tripbook.config.BaseFragment
import com.olympos.tripbook.databinding.FragmentTripcourseViewBinding
import com.olympos.tripbook.src.trip.model.Trip
import com.olympos.tripbook.src.tripcourse.TripcourseActivity
import com.olympos.tripbook.src.tripcourse.model.Card
import com.olympos.tripbook.src.tripcourse_view.model.*
import com.olympos.tripbook.utils.*

class TripcourseViewFragment : BaseFragment() , RecentTripResponseView, RecentTripCardsResponseView {

    lateinit var binding: FragmentTripcourseViewBinding

    private lateinit var cardRVAdapterView : RVCardAdapter_view

    private var thisTrip: Trip? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTripcourseViewBinding.inflate(inflater, container, false)

        initRecyclerView()

        initView()

        return binding.root
    }

    private fun initRecyclerView() {
        cardRVAdapterView = RVCardAdapter_view(requireContext())
        binding.tripcourseViewRecyclerview.adapter = cardRVAdapterView

        binding.tripcourseViewRecyclerview.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        binding.tripcourseViewRecyclerview.addItemDecoration(RVCardViewAdapterDecoration())

        cardRVAdapterView.setItemClickListener(object : RVCardAdapter_view.CardClickListener{
            override fun onItemClick() {
                startTripcourseRecordViewActivity()
            }
        })
    }

    private fun initView() {

        binding.tripcourseViewTitlebarLayout.setOnLongClickListener(OnLongClickListener {

            if( thisTrip == null ) {
                Toast.makeText(context, "비어있는 여행", Toast.LENGTH_LONG).show()
            }
            val intent = Intent(activity, TripcourseActivity::class.java)
            val gson = Gson()
            //trip 정보
            intent.putExtra("tripData", gson.toJson(thisTrip))
            //tripcourse 정보
            intent.putExtra("tripcourseData", gson.toJson(serverTripCards))
            startActivity(intent)

            true //true 설정
        })

        getRecentTrip()
        getRecentTripCards()
    }

    private fun getRecentTrip() {
        val viewService = ViewService()
        viewService.setRecentTripResponseView(this)
        viewService.getRecentTrip()
    }

    private fun getRecentTripCards() {
        val viewService = ViewService()
        viewService.setRecentTripCardsResponseView(this)
        viewService.getRecentTripCards()
    }

    private fun startTripcourseRecordViewActivity() {
        val intent = Intent(activity, TripcourseRecordViewActivity::class.java)
        startActivity(intent)
    }

    override fun onGetRecentTripLoading() {
        Log.d("MainLoading", "onGetRecentTripLoading Start")
    }

    override fun onGetRecentTripSuccess(trip: Trip) {
        thisTrip = trip

        //출발일
        val dDate = trip.departureDate.split("-")
        val dYear = dDate[0].substring(2,4)
        val dMonth = dDate[1]
        val dDay = dDate[2]
        saveDepartureYear(dYear.toInt())
        saveDepartureMonth(dMonth.toInt())
        saveDepartureDay(dDay.toInt())

        //도착일
        val aDate = trip.arrivalDate.split("-")
        val aYear = aDate[0].substring(2,4)
        val aMonth = aDate[1]
        val aDay = aDate[2]
        saveArrivalYear(aYear.toInt())
        saveArrivalMonth(aMonth.toInt())
        saveArrivalDay(aDay.toInt())

        val period = dYear + "년 " + dMonth + "월 " + dDay + "일 ~ " + aYear + "년 " + aMonth + "월 " + aDay + "일"
        //여행 제목 띄우기
        binding.tripcourseViewTitlebarPeriodTv.text = period
        binding.tripcourseViewTitlebarTitleTv.text = trip.tripTitle
    }

    override fun onGetRecentTripFailure(code: Int, message: String) {
        Toast.makeText(context, "최근 여행을 가져오는데 문제가 발생했습니다.", Toast.LENGTH_LONG).show()
    }

    override fun onGetRecentTripCardsLoading() {
        Log.d("MainLoading", "onGetRecentTripCardsLoading Start")
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onGetRecentTripCardsSuccess(cards: ArrayList<Card>) {
        Log.d("ServerRecentTripCards", cards.toString())
        serverTripCards = cards

        filledCards.clear()

        for(i in 0 until serverTripCards.size) {
            if(serverTripCards[i].title != "NONE") {
                filledCards.add(serverTripCards[i])
            }
        }
        cardRVAdapterView.notifyDataSetChanged()
    }

    override fun onGetRecentTripCardsFailure(code: Int, message: String) {
        Toast.makeText(context, "최근 여행을 가져오는데 문제가 발생했습니다.", Toast.LENGTH_LONG).show()
    }
}