package com.olympos.tripbook.src.tripcourse

import android.annotation.SuppressLint
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
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.olympos.tripbook.R
import com.olympos.tripbook.config.BaseActivity
import com.olympos.tripbook.databinding.ActivityTripcourseRecordBinding
import com.olympos.tripbook.src.tripcourse.model.Card
import com.olympos.tripbook.src.tripcourse.model.CardService
import com.olympos.tripbook.src.tripcourse.model.PostCardView
import com.olympos.tripbook.src.tripcourse.model.ServerView
import com.olympos.tripbook.utils.getTripIdx
import com.olympos.tripbook.utils.getUserIdx
import java.text.SimpleDateFormat
import java.util.*

class TripcourseRecordActivity : BaseActivity(), PostCardView, DateSelectDialog.DialogClickListener {

    lateinit var binding: ActivityTripcourseRecordBinding
    private var gson: Gson = Gson()
    private var card: Card = Card() //채울 카드

    private var uri: Uri = Uri.parse("") //사진 uri 전역변수

    private var launcher = registerForActivityResult(ActivityResultContracts.GetContent()) {
        binding.tripcourseRecordImgIv.setImageURI(it)
        binding.tripcourseRecordImgTv.visibility = View.GONE
        uri = it
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTripcourseRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        //상단바
        binding.tripcourseRecordTopbarLayout.topbarTitleTv.setText(R.string.tripcourse_record_title)
        binding.tripcourseRecordTopbarLayout.topbarSubbuttonIb.setImageResource(R.drawable.btn_base_check_black)
        binding.tripcourseRecordTopbarLayout.topbarSubtitleTv.visibility = View.GONE

        if (intent.hasExtra("card")) {
            card = gson.fromJson(intent.getStringExtra("card"), card::class.java)
            if (card.hasData == TRUE) {
                binding.tripcourseRecordBodyEt.hint = card.body
                binding.tripcourseRecordTitleEt.hint = card.title
                binding.tripcourseRecordSelectDateBtn.text = card.date
                Glide.with(this.applicationContext).load(card.coverImg)
                    .into(binding.tripcourseRecordImgIv)
                //binding.tripcourseRecordSelectCountryBtn.text = card.country
            }
        }

        //body : 내용 최대 200자 이벤트 처리
        binding.tripcourseRecordBodyEt.addTextChangedListener(object : TextWatcher {
            val wordCountTv = binding.tripcourseRecordContentWordcountTv
            var userInput = binding.tripcourseRecordBodyEt

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

        //여행 날짜 선택 - Dialog 생성
        binding.tripcourseRecordSelectDateBtn.setOnClickListener(this)
        binding.tripcourseRecordTopbarLayout.topbarBackIb.setOnClickListener(this)
        binding.tripcourseRecordTopbarLayout.topbarSubbuttonIb.setOnClickListener(this)
        binding.tripcourseRecordImgCl.setOnClickListener(this)
        binding.tripcourseRecordSelectCountryBtn.setOnClickListener(this)
        binding.tripcourseRecordHashtagAddBtn.setOnClickListener(this)
        binding.tripcourseRecordSelectDateBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        super.onClick(v)

        when (v!!.id) {
            R.id.topbar_back_ib ->
                showDialog("안내", "발자국 작성을 취소하시겠습니까?\n" + "작성하셨던 내용은 임시저장됩니다.", "확인")

            R.id.topbar_subbutton_ib -> {
                //입력받은 정보를 Card에 담기
//                getInputInfo()
                //서버에 Card의 수정된 정보를 전송
                postCard(card)
            }

            R.id.tripcourse_record_img_cl -> {
                photoSelect()
                //사진 저장(Uri)
                card.coverImg = uri.toString()
            }

            //여행 도시 선택 - TripcourseSelectContryActivity로 이동
            R.id.tripcourse_record_select_country_btn ->
                startTripcourseSelectCountryActivity()

            R.id.tripcourse_record_select_date_btn -> {
                showDateDialog("여행 날짜 선택", "날짜 선택 완료")
            }

            //해시태그 선택 - TripcourseSelectHashtagActivity로 이동
            R.id.tripcourse_record_hashtag_add_btn ->
                startTripcourseSelectHashtagActivity()
        }
    }

    private fun showDateDialog(title: String, okMessage: String) {
        val dig = DateSelectDialog(this)
        dig.listener = this
        dig.show(title, okMessage)

    }

    override fun onDateOKClicked(selectedYear: Int, selectedMonth: Int, selectedDay: Int) {
        binding.tripcourseRecordSelectDateBtn.text =
            String.format("%d년 %d월 %d일", selectedYear, selectedMonth, selectedDay)
        val tripDate = String.format("%d-%d-%d", selectedYear, selectedMonth, selectedDay)

        //날짜 저장
        card.date = tripDate
    }

    override fun onDateCancelClicked() {

    }

    override fun onOKClicked() {
        super.onOKClicked()
        finish()
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
        val fileName = "IMAGE_${SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())}"
        //파일 업로드, 다운로드, 삭제, 메타데이터 가져오기 또는 업데이트를 하기 위해 참조를 생성.
        //참조는 클라우드 파일을 가리키는 포인터라고 할 수 있음.
        val imagesRef =
            storage!!.reference.child("images/").child(fileName)    //기본 참조 위치/images/${fileName}
        //이미지 파일 업로드
        imagesRef.putFile(uri).addOnSuccessListener {
            Toast.makeText(this, "성공", Toast.LENGTH_SHORT).show()
            it.storage.downloadUrl.addOnSuccessListener {
                it.toString()
                //api호출 it을 사진 text값에 post
            }.addOnFailureListener { }
        }.addOnFailureListener {
            println(it)
            Toast.makeText(this, "실패", Toast.LENGTH_SHORT).show()
        }
    }

    private fun startTripcourseSelectCountryActivity() {
        val intent = Intent(this, TripcourseSelectCountryActivity::class.java)
        startActivity(intent)
    }

    private fun startTripcourseSelectHashtagActivity() {
        val intent = Intent(this, TripcourseSelectHashtagActivity::class.java)
        startActivity(intent)
    }

    private fun getInputInfo() {
        card.hasData = TRUE

        //제목 저장
        card.title = binding.tripcourseRecordTitleEt.text.toString()
        //body 저장
        card.body = binding.tripcourseRecordBodyEt.text.toString()


        //아직 구현이 안된 더미 데이터들
        card.time = 2

        //아직까진 다시 TripcourseActivity로 보내진 않고 서버로 바로 카드를 보냄
//            val intent = Intent(this@TripcourseRecordActivity, TripcourseRecordActivity::class.java)
//            val cardData = gson.toJson(card)
//            intent.putExtra("cardInputResult", cardData)
//
//            setResult(COUNTRY_ACTIVITY_CODE, intent)

    }

    //Retrofit
//    private fun postCard(card : Card) {
//        val cardService = CardService()
//        cardService.setServerView(this)
//        cardService.postCard(card)
//    }

    private fun patchInfo(card: Card) {
        val cardService = CardService()
        cardService.setPostCardView(this)

        Log.d("Last check before patch", card.toString())

//        //사진 저장(Uri)
//        cardService.patchImg(getUserIdx().toString(), card.courseIdx.toString(), card.coverImg)

        //제목 저장
        cardService.patchTitle(getUserIdx().toString(), card.courseIdx.toString(), card.title)

        //body 저장
        cardService.patchBody(getUserIdx().toString(), card.courseIdx.toString(), card.body)

        //아직 구현이 안된 더미 데이터들
        //card.date = "0000-00-00"
        //card.time = 2
    }

    private fun postCard(card: Card) {
        val cardService = CardService()
        cardService.setPostCardView(this)

        cardService.postCard(card)
    }

    override fun onPostCardLoading() {
        binding.tripcourseRecordLoadingPb.visibility = View.VISIBLE
    }

    override fun onPostCardSuccess(courseIdx: Int) {
        binding.tripcourseRecordLoadingPb.visibility = View.GONE
        //firebase storage에 이미지를 업로드
        uploadImage(uri)

        Toast.makeText(this, "서버에 카드 전송 완료", Toast.LENGTH_SHORT).show()
        finish() //셋중 하나만 성공해도 종료됨 수정 필요
    }

    override fun onPostCardFailure(code: Int, message: String) {
        binding.tripcourseRecordLoadingPb.visibility = View.GONE

        when (code) {
            400 -> Toast.makeText(this, "네트워크 상태를 확인해주세요.", Toast.LENGTH_SHORT).show()
            2201 -> Toast.makeText(this, "여행을 지정해주세요.", Toast.LENGTH_SHORT).show()
            2202 -> Toast.makeText(this, "날짜를 지정해주세요.", Toast.LENGTH_SHORT).show()
            2203 -> Toast.makeText(this, "시간을 지정해주세요.", Toast.LENGTH_SHORT).show()
            2204 -> Toast.makeText(this, "제목를 입력해주세요.", Toast.LENGTH_SHORT).show()
            2205 -> Toast.makeText(this, "이미지를 선택해주세요.", Toast.LENGTH_SHORT).show()
            2206 -> Toast.makeText(this, "코멘트를 입력해주세요.", Toast.LENGTH_SHORT).show()
            2211 -> Toast.makeText(this, "카드를 선택해주세요.", Toast.LENGTH_SHORT).show()
        }
    }
}