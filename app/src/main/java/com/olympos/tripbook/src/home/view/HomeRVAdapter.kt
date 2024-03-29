package com.olympos.tripbook.src.home.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.olympos.tripbook.R
import com.olympos.tripbook.src.home.view.HomeRVAdapter.ViewHolderType.*
import com.olympos.tripbook.src.tripcourse.model.TripCourse

class HomeRVAdapter(private val context: Context, private val tripCourses: ArrayList<TripCourse>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    enum class ViewHolderType {
        CARD, LINE1, LINE2, LINE3;
        fun getType() = this.ordinal
    }

    interface ItemClickListener {
        fun onClick(course : TripCourse)
    }

    private lateinit var itemClickListener : ItemClickListener

    override fun getItemViewType(position: Int): Int {
        return when {
            position % 2 == 0 -> CARD.getType()
            position % 8 == 1 -> LINE1.getType()
            position % 4 == 3 -> LINE2.getType()
            position % 8 == 5 -> LINE3.getType()
            else -> throw IllegalStateException("ViewHolder Type Not Found")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            CARD.getType() -> {
                val binding = LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.item_card, parent, false)
                ViewHolderCard(binding)
            }
            LINE1.getType() -> {
                val binding = LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.item_line1, parent, false)
                ViewHolderLine1(binding)
            }
            LINE2.getType() -> {
                val binding = LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.item_line2, parent, false)
                ViewHolderLine2(binding)
            }
            LINE3.getType() -> {
                val binding = LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.item_line3, parent, false)
                ViewHolderLine3(binding)
            }
            else -> {
                throw IllegalStateException("ViewHolder Type Not Found $viewType")
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val course = tripCourses[position]

        when (holder) {

            is ViewHolderCard -> with(holder) {
                holder.apply {
                    title.text = course.courseTitle
                    date.text = course.courseDate
                    content.text = course.courseComment
                    Glide.with(context)
                        .load(course.courseImg)
                        .placeholder(R.drawable.img_basic_add)
                        .error(R.drawable.img_basic_add)
                        .into(image)
                }
                setIsRecyclable(false)
            }

            else -> holder.setIsRecyclable(false)
        }

        holder.itemView.setOnClickListener { itemClickListener.onClick(tripCourses[position]) }
    }

    override fun getItemCount(): Int = tripCourses.size

    inner class ViewHolderCard(itemView: View): RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.item_title_tv)
        val date: TextView = itemView.findViewById(R.id.item_date_tv)
        val content: TextView = itemView.findViewById(R.id.item_content_tv)
        val image: ImageView = itemView.findViewById(R.id.item_card_pic_iv)
    }

    inner class ViewHolderLine1(itemView: View): RecyclerView.ViewHolder(itemView)
    inner class ViewHolderLine2(itemView: View): RecyclerView.ViewHolder(itemView)
    inner class ViewHolderLine3(itemView: View): RecyclerView.ViewHolder(itemView)

}