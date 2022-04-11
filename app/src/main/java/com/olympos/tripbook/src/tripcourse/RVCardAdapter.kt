package com.olympos.tripbook.src.tripcourse

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.olympos.tripbook.R
import com.olympos.tripbook.config.BaseDialog
import com.olympos.tripbook.databinding.ItemTripcourseCardBaseEmptyBinding
import com.olympos.tripbook.databinding.ItemTripcourseCardBaseFillBinding
import com.olympos.tripbook.src.tripcourse.model.Card
import com.olympos.tripbook.src.tripcourse.model.ServerView
import java.util.*


class RVCardAdapter(context : Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), ServerView {

    /*---------- 전역 변수 ----------*/

    //인터페이스 구현
    private lateinit var cardClickListener: CardClickListener

    private val mContext = context

    /*---------- 인터페이스 ----------*/

    interface CardClickListener{
        fun onItemClick(card : Card)            //아이템 클릭 이벤트 인터페이스
    }

    inner class DialogClass(context: Context) : BaseDialog.BaseDialogClickListener {
        val thisContext = context
        override fun onOKClicked() {
            // todo 카드의 courseIdx 받아와서 deleteCard(courseIdx)
            notifyItemRemoved(focusedCardPosition)
            tripCards.removeAt(focusedCardPosition)
        }
        override fun onCancelClicked() {

        }
        fun showDialog(title: String, message: String , okMessage: String) {
            val dig = BaseDialog(thisContext)
            dig.listener = this
            dig.show(title, message, okMessage)
        }
    }

    /*---------- 내부 클래스(뷰 홀더) ----------*/

