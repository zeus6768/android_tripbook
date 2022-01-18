package com.olympos.tripbook.src.tripcourse

import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import com.olympos.tripbook.R
import com.olympos.tripbook.config.BaseActivity
import com.olympos.tripbook.databinding.ActivityTripcourseBinding

class TripcourseActivity : BaseActivity() {

    lateinit var binding : ActivityTripcourseBinding

    val albums = ArrayList<View>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTripcourseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView();
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

        //상단바 - 뒤로가기 버튼
        binding.tripcourseTopbarLayout.topbarBackIb.setOnClickListener {

        }
        //상단바 - 홈 버튼
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
    }
}