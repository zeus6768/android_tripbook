package com.olympos.tripbook.src.tripcourse

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.olympos.tripbook.R
import com.olympos.tripbook.config.BaseActivity
import com.olympos.tripbook.databinding.ActivityTripcourseRecordBinding
import java.text.SimpleDateFormat
import java.util.*

class TripcourseRecordActivity : BaseActivity(), DateSelectDialog.DialogClickListener {

    lateinit var binding: ActivityTripcourseRecordBinding
    lateinit var tripDate: String

    private var firebaseUrl: Uri? = null //firebase uri 전역변수
    private var localUrl: Uri? = null //local uri 전역변수
    private var resultLauncher: ActivityResultLauncher<Intent>? = null
//    private var launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
//        binding.tripcourseRecordImgIv.setImageURI(uri)
//        binding.tripcourseRecordImgTv.visibility = View.GONE
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTripcourseRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        galleryCallback()
    }

    private fun initView() {
        //상단바
        binding.tripcourseRecordTopbarLayout.topbarTitleTv.setText(R.string.tripcourse_record_title)
        binding.tripcourseRecordTopbarLayout.topbarSubbuttonIb.setImageResource(R.drawable.btn_base_check_black)
        binding.tripcourseRecordTopbarLayout.topbarSubtitleTv.visibility = View.GONE

        if(tripCards[focusedCardPosition].title != "NONE") {
            binding.tripcourseRecordBodyEt.hint = tripCards[focusedCardPosition].body
            binding.tripcourseRecordTitleEt.hint = tripCards[focusedCardPosition].title
            binding.tripcourseRecordSelectDateBtn.text = tripCards[focusedCardPosition].date
            binding.tripcourseRecordImgTv.visibility = View.GONE
            Glide.with(this.applicationContext).load(tripCards[focusedCardPosition].imgUrl)
                .into(binding.tripcourseRecordImgIv)
            //binding.tripcourseRecordSelectCountryBtn.text = card.country
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
                showDialog("발자국 작성 취소", "발자국 작성을 취소하시겠습니까?\n" + "작성하셨던 내용은 사라집니다.", "확인")
            R.id.topbar_subbutton_ib -> {
                localUrl?.let { uploadImage(it) } //firebase에 이미지 업로드
                getInputInfo() //입력받은 정보를 tripCards[focusedCardPosition]에 담기
                finish()
            }

            R.id.tripcourse_record_img_cl -> {
                openGallery()
//                galleryCallback()
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

    private fun getModifyInfo() {
        if(binding.tripcourseRecordTitleEt.toString().isNotEmpty()) {
            tripCards[focusedCardPosition].whatsChange.add(TITLE_CHANGED)
        }
        if(binding.tripcourseRecordBodyEt.toString().isNotEmpty()){
            tripCards[focusedCardPosition].whatsChange.add(BODY_CHANGED)
        }
        //if(){ //todo 사진 변경되었는지 확인 : 기존 card.coverImg와 새로운 card.coverImg의 차이가 있다면 변경 됐음 -> 수정
        //tripCards[focusedCardPosition].whatsChange.add(IMG_CHANGED)
        // }
    }

    private fun getInputInfo() {
        //필수요소 : 제목
        tripCards[focusedCardPosition].hasData = TRUE
        //사진 저장(Uri)
        tripCards[focusedCardPosition].imgUrl = firebaseUrl.toString()
        //제목 저장
        tripCards[focusedCardPosition].title = binding.tripcourseRecordTitleEt.text.toString()
        //body 저장
        tripCards[focusedCardPosition].body = binding.tripcourseRecordBodyEt.text.toString()
        //날짜 저장
        tripCards[focusedCardPosition].date = tripDate

        //아직 구현이 안된 더미 데이터들ㅎ
        tripCards[focusedCardPosition].time = 2

        //아직까진 다시 TripcourseActivity로 보내진 않고 서버로 바로 카드를 보냄
    }

    private fun startTripcourseSelectCountryActivity() {
        val intent = Intent(this, TripcourseSelectCountryActivity::class.java)
        startActivity(intent)
    }

    private fun startTripcourseSelectHashtagActivity() {
        val intent = Intent(this, TripcourseSelectHashtagActivity::class.java)
        startActivity(intent)
    }

    //상단바 - 뒤로가기 버튼 -> 종료(저장 x)
    override fun onOKClicked() {
        super.onOKClicked()
        finish()
    }

    private fun openGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        resultLauncher?.launch(intent)
//        if (ContextCompat.checkSelfPermission(this,
//                android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
//        ) {
//            launcher.launch("image/*")
//            URLhash[index++] = uri.toString()
//        } else {
//            Toast.makeText(this, "접근 권한 거부", Toast.LENGTH_SHORT).show()
//        }
    }

    private fun showDateDialog(title: String, okMessage: String) {
        val dig = DateSelectDialog(this)
        dig.listener = this
        dig.show(title, okMessage)
    }

    override fun onDateOKClicked(selectedYear: Int, selectedMonth: Int, selectedDay: Int) {
        binding.tripcourseRecordSelectDateBtn.text = String.format("%d년 %d월 %d일", selectedYear, selectedMonth, selectedDay)
    }

    override fun onDateCancelClicked() {

    }

    private fun galleryCallback() {
        resultLauncher = registerForActivityResult(
            StartActivityForResult()
        ) { result: ActivityResult ->
            if ((ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                && result.resultCode == RESULT_OK
            ) {
                if (result.data == null) {
                    return@registerForActivityResult
                }
                localUrl = result.data!!.data
                binding.tripcourseRecordImgTv.visibility = View.GONE
                Glide.with(this).load(localUrl).into(binding.tripcourseRecordImgIv)

            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun uploadImage(selectedImgUri: Uri) {
        //파일 이름 생성
        val fileName = "IMAGE_${SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())}"
        //파일 업로드, 다운로드, 삭제, 메타데이터 가져오기 또는 업데이트를 하기 위해 참조를 생성.
        //참조는 클라우드 파일을 가리키는 포인터라고 할 수 있음.(사진 저장 경로)
        val imagesRef = storageRef.child("images/").child(fileName)    //기본 참조 위치/images/${fileName}
        //이미지 파일 업로드
        imagesRef.putFile(selectedImgUri).addOnSuccessListener {
            Toast.makeText(this, "파이어베이스 업로드 성공", Toast.LENGTH_SHORT).show()
            //firebase url 저장
            it.storage.downloadUrl.addOnSuccessListener { uri->
                Log.d("firebase Url", firebaseUrl.toString())
                firebaseUrl = uri
            }.addOnFailureListener { }
        }.addOnFailureListener {
            Log.d("firebase failure", it.toString())
            Toast.makeText(this, "파이어베이스 업로드 실패", Toast.LENGTH_SHORT).show()
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
        tripDate = String.format("%d-%d-%d", selectedYear, selectedMonth, selectedDay)
    }

    override fun onDateCancelClicked() {

    }
}