    //View Holder
    inner class EmptyCardViewHolder(val binding : ItemTripcourseCardBaseEmptyBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            //사실 얜 바인딩 할거 없음
        }
    }

    //View Holder
    inner class FillCardViewHolder(val binding : ItemTripcourseCardBaseFillBinding) : RecyclerView.ViewHolder(binding.root),
        View.OnCreateContextMenuListener {
        fun bind(card : Card) {
            if(card.imgUrl == "NONE"){
                binding.itemCardFillCoverImg.setImageResource(R.drawable.img_tripcourse_card_ex)
            } else {
                Glide.with(mContext).load(card.imgUrl).into(binding.itemCardFillCoverImg) //context 인자로 받아와야 함
            }

            binding.itemCardFillTitleTv.text = card.title
            binding.itemCardFillDateTv.text = card.date
            binding.itemCardFillBodyTv.text = card.body

            binding.root.setOnCreateContextMenuListener(this)
        }

        override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
            focusedCardPosition = adapterPosition

            val dialog = DialogClass(mContext)
            dialog.showDialog("카드 삭제", "카드를 삭제하시겠습니까?\n삭제된 카드는 저장되지 않습니다.", "확인")
        }

        private val onEditMenu : MenuItem.OnMenuItemClickListener = object : MenuItem.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem?): Boolean {
                when(item?.itemId) {
                    1002 -> { //삭제
                        DialogClass(mContext).showDialog("여행 카드 삭제", "여행 카드를 삭제하시겠습니까?\n 삭제한 카드는 복구되지 않습니다.", "삭제하기")
                    }
                    else -> { //에러
                        Toast.makeText(mContext, "에러가 발생했습니다.", Toast.LENGTH_LONG).show()
                    }
                }
                return true
            }
        }
    }

    /*---------- 오버라이딩 함수 ----------*/

    override fun getItemViewType(position: Int): Int {
        return tripCards[position].hasData
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //to use at View Holder
        return when(viewType) {
            FALSE -> { //데이터가 없는 경우 -> 빈 레이아웃
                val binding : ItemTripcourseCardBaseEmptyBinding //Create ItemView Object
                        = ItemTripcourseCardBaseEmptyBinding
                            .inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

                EmptyCardViewHolder(binding)
            }
            else -> {
                val binding : ItemTripcourseCardBaseFillBinding //Create ItemView Object
                        = ItemTripcourseCardBaseFillBinding
                            .inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

                FillCardViewHolder(binding)
            }
        }
    }

    //binding data and ViewHolder
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        holder.itemView.setOnClickListener { //카드 클릭시 이동
            focusedCardPosition = position
            cardClickListener.onItemClick(tripCards[position])
        }

        when(tripCards[position].hasData) {
            FALSE -> { //데이터가 없는 경우에 바인딩
                (holder as EmptyCardViewHolder).bind()

                holder.binding.itemCardEmptyDeleteIb.setOnClickListener{  //카드 삭제
                    onRemoveCard(position) //cards[position].id
                }
            }
            else -> { //데이터가 있는 경우에 바인딩
                (holder as FillCardViewHolder).bind(tripCards[position])
            }
        }
    }

    override fun getItemCount() : Int = tripCards.size

    /*---------- 추가 함수 ----------*/

    fun setItemClickListener(itemClickListener : CardClickListener) {
        cardClickListener = itemClickListener
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun onRemoveCard(position: Int){
//        deleteImage(Uri.parse(tripCards[focusedCardPosition].imgUrl)) //firebase 이미지 삭제
        notifyItemRemoved(position) // 얘 작동하는지 확인
        tripCards.removeAt(position)
    }

//    빈 카드를 삭제하는 함수
//    fun onRemoveEmptyCard() {
//        var i = 0
//        var deleteList = ArrayList<Int>()
//        for(i in 0..itemCount-1) {
//            if(cards[i].hasData == FALSE) {
//                deleteList.add(cards[i].courseIdx)
//            }
//        }
//        if(!deleteList.isEmpty()) {
//            for(i in 0..deleteList.size-1) {
//                deleteCard(deleteList[i])
//            }
//        }
//    }

//    private fun deleteImage(imgUrl: Uri) {
//        //todo firebase 삭제-focusedCardPosition을 이용해서
//        //todo storageRef 타입확인 후 참조해서 delete
//        val imageRef = storageRef.getReferenceFromUrl(imgUrl)
//        imageRef.delete()
//            .addOnSuccessListener(OnSuccessListener<Void?> {
//                Log.d("firebase", "onSuccess: deleted file")
//            }).addOnFailureListener(OnFailureListener {
//                Log.d("firebase", "onFailure: $it")
//            })
//    }

//    private fun uploadImage(selectedImgUri: Uri) {
//        val storage: FirebaseStorage? = FirebaseStorage.getInstance() //FirebaseStorage 인스턴스 생성
//        //파일 이름 생성
//        val fileName = "IMAGE_${SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())}"
//        //파일 업로드, 다운로드, 삭제, 메타데이터 가져오기 또는 업데이트를 하기 위해 참조를 생성.
//        //참조는 클라우드 파일을 가리키는 포인터라고 할 수 있음.(사진 저장 경로)
//        val imagesRef = storage!!.reference.child("images/").child(fileName)    //기본 참조 위치/images/${fileName}
//        //이미지 파일 업로드
//        imagesRef.putFile(selectedImgUri).addOnSuccessListener {
//            Toast.makeText(this, "파이어베이스 업로드 성공", Toast.LENGTH_SHORT).show()
//            //firebase url 저장
//            it.storage.downloadUrl.addOnSuccessListener { firebaseUrl->
//                Log.d("firebase Url", firebaseUrl.toString())
//                tripCards[focusedCardPosition].imgUrl = firebaseUrl.toString()
//            }.addOnFailureListener { }
//        }.addOnFailureListener {
//            Log.d("firebase failure", it.toString())
//            Toast.makeText(this, "파이어베이스 업로드 실패", Toast.LENGTH_SHORT).show()
//        }
//    }

    override fun onServerLoading() {
        Log.d("RVCardAdapter Response", "delete Card Loading")
    }

    override fun onServerSuccess() {
        Log.d("RVCardAdapter Response", "delete Card Success")
    }

    override fun onServerFailure(code: Int, message: String) {
        Log.d("RVCardAdapter Response", code.toString() + "delete Card Fail : " + message)
    }
}