package com.olympos.tripbook.src.tripcourse

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.olympos.tripbook.R
import com.olympos.tripbook.config.BaseActivity
import com.olympos.tripbook.config.BaseDialog
import com.olympos.tripbook.databinding.ActivityTripcourseBinding
import com.olympos.tripbook.src.home.MainActivity
import com.olympos.tripbook.src.tripcourse.model.Card

class TripcourseActivity : BaseActivity() {

    lateinit var binding : ActivityTripcourseBinding

    private val cardDatas = ArrayList<Card>() //Datas in here. from Sever

//    private val cards = ArrayList<View>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTripcourseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()

        val card : Card = Card()
        cardDatas.add(card)
        cardDatas.add(card)
        cardDatas.add(card)

//        Log.d("Check num of cardDatas", cardDatas.size.toString())
//        Log.d("AM I HERE?", "YES YOU ARE!!")

        val cardRVAdapter = RVCardAdapter(cardDatas)
        binding.lookerAlbumlistRecyclerview.adapter = cardRVAdapter
        binding.lookerAlbumlistRecyclerview.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)

        cardRVAdapter.setItemClickListener(object : RVCardAdapter.CardClickListener {
            override fun onItemClick(card: Card) {
                val intent = Intent(this@TripcourseActivity, TripcourseRecordActivity::class.java)
                startActivity(intent)
            }
        })

        binding.tripcourseAddAlbumBtn.setOnClickListener {
            val card : Card = Card()
            cardDatas.add(card)
            Log.d("Check num of cardDatas", cardDatas.size.toString())
            cardRVAdapter.notifyItemInserted(cardDatas.size - 1)
        }
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


    private fun initView() {
        //topbar layout view randering
        binding.tripcourseTopbarLayout.topbarTitleTv.setText(R.string.tripcourse_title)
        binding.tripcourseTopbarLayout.topbarSubbuttonIb.setImageResource(R.drawable.btn_base_check_black)

        //상단바 - 뒤로가기 버튼 - 현재 액티비티 종료
        binding.tripcourseTopbarLayout.topbarBackIb.setOnClickListener {
//            val intent = Intent(this@TripcourseActivity, MainActivity::class.java)
//            startActivity(intent)
            val backBtnDialog = object : BaseDialog(this) {
            }
            backBtnDialog.show("안내","발자국 작성을 취소하시겠습니까?", "확인")
            finish()
        }

        //상단바 - 체크 버튼 - 저장
        binding.tripcourseTopbarLayout.topbarSubbuttonIb.setOnClickListener {

        }

        //타이틀바 클릭 - 편집 창으로 이동
        binding.tripcourseTitlebarLayout.setOnClickListener {

        }

        //타이틀바 길게 클릭 - 여행 삭제하기
        registerForContextMenu(binding.tripcourseTitlebarLayout)

        //카드 추가하기 버튼
        binding.tripcourseAddAlbumBtn.setOnClickListener {

        }

        //(임시) 카드 선택 - TripcourseRecordActivity 이동
//        binding.tripcourseCard1.setOnClickListener {
//            val intent = Intent(this@TripcourseActivity, TripcourseRecordActivity::class.java)
//            startActivity(intent)
//        }
    }

}