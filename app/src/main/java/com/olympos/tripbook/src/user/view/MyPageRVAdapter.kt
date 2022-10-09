package com.olympos.tripbook.src.user.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.olympos.tripbook.databinding.ItemUserMyPageTripHistoryBinding
import com.olympos.tripbook.src.trip.model.Trip
import com.olympos.tripbook.utils.ApplicationClass.Companion.DateUnit.DAY
import com.olympos.tripbook.utils.ApplicationClass.Companion.DateUnit.MONTH
import com.olympos.tripbook.utils.ApplicationClass.Companion.parseDateToKorean

class MyPageRVAdapter(private val tripList : ArrayList<Trip>): RecyclerView.Adapter<MyPageRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemUserMyPageTripHistoryBinding = ItemUserMyPageTripHistoryBinding.inflate(
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

    inner class ViewHolder(private val binding: ItemUserMyPageTripHistoryBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(trip: Trip) {

            val historyContentText = parseDateToKorean(trip.departureDate, MONTH) + "에 다녀온 " + trip.tripTitle + " 여행을 등록했어요."

            binding.mypageHistoryTitleTv.text = parseDateToKorean(trip.createdDate, DAY)
            binding.mypageHistoryContentTv.text = historyContentText

        }
    }

}