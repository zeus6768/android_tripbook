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

    /*---------- 인터페이스 ----------*/

    //인터페이스 선언 -> 사용하려면 다시 객채 생성해야 함
    interface CardClickListener{
        fun onItemClick(card : Card)            //아이템 클릭 이벤트 인터페이스
//        fun addCard(card : Card)                       //카드 추가를 위한 인터페이스
//        fun onRemoveCard(position : Int)      //카드 제거를 위한 인터페이스
    }

    /*---------- 전역 변수 ----------*/

    //인터페이스 구현
    private lateinit var cardClickListener: CardClickListener

    private val mContext = context

//    private var baseDialog = BaseDialog(context)

    inner class DialogClass(context: Context) : BaseDialog.BaseDialogClickListener {
        val thisContext = context
        override fun onOKClicked() {
            // todo 카드의 courseIdx 받아와서 deleteCard(courseIdx)
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
            //Log.d("asdf", "ASDFASDF")
            //baseDialog.show("여행 카드 삭제", "여행 카드를 삭제하시겠습니까?\n 삭제한 카드는 복구되지 않습니다.", "삭제하기")
            //DialogClass(mContext).showDialog("여행 카드 삭제", "여행 카드를 삭제하시겠습니까?\n 삭제한 카드는 복구되지 않습니다.", "삭제하기")

            //val edit : MenuItem? = menu?.add(Menu.NONE, 1001, 1, "편집")
            val delete : MenuItem? = menu?.add(Menu.NONE, 1002, 2, "삭제")

            //edit?.setOnMenuItemClickListener(onEditMenu)
            delete?.setOnMenuItemClickListener(onEditMenu)
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

//    fun deleteCard(courseIdx : Int) {
//        val cardservice = CardService()
//        cardservice.setServerView(this)
//        cardservice.deleteCard(getUserIdx().toString(), courseIdx.toString())
//    }

//    @SuppressLint("NotifyDataSetChanged")
//    fun setCards(cards : ArrayList<Card>) {
//        this.cards.clear()
//        this.cards.addAll(cards)
//
//        notifyDataSetChanged()
//    }

    fun addCard(card : Card) {
        tripCards.add(card)

        notifyItemInserted(itemCount-1)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun onRemoveCard(position: Int){
//        deleteImage(Uri.parse(tripCards[focusedCardPosition].imgUrl)) //firebase 이미지 삭제
        //deleteCard(cards[position].courseIdx)
        notifyItemRemoved(position) // 얘 작동하는지 확인
        tripCards.removeAt(position)
        //notifyItemRemoved(position) // 얘 문제임 아마 이미 지워진 애를 지워졌다고 알려서 그런게 아닐까 추측함
        //ex) 3번 사라짐(124 남음) 근데 3번 사라졌다고 알림(4번 지목함) -> 자세히는 나도 모름
        //notifyDataSetChanged()
    }

//    fun onRemoveEmptyCard() {
//        var i = 0
//        var deleteList = ArrayList<Int>()
//        for(i in 0..itemCount-1) {
//            if(cards[i].hasData == FALSE) {
//                deleteList.add(cards[i].courseIdx)
//            }
//        }
//        if(!deleteList.isEmpty()) {
//            //todo deleteList에 있는 카드들의 courseIdx를 가지고 서버에서 삭제
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