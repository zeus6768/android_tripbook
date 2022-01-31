package com.olympos.tripbook.src.tripcourse

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.olympos.tripbook.databinding.ItemTripcourseCardBaseEmptyBinding
import com.olympos.tripbook.src.tripcourse.model.Card

class RVCardAdapter(private val card : ArrayList<Card>) : RecyclerView.Adapter<RVCardAdapter.CardViewHolder>() {

    /*---------- 전역 변수 ----------*/

    private val cards = ArrayList<Card>()

    lateinit var cardClickListener: CardClickListener

    /*---------- 내부 클래스 ----------*/

    //View Holder
    inner class CardViewHolder(val binding : ItemTripcourseCardBaseEmptyBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(card : Card) {
//            binding.itemTripcourseCardTitleTv.setText(card.cardTitle)
        }
    }

    /*---------- 인터페이스 ----------*/

    //인터페이스 선언 -> 사용하려면 다시 객채 생성해야 함
    interface CardClickListener{
        fun onItemClick(card : Card)            //아이템 클릭 이벤트 인터페이스
//        fun onAddCard()                       //카드 추가를 위한 인터페이스
//        fun onRemoveCard(position : Int)      //카드 제거를 위한 인터페이스
    }

    /*---------- 오버라이딩 함수 ----------*/

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): CardViewHolder {
        val binding : ItemTripcourseCardBaseEmptyBinding //Create ItemView Object
                = ItemTripcourseCardBaseEmptyBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return CardViewHolder(binding) //to use at View Holder
    }

    //binding data and ViewHolder
    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(cards[position])
        holder.itemView.setOnClickListener { //카드 클릭시 이동
            cardClickListener.onItemClick(cards[position])
        }
        holder.binding.itemCardEmptyDeleteIb.setOnClickListener{  //카드 삭제
            onRemoveCard(position) //cards[position].id
        }

    }

    override fun getItemCount(): Int = cards.size

    /*---------- 추가 함수 ----------*/

    fun setItemClickListener(itemClickListener : CardClickListener) {
        cardClickListener = itemClickListener
    }

//    @SuppressLint("NotifyDataSetChanged")
//    fun onAddCard(card: Card) {
////        this.cards.clear()          //비우고
////        this.cards.addAll(cards)    //새로운 어레이 리스트로 둠
//        cards.add(card)
//
//        notifyDataSetChanged()
//    }

    fun onRemoveCard(position: Int){
        cards.removeAt(position)

//        notifyDataSetChanged()
        notifyItemRemoved(position)
    }
}