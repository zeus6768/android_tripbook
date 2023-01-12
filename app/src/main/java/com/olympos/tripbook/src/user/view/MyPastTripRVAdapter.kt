package com.olympos.tripbook.src.user.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.olympos.tripbook.R
import com.olympos.tripbook.src.trip.model.Trip
import com.olympos.tripbook.utils.ApplicationClass.Companion.DateUnit.DAY
import com.olympos.tripbook.utils.ApplicationClass.Companion.dateToKorean

class MyPastTripRVAdapter(private val context: Context, private val tripList : ArrayList<Trip>): RecyclerView.Adapter<MyPastTripRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_user_my_past_trip, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.apply {

            val testImage: Int = if (position%2 == 0) {
                R.drawable.img_test_cat_1
            } else {
                R.drawable.img_test_cat_2
            }

            image.clipToOutline = true
            Glide.with(context)
                .load(tripList[position].representativeImage)
                .placeholder(testImage)
                .error(testImage)
                .into(image)

            date.text = dateToKorean(tripList[position].departureDate, DAY)
            title.text = tripList[position].tripTitle

        }

    }

    override fun getItemCount(): Int = tripList.size

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.mypasttrip_iv)
        val date: TextView = itemView.findViewById(R.id.mypasttrip_date_tv)
        val title: TextView = itemView.findViewById(R.id.mypasttrip_title_tv)
    }

}