package com.olympos.tripbook.src.tripcourse

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior.getTag
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
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

class TripcourseActivity : BaseActivity(), CardsView, TripGetProcess, PostCardView {

    lateinit var binding : ActivityTripcourseBinding
    private var gson : Gson = Gson()
    private var tripData = Trip()

    private lateinit var cardRVAdapter : RVCardAdapter

    private var cardIdx = 1
    private var tripIdx : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTripcourseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tripIdx = getTripIdx(this)

        initView()
        initRecyclerView()
        addDefaultCard()
    }

    private fun addDefaultCard() {
//        val setTestCard1 : Card = Card(tripIdx, cardIdx, TRUE,"https://post-phinf.pstatic.net/MjAxOTEyMjRfODgg/MDAxNTc3MTY0NzE3ODI0.td40390rDg76HqexxOaLbmw4FMvAE5-taBjKL0QqGw4g.O1S4JTJnFfVcGPgHiCn09gNG2VtFZDO6umEH6e6fqygg.JPEG/%EC%A0%9C%EC%A3%BC%EB%8F%84_%EB%9A%9C%EB%B2%85%EC%9D%B4_%EC%97%AC%ED%96%89.jpg?type=w1200", "2000-00-00", 2, "이름있는제목 1","바뀐 내용 11111", "", "") //cardIdx =1
//        cardRVAdapter.addCard(setTestCard1)
//        cardIdx++
//
//        val setTestCard2 : Card = Card(tripIdx, cardIdx, TRUE, "https://korean.nlcsjeju.co.kr/userfiles/nlcsjejukrmvc/images/body/IMG_9153.jpg", "2000-11-11", 3, "어떻게든 지어본 이름 2", "바뀌어버린 내용", "", "") //cardIdx =2
//        cardRVAdapter.addCard(setTestCard2)
//        cardIdx++

//        val defaultCard1: Card = Card(tripIdx, cardIdx) //cardIdx =1
//        cardRVAdapter.addCard(defaultCard1)
//        postCard(defaultCard1)
//        cardIdx++
//
//        val defaultCard2: Card = Card(tripIdx, cardIdx) //cardIdx =2
//        cardRVAdapter.addCard(defaultCard2)
//        postCard(defaultCard2)
//        cardIdx++
//
//        val defaultCard3: Card = Card(tripIdx, cardIdx) //cardIdx =3
//        cardRVAdapter.addCard(defaultCard3)
//        postCard(defaultCard3)
//        cardIdx++

        addCard()
        addCard()
        addCard()
    }

    private fun startTripcourseRecordActivity(card: Card) {
        val intent = Intent(this@TripcourseActivity, TripcourseRecordActivity::class.java)

        if (card.hasData == TRUE) { //데이터가 있는 경우
            val cardData = gson.toJson(card)
            intent.putExtra("card", cardData)
        }

        intent.putExtra("cardIdx", card.idx)
        intent.putExtra("tripIdx", card.tripIdx)

        startActivity(intent)
    }

    override fun onRestart() {
        super.onRestart()
        initRecyclerView()
        getTripcourses(tripIdx.toString())
    }

    //여행 삭제하기 context menu
    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo? ) {
        val inflater = menuInflater
        inflater.inflate(R.menu.context_menu_tripcourse_delete_trip, menu)
//        super.onCreateContextMenu(menu, v, menuInfo)
        showDialog("", "", "")
    }
    //여행 삭제하기 context menu
    override fun onContextItemSelected(item: MenuItem): Boolean {
        //다이어로그 뜨기
        return super.onContextItemSelected(item)
    }

    override fun onOKClicked() {
        super.onOKClicked()
        //todo 현재 여행 삭제
        startMainActivity()
    }

    private fun initView() {
        //topbar layout view randering
        binding.tripcourseTopbarLayout.topbarTitleTv.setText(R.string.tripcourse_title)
        binding.tripcourseTopbarLayout.topbarSubbuttonIb.setImageResource(R.drawable.btn_base_check_black)

        //여행 정보 가져옴
        getTrip()

        binding.tripcourseTopbarLayout.topbarBackIb.setOnClickListener(this)
        binding.tripcourseTopbarLayout.topbarSubbuttonIb.setOnClickListener(this)
        binding.tripcourseAddCardBtn.setOnClickListener(this)
        Log.d("tripData", tripData.toString())

        //타이틀바 길게 클릭 - 여행 삭제하기
        registerForContextMenu(binding.tripcourseTitlebarLayout)
    }

    //종료된 액티비티에서 정보 받아오기 : TripcourseRecord -> card Data
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun initRecyclerView() {
        cardRVAdapter = RVCardAdapter(this)
        binding.lookerAlbumlistRecyclerview.adapter = cardRVAdapter

        binding.lookerAlbumlistRecyclerview.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        binding.lookerAlbumlistRecyclerview.addItemDecoration(RVCardAdapterDecoration())

        cardRVAdapter.setItemClickListener(object : RVCardAdapter.CardClickListener {
            override fun onItemClick(card: Card) {
                startTripcourseRecordActivity(card)
            }
        })
    }

    private fun getTripcourses(tripIdx : String) {
        val cardService = CardService()
        cardService.setCardsView(this)
        Log.d("Check tripIdx", tripIdx)
        cardService.getTripcourses(tripIdx)
    }

    private fun getTrip() {
        val tripService = TripService()
        tripService.setGetProcess(this)

        tripService.getTrip()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onClick(v: View?) {
        super.onClick(v)
        when(v!!.id) {
            R.id.topbar_back_ib -> //상단바 - 뒤로가기 버튼 - 현재 액티비티 종료
                showDialog("발자국 작성 취소", "발자국 작성을 취소하시겠습니까?\n"
                        + "작성중인 정보는 저장되지 않습니다.", "확인")
            R.id.topbar_subbutton_ib -> { //상단바 - 체크 버튼 - 저장
                //빈 카드 모두삭제하고 끝
                cardRVAdapter.onRemoveEmptyCard()
                finish()
            }
            R.id.tripcourse_add_card_btn -> {
                addCard()
            }
        }
    }

    private fun addCard() {
        val card: Card = Card(0, tripIdx, cardIdx)
        cardIdx++

        card.courseIdx = postCard(card)

        cardRVAdapter.addCard(card)
        cardRVAdapter.notifyItemInserted(cardRVAdapter.itemCount - 1)

        Log.d("Check num of cardDatas", cardRVAdapter.itemCount.toString())
    }

    private fun postCard(card : Card) : Int {
        val cardService = CardService()
        cardService.setPostCardView(this)
        Log.d("Check card Data", card.toString())

        //확인용 코드
        val asdf = cardService.postCard(card)
        Log.d("CheckCourseIdxAAAAA", asdf.toString())
        return asdf
        //return cardService.postCard(card)
        }

    //서버에서 tripcourse의 카드들을 가져오는 View
    override fun onGetCardsLoading() {
        binding.tripcourseLoadingPb.visibility = View.VISIBLE
    }

    override fun onGetCardsSuccess(cards: ArrayList<Card>) {
        binding.tripcourseLoadingPb.visibility = View.GONE
        cardRVAdapter.setCards(cards)
    }

    override fun onGetCardsFailure(code: Int, message: String) { //통신 실패 View
        binding.tripcourseLoadingPb.visibility = View.GONE
        Toast.makeText(this, "$code : $message", Toast.LENGTH_LONG).show()
    }

    override fun onGetTripLoading() {
        //todo
    }

    override fun onGetTripSuccess(result: Trip) {
        tripData = result

        //출발일
        val dDate = tripData.departureDate.split("-")
        val dYear = dDate[0].substring(2,4)
        val dMonth = dDate[1]
        val dDay = dDate[2]
        saveDepartureYear(this, dYear.toInt())
        saveDepartureMonth(this, dMonth.toInt())
        saveDepartureDay(this, dDay.toInt())

        //도착일
        val aDate = tripData.arrivalDate.split("-")
        val aYear = aDate[0].substring(2,4)
        val aMonth = aDate[1]
        val aDay = aDate[2]
        saveArrivalYear(this, aYear.toInt())
        saveArrivalMonth(this, aMonth.toInt())
        saveArrivalDay(this, aDay.toInt())

        val period = dYear + "년 " + dMonth + "월 " + dDay + "일 ~ " + aYear + "년 " + aMonth + "월 " + aDay + "일"

        binding.tripcourseTitlebarPeriodTv.text = period
        binding.tripcourseTitlebarTitleTv.text = tripData.tripTitle
    }

    override fun onGetTripFailure(code: Int, message: String) {
        when(code) {
            400 -> Toast.makeText(this, "네트워크 상태를 확인해주세요.", Toast.LENGTH_SHORT).show()
            2105 -> Toast.makeText(this, "유저를 확인해주세요.", Toast.LENGTH_SHORT).show()
            2107 -> Toast.makeText(this, "여행 기록이 존재하지 않습니다.", Toast.LENGTH_SHORT).show()
        }
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
}