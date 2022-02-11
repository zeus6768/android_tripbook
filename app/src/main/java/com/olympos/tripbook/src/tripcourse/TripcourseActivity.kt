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
import com.olympos.tripbook.src.tripcourse.model.Card
import com.olympos.tripbook.src.tripcourse.model.CardService
import com.olympos.tripbook.src.tripcourse.model.CardsView
import com.olympos.tripbook.utils.getTripIdx

class TripcourseActivity : BaseActivity(), CardsView {

    lateinit var binding : ActivityTripcourseBinding
    private var gson : Gson = Gson()

    private lateinit var cardRVAdapter : RVCardAdapter

    private var cardIdx = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTripcourseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cardService = CardService()
        cardService.setCardsView(this)

        initView()
        initRecyclerView()

//        val defaultCard1 : Card = Card(cardIdx) //cardIdx =1
//        cardRVAdapter.addCard(defaultCard1)
//        cardIdx++
//
//        val defaultCard2 : Card = Card(cardIdx) //cardIdx =2
//        cardRVAdapter.addCard(defaultCard2)
//        cardIdx++

        val setTestCard1 : Card = Card(getTripIdx(this), cardIdx, TRUE,"https://post-phinf.pstatic.net/MjAxOTEyMjRfODgg/MDAxNTc3MTY0NzE3ODI0.td40390rDg76HqexxOaLbmw4FMvAE5-taBjKL0QqGw4g.O1S4JTJnFfVcGPgHiCn09gNG2VtFZDO6umEH6e6fqygg.JPEG/%EC%A0%9C%EC%A3%BC%EB%8F%84_%EB%9A%9C%EB%B2%85%EC%9D%B4_%EC%97%AC%ED%96%89.jpg?type=w1200", "2000-00-00", 2, "이름있는제목 1","바뀐 내용 11111", "", "") //cardIdx =1
        cardRVAdapter.addCard(setTestCard1)
        cardIdx++

        val setTestCard2 : Card = Card(getTripIdx(this), cardIdx, TRUE, "https://korean.nlcsjeju.co.kr/userfiles/nlcsjejukrmvc/images/body/IMG_9153.jpg", "2000-11-11", 3, "어떻게든 지어본 이름 2", "바뀌어버린 내용", "", "") //cardIdx =2
        cardRVAdapter.addCard(setTestCard2)
        cardIdx++

        val defaultCard3 : Card = Card(getTripIdx(this), cardIdx) //cardIdx =3
        cardRVAdapter.addCard(defaultCard3)
        cardIdx++

        binding.lookerAlbumlistRecyclerview.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        binding.lookerAlbumlistRecyclerview.addItemDecoration(RVCardAdapterDecoration())

//        setDummyData2Card(cardDatas)

        cardRVAdapter.setItemClickListener(object : RVCardAdapter.CardClickListener {
            override fun onItemClick(card: Card) {
                startTripcourseRecordActivity(card)
            }
        })
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
        getTrip()
        //todo 서버에서 카드정보 가져와서 적용하기
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
        finish()
    }

    private fun initView() {
        //topbar layout view randering
        binding.tripcourseTopbarLayout.topbarTitleTv.setText(R.string.tripcourse_title)
        binding.tripcourseTopbarLayout.topbarSubbuttonIb.setImageResource(R.drawable.btn_base_check_black)

        binding.tripcourseTopbarLayout.topbarBackIb.setOnClickListener(this)
        binding.tripcourseTopbarLayout.topbarSubbuttonIb.setOnClickListener(this)
        binding.tripcourseAddCardBtn.setOnClickListener(this)

        //타이틀바 길게 클릭 - 여행 삭제하기
        registerForContextMenu(binding.tripcourseTitlebarLayout)
    }

    //종료된 액티비티에서 정보 받아오기 : TripcourseRecord -> card Data
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//    }

    private fun initRecyclerView() {
        cardRVAdapter = RVCardAdapter(this)
        binding.lookerAlbumlistRecyclerview.adapter = cardRVAdapter
    }

    private fun getTrip(){
        val cardService = CardService()
        cardService.setCardsView(this)
        cardService.getTrip()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onClick(v: View?) {
        super.onClick(v)
        when(v!!.id) {
            R.id.topbar_back_ib -> //상단바 - 뒤로가기 버튼 - 현재 액티비티 종료
                showDialog("발자국 작성 취소", "발자국 작성을 취소하시겠습니까?\n"
                        + "작성중인 정보는 저장되지 않습니다.", "확인")
            R.id.topbar_subbutton_ib -> { //상단바 - 체크 버튼 - 저장
                //todo 저장
            }
            R.id.tripcourse_add_card_btn -> {
                addCard()
            }
        }
    }

    private fun addCard() {
        val card: Card = Card(cardIdx)
        cardIdx++

        cardRVAdapter.addCard(card)
        cardRVAdapter.notifyItemInserted(cardRVAdapter.itemCount - 1)

        Log.d("Check num of cardDatas", cardRVAdapter.itemCount.toString())
    }

    override fun onGetCardsLoading() {
        //todo 로딩바 생성
    }

    override fun onGetCardsSuccess(cards: ArrayList<Card>) {
        //todo 로딩바 제거
        cardRVAdapter.setCards(cards)
    }

    override fun onGetCardsFailure(code: Int, message: String) { //통신 실패 View
        //todo 로딩바 제거
        Toast.makeText(this, "$code : $message", Toast.LENGTH_LONG).show()
    }
}