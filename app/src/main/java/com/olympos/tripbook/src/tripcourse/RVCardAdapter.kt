package com.olympos.tripbook.src.tripcourse

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.olympos.tripbook.R
import com.olympos.tripbook.databinding.ItemTripcourseCardBaseEmptyBinding
import com.olympos.tripbook.databinding.ItemTripcourseCardBaseFillBinding
import com.olympos.tripbook.src.trip.TripActivity
import com.olympos.tripbook.src.tripcourse.model.Card

class RVCardAdapter(private val cards : ArrayList<Card>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /*---------- 전역 변수 ----------*/

    private lateinit var cardClickListener: CardClickListener

    /*---------- 내부 클래스 ----------*/

    //View Holder
    inner class EmptyCardViewHolder(val binding : ItemTripcourseCardBaseEmptyBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(card : Card) {
            //사실 얜 바인딩 할거 없음
        }
    }

    //View Holder
    inner class FillCardViewHolder(val binding : ItemTripcourseCardBaseFillBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(card : Card) {
            binding.itemCardFillCoverImg.setImageResource(card.coverImg)
            binding.itemCardFillTitleTv.setText(card.title)
            binding.itemCardFillDateTv.setText(card.date)
            binding.itemCardFillBodyTv.setText(card.body)
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
                (holder as EmptyCardViewHolder).bind(cards[position])

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

//    @SuppressLint("NotifyDataSetChanged")
//    fun onAddCard(card: Card) {
////        this.cards.clear()          //비우고
////        this.cards.addAll(cards)    //새로운 어레이 리스트로 둠
//        cards.add(card)
//
//        notifyDataSetChanged()
//    }

    private fun onRemoveCard(position: Int){
        cards.removeAt(position)
        notifyItemRemoved(position)
    }
}