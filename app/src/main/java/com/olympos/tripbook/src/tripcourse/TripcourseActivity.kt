package com.olympos.tripbook.src.tripcourse

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
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


        binding.lookerAlbumlistRecyclerview.adapter = cardRVAdapter
        binding.lookerAlbumlistRecyclerview.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)

        cardRVAdapter.setItemClickListener(object : RVCardAdapter.CardClickListener {
            override fun onItemClick(card: Card) {
                val intent = Intent(this@TripcourseActivity, TripcourseRecordActivity::class.java)
                if(card.hasData) { //데이터가 있는 경우
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

        //타이틀바 길게 클릭 - 여행 삭제하기
        registerForContextMenu(binding.tripcourseTitlebarLayout)
    }

    override fun onClick(v: View?) {
        super.onClick(v)

        when(v!!.id) {
            R.id.topbar_back_ib -> //상단바 - 뒤로가기 버튼 - 현재 액티비티 종료
                showDialog("발자국 작성 취소", "발자국 작성을 취소하시겠습니까?\n"
                        + "작성중인 정보는 저장되지 않습니다.", "확인")
            R.id.topbar_subbutton_ib -> { //상단바 - 체크 버튼 - 저장
                //todo 저장
            }
            R.id.tripcourse_add_album_btn -> {
                val card : Card = Card()
                cardDatas.add(card)
                Log.d("Check num of cardDatas", cardDatas.size.toString())
                cardRVAdapter.notifyItemInserted(cardDatas.size - 1)
            }
        }
    }
}