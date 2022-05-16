package com.olympos.tripbook.src.tripcourse

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior.getTag
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.Gson
import com.kakao.sdk.common.util.Utility
import com.olympos.tripbook.R
import com.olympos.tripbook.config.BaseActivity
import com.olympos.tripbook.config.BaseDialog
import com.olympos.tripbook.databinding.ActivityTripcourseBinding
import com.olympos.tripbook.src.home.MainActivity
import com.olympos.tripbook.src.trip.model.Trip
import com.olympos.tripbook.src.trip.model.TripGetProcess
import com.olympos.tripbook.src.trip.model.TripService
import com.olympos.tripbook.src.tripcourse.model.*
import com.olympos.tripbook.src.tripcourse.model.Card
import com.olympos.tripbook.src.tripcourse.model.CardService
import com.olympos.tripbook.src.tripcourse.model.CardsView
import com.olympos.tripbook.src.tripcourse.model.ServerView
import com.olympos.tripbook.utils.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class TripcourseActivity : BaseActivity(), PostCardView, ServerView {

    lateinit var binding : ActivityTripcourseBinding
    private var tripData = Trip()

    private lateinit var cardRVAdapter : RVCardAdapter

    private var cardIdx : Int = 1
    private var tripIdx : Int = 0

    private var card = Card()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTripcourseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initRecyclerView()
        addDefaultCard()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        initRecyclerView()
        cardRVAdapter.notifyDataSetChanged()
    }

    override fun onOKClicked() {
        super.onOKClicked()
        deleteTrip(tripIdx)
        startMainActivity()
    }

    private fun initView() {
        //topbar layout view randering
        binding.tripcourseTopbarLayout.topbarTitleTv.setText(R.string.tripcourse_title)
        binding.tripcourseTopbarLayout.topbarSubbuttonIb.setImageResource(R.drawable.btn_base_check_black)

        val gson : Gson = Gson()
        tripData = gson.fromJson(intent.getStringExtra("tripData"), Trip::class.java)
        //여행 정보 가져옴
        tripIdx = getTripIdx()

        Log.d("Tripcourse_tripIdx/Data", "tripIdx : $tripIdx, tripData : $tripData")

        //출발일
        val dDate = tripData.departureDate.split("-")
        val dYear = dDate[0].substring(2,4)
        val dMonth = dDate[1]
        val dDay = dDate[2]
        saveDepartureYear(dYear.toInt())
        saveDepartureMonth(dMonth.toInt())
        saveDepartureDay(dDay.toInt())

        //도착일
        val aDate = tripData.arrivalDate.split("-")
        val aYear = aDate[0].substring(2,4)
        val aMonth = aDate[1]
        val aDay = aDate[2]
        saveArrivalYear(aYear.toInt())
        saveArrivalMonth(aMonth.toInt())
        saveArrivalDay(aDay.toInt())

        val period = dYear + "년 " + dMonth + "월 " + dDay + "일 ~ " + aYear + "년 " + aMonth + "월 " + aDay + "일"

        //여행 제목 띄우기
        binding.tripcourseTitlebarPeriodTv.text = period
        binding.tripcourseTitlebarTitleTv.text = tripData.tripTitle

        //click listener
        binding.tripcourseTopbarLayout.topbarBackIb.setOnClickListener(this)
        binding.tripcourseTopbarLayout.topbarSubbuttonIb.setOnClickListener(this)
        binding.tripcourseAddCardBtn.setOnClickListener(this)

        //타이틀바 길게 클릭 - 여행 삭제하기
        registerForContextMenu(binding.tripcourseTitlebarLayout)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onClick(v: View?) {
        super.onClick(v)
        when(v!!.id) {
            R.id.topbar_back_ib -> { //상단바 - 뒤로가기 버튼 - 현재 액티비티 종료
                showDialog(
                    "발자국 작성 취소", "발자국 작성을 취소하시겠습니까?\n"
                            + "작성중인 정보는 저장되지 않습니다.", "확인"
                )
            }
            R.id.topbar_subbutton_ib -> { //상단바 - 체크 버튼 - 저장
                uploadCards()
                uploadTripImg()
                tripCards.clear()
                finish()
            }
            R.id.tripcourse_add_card_btn -> {
                addCard()
            }
        }
    }

    //여행 삭제하기 context menu
    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo? ) {
        showDialog("여행 삭제", "지금까지 작성한 여행기록과 여행을 삭제합니다.\n기록은 저장되지 않습니다. 삭제하시겠습니까?", "확인")
    }

    private fun initRecyclerView() {
        cardRVAdapter = RVCardAdapter(this)
        binding.lookerAlbumlistRecyclerview.adapter = cardRVAdapter
        binding.lookerAlbumlistRecyclerview.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        binding.lookerAlbumlistRecyclerview.addItemDecoration(RVCardAdapterDecoration())

        cardRVAdapter.setItemClickListener(object : RVCardAdapter.CardClickListener {
            override fun onItemClick(card: Card) {
                startTripcourseRecordActivity()
            }
        })
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun startTripcourseRecordActivity() {
        val intent = Intent(this@TripcourseActivity, TripcourseRecordActivity::class.java)
        startActivity(intent)
    }

    private fun addDefaultCard() {
        addCard()
        addCard()
        addCard()
    }

    private fun addCard() {
        if(tripCards.size >= 15) {
            Toast.makeText(this, "최대 15개의 카드까지 생성 가능합니다", Toast.LENGTH_SHORT).show()
            return
        }
        card = Card(tripIdx = tripIdx, cardIdx= cardIdx)
        cardIdx++

        tripCards.add(card)

        cardRVAdapter.notifyItemInserted(cardRVAdapter.itemCount - 1)

        Log.d("Tripcourse_numCards", "tripCards : ${tripCards.size}, RVCardAdapter : ${cardRVAdapter.itemCount}")
    }

    private fun uploadCards() {
        for(i in 0 until tripCards.size) {
            postCard(tripCards[i])
        }
    }

    /*---------------------서버 통신 부분------------------*/

    private fun postCard(card : Card) {
        val cardService = CardService()
        cardService.setPostCardView(this)
        Log.d("Check card Data", card.toString())

        cardService.postCard(card)
    }

    //서버에 카드 보내는 중 View
    override fun onPostCardLoading() {
        binding.tripcourseLoadingPb.visibility = View.VISIBLE
    }

    override fun onPostCardSuccess(courseIdx: Int) {
        binding.tripcourseLoadingPb.visibility = View.GONE
    }

    override fun onPostCardFailure(code: Int, message: String) {
        binding.tripcourseLoadingPb.visibility = View.GONE
        Toast.makeText(this, "$code : $message", Toast.LENGTH_LONG).show()
    }

    private fun uploadTripImg() {
        val tripImg = tripCards[0].imgUrl
        //대표 tripImg 변경 추가
        val cardService = CardService()
        cardService.setServerView(this)
        Log.d("Check Trip Img", tripImg)

        cardService.patchTripImg(tripIdx, tripImg)
    }

    private fun deleteTrip(tripIdx: Int) {
        val cardService = CardService()
        cardService.setServerView(this)
        Log.d("deleteTrip", "Check tripIdx : ${tripIdx.toString()}")

        cardService.deleteTrip(tripIdx)
    }

    override fun onServerLoading() {
        binding.tripcourseLoadingPb.visibility = View.VISIBLE
    }

    override fun onServerSuccess() {
        binding.tripcourseLoadingPb.visibility = View.GONE
    }

    override fun onServerFailure(code: Int, message: String) {
        binding.tripcourseLoadingPb.visibility = View.GONE
        Toast.makeText(this, "$code : $message", Toast.LENGTH_LONG).show()
    }
}