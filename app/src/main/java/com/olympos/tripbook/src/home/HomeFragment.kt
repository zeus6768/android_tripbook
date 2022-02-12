package com.olympos.tripbook.src.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.olympos.tripbook.config.BaseFragment
import com.olympos.tripbook.databinding.FragmentHomeBinding
import com.olympos.tripbook.src.home.model.CardsView
import com.olympos.tripbook.src.home.model.HomeService
import com.olympos.tripbook.src.tripcourse.model.Card
import com.olympos.tripbook.src.tripcourse.model.CardService

class HomeFragment : BaseFragment() , CardsView {
    lateinit var binding: FragmentHomeBinding

    private lateinit var cardRVAdapter : RVCardAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        initRecyclerView()

//        if(여행이 있는 경우) 여행의 tripIdx를 가져옴
        if(true) { //여행이 있는 경우
            val homeService = HomeService()
            homeService.setCardsView(this)

            val tripIdx = 0 //가장 최근 여행의 tripIdx를 가져옴
            getTrip(tripIdx)

            binding.homeEmptyLayout.visibility = View.GONE
            binding.homeFillRecyclerview.visibility = View.VISIBLE
        }
        else { //여행이 없는 경우
            binding.homeEmptyLayout.visibility = View.VISIBLE
            binding.homeFillRecyclerview.visibility = View.GONE
        }

        return binding.root
    }

    private fun initRecyclerView() {
        cardRVAdapter = com.olympos.tripbook.src.home.RVCardAdapter(requireContext())
        binding.homeFillRecyclerview.adapter = cardRVAdapter
    }

    private fun getTrip(tripIdx : Int){
        val homeService = HomeService()
        homeService.setCardsView(this)
        homeService.getTrip(tripIdx)
    }

    override fun onGetCardsLoading() {
        //todo 로딩바 생성
    }

    override fun onGetCardsSuccess(cards: ArrayList<Card>) {
        //todo 로딩바 제거
        cardRVAdapter.setCards(cards)
    }

    override fun onGetCardsFailure(code: Int, message: String) {
        //todo 로딩바 제거
//        Toast.makeText(this, "$code : $message", Toast.LENGTH_LONG).show()
    }
}