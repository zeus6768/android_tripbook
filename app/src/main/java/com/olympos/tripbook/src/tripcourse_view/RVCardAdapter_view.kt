package com.olympos.tripbook.src.tripcourse_view

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.olympos.tripbook.databinding.ItemTripcourseCardBaseFillBinding
import com.olympos.tripbook.src.tripcourse.model.Card
import com.olympos.tripbook.src.tripcourse_view.model.filledCards
import com.olympos.tripbook.src.tripcourse_view.model.focusedViewCardPosition

class RVCardAdapter_view(context : Context) : RecyclerView.Adapter<RVCardAdapter_view.FillCardViewHolder>() {

    /*---------- 인터페이스 ----------*/

    //인터페이스 선언 -> 사용하려면 다시 객채 생성해야 함
    interface CardClickListener{
        fun onItemClick()            //아이템 클릭 이벤트 인터페이스
    }

    /*---------- 전역 변수 ----------*/

    private lateinit var cardClickListener: CardClickListener

    private val mContext = context

    /*---------- 내부 클래스(뷰 홀더) ----------*/

    //View Holder
    inner class FillCardViewHolder(val binding : ItemTripcourseCardBaseFillBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(card : Card) {
            Glide.with(mContext).load(card.coverImg).into(binding.itemCardFillCoverImg) //context 인자로 받아와야 함

            binding.itemCardFillTitleTv.text = card.title
            binding.itemCardFillDateTv.text = card.date
            binding.itemCardFillBodyTv.text = card.body
        }
    }

    /*---------- 오버라이딩 함수 ----------*/

    override fun getItemViewType(position: Int): Int = filledCards.size

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RVCardAdapter_view.FillCardViewHolder {
        //to use at View Holder
        val binding : ItemTripcourseCardBaseFillBinding //Create ItemView Object
                = ItemTripcourseCardBaseFillBinding
            .inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return FillCardViewHolder(binding)
    }

    //binding data and ViewHolder
    override fun onBindViewHolder(holder: FillCardViewHolder, position: Int) {
        holder.itemView.setOnClickListener { //카드 클릭시 이동
            focusedViewCardPosition = position
            Log.d("show position", "position : $position,, focusedCardPosition : $focusedViewCardPosition")
            cardClickListener.onItemClick()
        }

        (holder as FillCardViewHolder).bind(filledCards[position])
    }

    override fun getItemCount() : Int = filledCards.size

    /*---------- 추가 함수 ----------*/

    fun setItemClickListener(itemClickListener : CardClickListener) {
        cardClickListener = itemClickListener
    }
}