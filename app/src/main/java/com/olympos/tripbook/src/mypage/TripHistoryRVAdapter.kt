package com.olympos.tripbook.src.mypage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.olympos.tripbook.databinding.ItemMypageTripHistoryBinding
import com.olympos.tripbook.src.trip.model.Trip

class TripHistoryRVAdapter(private val tripList : ArrayList<Trip>): RecyclerView.Adapter<TripHistoryRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemMypageTripHistoryBinding = ItemMypageTripHistoryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tripList[position])
    }

    override fun getItemCount(): Int {
        return tripList.size
    }

    inner class ViewHolder(private val binding: ItemMypageTripHistoryBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(trip: Trip) {
            binding.mypageHistoryTitleTv.text = trip.tripTitle
            binding.mypageHistoryContentTv.text = trip.arrivalDate

//            TODO(
//                "Class Trip 및 Trip API 3-1 수정 필요" +
//                        "binding.myPageHistoryTitleTv.text = trip.createdDate.toString()" +
//                        "에 다녀온 " +
//                        "'<'getTitle()'>'" +
//                        "을 등록했어요"
//            )

        }
    }

}