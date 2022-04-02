package com.olympos.tripbook.src.tripcourse_view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.gson.Gson
import com.olympos.tripbook.config.BaseFragment
import com.olympos.tripbook.databinding.FragmentTripcourseViewBinding
import com.olympos.tripbook.src.tripcourse.TRUE
import com.olympos.tripbook.src.tripcourse.model.Card
import com.olympos.tripbook.src.tripcourse_view.model.*

class TripcourseViewFragment : BaseFragment() , TripResponseView {

    lateinit var binding: FragmentTripcourseViewBinding

    private lateinit var cardRVAdapterView : RVCardAdapter_view
    private val gson = Gson()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTripcourseViewBinding.inflate(inflater, container, false)

        val bundle = arguments

        val tripIdx : String? = bundle!!.getString("tripIdx")
        getTrip(tripIdx.toString())

        initRecyclerView()

        return binding.root
    }

    private fun initRecyclerView() {
        cardRVAdapterView = RVCardAdapter_view(requireContext())
        binding.tripcourseViewRecyclerview.adapter = cardRVAdapterView

        binding.tripcourseViewRecyclerview.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        binding.tripcourseViewRecyclerview.addItemDecoration(RVCardViewAdapterDecoration())

        cardRVAdapterView.setItemClickListener(object : RVCardAdapter_view.CardClickListener{
            override fun onItemClick(card: Card) {
                startTripcourseRecordViewActivity(card)
            }
        })
    }

    private fun getTrip(tripIdx : String){
        val viewService = ViewService()
        viewService.setTripResponseView(this)
        viewService.getTrip(tripIdx)
    }

    private fun startTripcourseRecordViewActivity(card : Card) {
        val intent = Intent(requireContext(), TripcourseRecordViewActivity::class.java)
        startActivity(intent)
    }

    override fun onGetCardsLoading() {
        //todo 로딩바 생성
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onGetCardsSuccess(cards: ArrayList<Card>) {
        //todo 로딩바 제거
        Log.d("ServerTripCards", cards.toString())
        serverTripCards.clear()
        serverTripCards.addAll(cards)
        for(i in 0..serverTripCards.size) {
            if(serverTripCards[i].hasData == TRUE) {
                filledCards.add(serverTripCards[i])
            }
        }
        Log.d("filledCards", filledCards.toString())
        cardRVAdapterView.notifyDataSetChanged()
    }

    override fun onGetCardsFailure(code: Int, message: String) {
        //todo 로딩바 제거
//        Toast.makeText(this, "$code : $message", Toast.LENGTH_LONG).show()
    }
}