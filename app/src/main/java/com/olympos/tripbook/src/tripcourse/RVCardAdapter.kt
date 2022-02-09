package com.olympos.tripbook.src.tripcourse

import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.olympos.tripbook.databinding.ItemTripcourseCardBaseEmptyBinding
import com.olympos.tripbook.databinding.ItemTripcourseCardBaseFillBinding
import com.olympos.tripbook.src.tripcourse.model.Card

class RVCardAdapter(private val cards : ArrayList<Card>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /*---------- 전역 변수 ----------*/

    private lateinit var cardClickListener: CardClickListener
//    private lateinit var context: Context

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
//            binding.itemCardFillCoverImg.setImageResource(card.coverImg)
            binding.itemCardFillTitleTv.text = card.title
            binding.itemCardFillDateTv.text = card.date
            binding.itemCardFillBodyTv.text = card.body

//            this.itemView.setOnCreateContextMenuListener(this)
        }

        override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {}
//            val edit : MenuItem? = menu?.add(Menu.NONE, 1001, 1, "편집")
//            val delete : MenuItem? = menu?.add(Menu.NONE, 1002, 2, "삭제")
//
//            edit?.setOnMenuItemClickListener(onEditMenu)
//            delete?.setOnMenuItemClickListener(onEditMenu)
//        }

//        private val onEditMenu : MenuItem.OnMenuItemClickListener = object : MenuItem.OnMenuItemClickListener {
//            override fun onMenuItemClick(item: MenuItem?): Boolean {
//                when(item?.itemId) {
//                    1001 -> { //편집
//
//                    }
//                    else -> { //삭제
//                        val builder : AlertDialog.Builder = object : AlertDialog.Builder(context)
//                        val view : View = LayoutInflater.from(context).inflate(R.layout.dialog_base, null, false)
//                        builder.setView(view)
//
//                        val dialog : AlertDialog = builder.create()
//                        val dialogBtn : Button = view.findViewById(R.id.dialog_base_ok_btn_tv)
//
//                        dialogBtn.setOnClickListener{
//                            cards.removeAt(adapterPosition)
//                            notifyItemRemoved(adapterPosition)
//
//                            dialog.dismiss()
//                        }
//                    }
//                }
//                return true
//            }
//        }
    }

    /*---------- 인터페이스 ----------*/

    //인터페이스 선언 -> 사용하려면 다시 객채 생성해야 함
    interface CardClickListener{
        fun onItemClick(card : Card)            //아이템 클릭 이벤트 인터페이스
//        fun onAddCard()                       //카드 추가를 위한 인터페이스
//        fun onRemoveCard(position : Int)      //카드 제거를 위한 인터페이스
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

    override fun getItemCount(): Int = cards.size

    /*---------- 추가 함수 ----------*/

    fun setItemClickListener(itemClickListener : CardClickListener) {
        cardClickListener = itemClickListener
    }

    fun getPosition() {
        return
    }

    private fun onRemoveCard(position: Int){
        cards.removeAt(position)
        notifyItemRemoved(position)
    }
}