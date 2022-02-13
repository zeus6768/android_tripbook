package com.olympos.tripbook.src.tripcourse

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.olympos.tripbook.R
import com.olympos.tripbook.config.BaseDialog
import com.olympos.tripbook.databinding.ItemTripcourseCardBaseEmptyBinding
import com.olympos.tripbook.databinding.ItemTripcourseCardBaseFillBinding
import com.olympos.tripbook.src.tripcourse.model.Card
import com.olympos.tripbook.src.tripcourse.model.CardService
import com.olympos.tripbook.src.tripcourse.model.ServerView

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

    private var cards = ArrayList<Card>()
    private val mContext = context

//    private var baseDialog = BaseDialog(context)

    inner class DialogClass(context: Context) : BaseDialog.BaseDialogClickListener {
        val thisContext = context
        override fun onOKClicked() {
            deleteCard()
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
            if(card.coverImg == null){
                binding.itemCardFillCoverImg.setImageResource(R.drawable.img_tripcourse_card_ex)
            } else {
                Glide.with(mContext).load(card.coverImg).into(binding.itemCardFillCoverImg) //context 인자로 받아와야 함
            }

            binding.itemCardFillTitleTv.text = card.title
            binding.itemCardFillDateTv.text = card.date
            binding.itemCardFillBodyTv.text = card.body

            binding.root.setOnCreateContextMenuListener(this)
            //this.itemView.setOnCreateContextMenuListener(this)
        }

        override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
            //Log.d("asdf", "ASDFASDF")
            //baseDialog.show("여행 카드 삭제", "여행 카드를 삭제하시겠습니까?\n 삭제한 카드는 복구되지 않습니다.", "삭제하기")
            //DialogClass(mContext).showDialog("여행 카드 삭제", "여행 카드를 삭제하시겠습니까?\n 삭제한 카드는 복구되지 않습니다.", "삭제하기")

            val edit : MenuItem? = menu?.add(Menu.NONE, 1001, 1, "편집")
            val delete : MenuItem? = menu?.add(Menu.NONE, 1002, 2, "삭제")

            edit?.setOnMenuItemClickListener(onEditMenu)
            delete?.setOnMenuItemClickListener(onEditMenu)
        }

        private val onEditMenu : MenuItem.OnMenuItemClickListener = object : MenuItem.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem?): Boolean {
                when(item?.itemId) {
                    1001 -> { //편집

                    }
                    else -> { //삭제
                        DialogClass(mContext).showDialog("여행 카드 삭제", "여행 카드를 삭제하시겠습니까?\n 삭제한 카드는 복구되지 않습니다.", "삭제하기")
                    }
                }
                return true
            }
        }
    }


    /*---------- 오버라이딩 함수 ----------*/

    override fun getItemViewType(position: Int): Int {
        return cards[position].hasData
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
       val view : View?
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
            cardClickListener.onItemClick(cards[position])
        }

        when(cards[position].hasData) {
            FALSE -> { //데이터가 없는 경우에 바인딩
                (holder as EmptyCardViewHolder).bind()

                holder.binding.itemCardEmptyDeleteIb.setOnClickListener{  //카드 삭제
                    onRemoveCard(position) //cards[position].id
                }
            }
            else -> { //데이터가 있는 경우에 바인딩
                (holder as FillCardViewHolder).bind(cards[position])
            }
        }
    }

    override fun getItemCount() : Int = cards.size

    /*---------- 추가 함수 ----------*/

    fun setItemClickListener(itemClickListener : CardClickListener) {
        cardClickListener = itemClickListener
    }

    fun deleteCard() {
        val cardservice = CardService()
        cardservice.setServerView(this)
        cardservice.deleteCard(userIdx, tripIdx)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setCards(cards : ArrayList<Card>) {
        this.cards.clear()
        this.cards.addAll(cards)

        notifyDataSetChanged()
    }

    fun addCard(card : Card) {
        this.cards.add(card)

        notifyItemInserted(itemCount-1)
    }

    fun removeCard(position: Int) {

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun onRemoveCard(position: Int){
        cards.removeAt(position)
//        notifyItemRemoved(position) // 얘 문제임 아마 이미 지워진 애를 지워졌다고 알려서 그런게 아닐까 추측함
        //ex) 3번 사라짐(124 남음) 근데 3번 사라졌다고 알림(4번 지목함) -> 자세히는 나도 모름

        deleteCard()

        notifyDataSetChanged()
    }

    override fun onServerLoading() {
        TODO("Not yet implemented")
    }

    override fun onServerSuccess() {
        TODO("Not yet implemented")
    }

    override fun onServerFailure(code: Int, message: String) {
        TODO("Not yet implemented")
    }
}