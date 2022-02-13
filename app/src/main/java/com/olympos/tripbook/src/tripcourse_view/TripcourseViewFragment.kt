package com.olympos.tripbook.src.tripcourse_view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.olympos.tripbook.config.BaseFragment
import com.olympos.tripbook.databinding.FragmentHomeBinding
import com.olympos.tripbook.databinding.FragmentTripcourseViewBinding
import com.olympos.tripbook.src.tripcourse.model.Card
import com.olympos.tripbook.src.tripcourse_view.model.TripResponseView
import com.olympos.tripbook.src.tripcourse_view.model.ViewService

class TripcourseViewFragment : BaseFragment() , TripResponseView {

    lateinit var binding: FragmentTripcourseViewBinding

    private lateinit var cardRVAdapter : RVCardAdapter

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
        cardRVAdapter = RVCardAdapter(requireContext())
        binding.tripcourseViewRecyclerview.adapter = cardRVAdapter

        binding.tripcourseViewRecyclerview.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        binding.tripcourseViewRecyclerview.addItemDecoration(RVCardViewAdapterDecoration())

        cardRVAdapter.setItemClickListener(object : RVCardAdapter.CardClickListener{
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