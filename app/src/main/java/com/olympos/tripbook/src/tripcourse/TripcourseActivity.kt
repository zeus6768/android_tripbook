package com.olympos.tripbook.src.tripcourse

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
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

class TripcourseActivity : BaseActivity() {

    lateinit var binding : ActivityTripcourseBinding
    private var gson : Gson = Gson()

    private val cardDatas = ArrayList<Card>() //Datas in here. from Sever
    val cardRVAdapter = RVCardAdapter(cardDatas)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTripcourseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()

        val card : Card = Card()
        cardDatas.add(card)
        cardDatas.add(card)
        cardDatas.add(card)
        setDummyData2Card()

        binding.lookerAlbumlistRecyclerview.adapter = cardRVAdapter
        binding.lookerAlbumlistRecyclerview.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)

        cardRVAdapter.setItemClickListener(object : RVCardAdapter.CardClickListener {
            override fun onItemClick(card: Card) {
                val intent = Intent(this@TripcourseActivity, TripcourseRecordActivity::class.java)

                if(card.hasData == TRUE) { //데이터가 있는 경우
                    val cardData = gson.toJson(card)
                    intent.putExtra("card", cardData)
                }

                startActivity(intent)
            }
        })
    }

    //여행 삭제하기 context menu
    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo? ) {
        val inflater = menuInflater
        inflater.inflate(R.menu.context_menu_tripcourse_delete_trip, menu)
        //super.onCreateContextMenu(menu, v, menuInfo)
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
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        setDummyData2Card()
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
                val card : Card = Card()
                cardDatas.add(card)
                Log.d("Check num of cardDatas", cardRVAdapter.itemCount.toString())
                cardRVAdapter.notifyItemInserted(cardDatas.size - 1)
                cardRVAdapter.notifyDataSetChanged()
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setDummyData2Card() {
        Log.d("setDummyData2Card", "start")
        cardDatas[0].hasData = TRUE
        cardDatas[0].coverImg = R.drawable.img_tripcourse_card_ex
        cardDatas[0].title = "안녕 나 들어왔어"
        cardDatas[0].date = "날짜도 바뀜"
        cardDatas[0].body = "내용도 요래요래요래요래 바뀜"

        cardDatas[1].hasData = TRUE
        cardDatas[1].coverImg = R.drawable.img_tripcourse_card_ex
        cardDatas[1].title = "안녕 나 들어왔어22"
        cardDatas[1].date = "날짜도 바뀜22"
        cardDatas[1].body = "내용도 요래요래요래요래 바뀜22"

        cardRVAdapter.notifyDataSetChanged()
    }
}