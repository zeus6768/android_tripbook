package com.olympos.tripbook.src.tripcourse

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import com.google.gson.Gson
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.google.firebase.storage.FirebaseStorage
import com.olympos.tripbook.R
import com.olympos.tripbook.config.BaseActivity
import com.olympos.tripbook.databinding.ActivityTripcourseRecordBinding
import com.olympos.tripbook.src.tripcourse.model.Card
import java.text.SimpleDateFormat
import java.util.*


class TripcourseRecordActivity : BaseActivity() {

    lateinit var binding: ActivityTripcourseRecordBinding
    lateinit var uri : Uri

    private var launcher = registerForActivityResult(ActivityResultContracts.GetContent()) {
        binding.tripcourseRecordImgIv.setImageURI(it)
        binding.tripcourseRecordImgTv.visibility=View.GONE
        uri = it
    }

    private var card: Card = Card()
//    private var hashtag : Hashtag = Hashtag()

    private val COUNTRY_ACTIVITY_CODE = 10
    private val HASHTAG_ACTIVITY_CODE = 20

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTripcourseRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()

        addHashtagDumyInfo()
        getInputInfo()

        //click 리스너
        binding.tripcourseRecordTopbarLayout.topbarBackIb.setOnClickListener(this)
        binding.tripcourseRecordTopbarLayout.topbarSubbuttonIb.setOnClickListener(this)
        binding.tripcourseRecordImgCl.setOnClickListener(this)
        binding.tripcourseRecordSelectCountryBtn.setOnClickListener(this)
        binding.tripcourseRecordHashtagAddBtn.setOnClickListener(this)

        //내용 최대 200자 이벤트 처리
        binding.tripcourseRecordContentEt.addTextChangedListener(object : TextWatcher {
            val wordCountTv = binding.tripcourseRecordContentWordcountTv
            var userInput = binding.tripcourseRecordContentEt

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                wordCountTv.text = "0 / 200"
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                wordCountTv.text = userInput.length().toString() + " / 200"
            }

            override fun afterTextChanged(s: Editable?) {
                if (userInput.isFocused && userInput.length() > 200) {
                    userInput.setText(s.toString().substring(0, 200))
                    userInput.setSelection(s!!.length - 1)
                    Toast.makeText(this@TripcourseRecordActivity,
                        "200자까지 입력 가능합니다.",
                        Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun initView() {
        //상단바
        binding.tripcourseRecordTopbarLayout.topbarTitleTv.setText(R.string.tripcourse_record_title)
        binding.tripcourseRecordTopbarLayout.topbarSubbuttonIb.setImageResource(R.drawable.btn_base_check_black)
        binding.tripcourseRecordTopbarLayout.topbarSubtitleTv.visibility = View.GONE

        //여행 날짜 선택 - Dialog 생성
        binding.tripcourseRecordSelectDateBtn.setOnClickListener {

        }
    }

    override fun onClick(v: View?) {
        super.onClick(v)

        when (v!!.id) {
            //뒤로 가기 버튼
            R.id.topbar_back_ib ->
                showDialog("안내", "발자국 작성을 취소하시겠습니까?\n" + "작성하셨던 내용은 임시저장됩니다.", "확인")
            //작성 완료 버튼
            R.id.topbar_subbutton_ib -> {
                //todo 저장완료, firebase storage에 이미지를 업로드
                uploadImage(uri)
            }
            R.id.tripcourse_record_img_cl ->
                photoSelect()

            //여행 도시 선택 - TripcourseSelectContryActivity로 이동
            R.id.tripcourse_record_select_country_btn ->
                startTripcourseSelectCountryActivity()

            //해시태그 선택 - TripcourseSelectHashtagActivity로 이동
            R.id.tripcourse_record_hashtag_add_btn ->
                startTripcourseSelectHashtagActivity()
        }
    }

    private fun photoSelect() {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        ) {
            launcher.launch("image/*")
        } else {
            Toast.makeText(this, "접근 권한 거부", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun uploadImage(uri: Uri) {
        val storage: FirebaseStorage? = FirebaseStorage.getInstance() //FirebaseStorage 인스턴스 생성
        //파일 이름 생성
        val fileName = "IMAGE_${SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())}_.png"
        //파일 업로드, 다운로드, 삭제, 메타데이터 가져오기 또는 업데이트를 하기 위해 참조를 생성.
        //참조는 클라우드 파일을 가리키는 포인터라고 할 수 있음.
        val imagesRef = storage!!.reference.child("images/").child(fileName)    //기본 참조 위치/images/${fileName}
        //이미지 파일 업로드
        imagesRef.putFile(uri).addOnSuccessListener {
            Toast.makeText(this, "성공", Toast.LENGTH_SHORT).show()
            it.storage.downloadUrl.addOnSuccessListener {
                it.toString()
                //api호출 it을 사진 text값에 post
            }.addOnFailureListener {  }
        }.addOnFailureListener {
            println(it)
            Toast.makeText(this, "실패", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onOKClicked() {
        super.onOKClicked()
        finish()
    }

    private fun startTripcourseSelectCountryActivity() {
        val intent = Intent(this, TripcourseSelectCountryActivity::class.java)
        startActivity(intent)
    }

    private fun startTripcourseSelectHashtagActivity() {
        val intent = Intent(this, TripcourseSelectHashtagActivity::class.java)
        startActivity(intent)
    }

    private fun addHashtagDumyInfo() {
//        var i = 0
//        for(i in 0..10) {
//            hashtagInfo.location
//            hashtagInfo.weather
//            hashtagInfo.feeling
//            hashtagInfo.companion
//            hashtagInfo.event
//        }
    }

    private fun getInputInfo() {
        if (binding.tripcourseRecordTitleEt.text.toString().isEmpty()) {
            //입력이 안된 경우
        } else {
            card.cardTitle = binding.tripcourseRecordTitleEt.text.toString()
        }
        if (!binding.tripcourseRecordContentEt.text.toString().isEmpty()) {
            card.body = binding.tripcourseRecordContentEt.text.toString()
        }

        //종료된 액티비티에서 정보 받아오기
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        when(resultCode) {
//            COUNTRY_ACTIVITY_CODE -> { //SelectCountryActivity에서 장소 정보 가져오기
//                card.cardCountry = data?.getStringExtra("country_result")!!
//                binding.tripcourseRecordSelectCountryBtn.setText(card.cardCountry)
//            }
//            HASHTAG_ACTIVITY_CODE -> { //SelectHashtagActivity에서 해시태그 정보 가져오기
//                //해시태그 저장
//            }
//        }
//    }

        //날짜 가져오기


        //해시태그 가져오기

    }
}