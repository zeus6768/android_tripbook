package com.olympos.tripbook.src.tripcourse.model

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.olympos.tripbook.databinding.ItemCardBinding
import com.olympos.tripbook.databinding.ItemLine1Binding
import com.olympos.tripbook.databinding.ItemLine2Binding
import com.olympos.tripbook.databinding.ItemLine3Binding

class TripcourseRVAdapter(private val courseList: ArrayList<Tripcourse>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val CARD = 0
    val LINE1 = 1
    val LINE2 = 2
    val LINE3 = 3

    //클릭 인터페이스 정의
    interface MyItemClickListener{
        fun onItemClick(course : Tripcourse)
//        fun onRemoveAlbum(position : Int)
    }

    //리스너 객체를 전달받는 함수랑 리스너 객체를 저장할 변수
    private lateinit var mItemClickListener : MyItemClickListener

    fun setMyItemClickListener(itemClickListner: MyItemClickListener) {
        mItemClickListener = itemClickListner
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            position % 2 == 0 -> CARD
            position % 8 == 1 -> LINE1
            position % 4 == 3 -> LINE2
            position % 8 == 5 -> LINE3
            else -> throw IllegalStateException("Not Found ViewHolder Type")
        }
    }

    //뷰홀더를 생성해줘야 할 때 호출되는 함수 => 아이템 뷰 객체를 만들어서 뷰홀더에 던져준다.
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            CARD -> {
                val binding: ItemCardBinding = ItemCardBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
                ViewHolderCard(binding)
            }
            LINE1 -> {
                val binding: ItemLine1Binding = ItemLine1Binding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
                ViewHolderLine1(binding)
            }
            LINE2 -> {
                val binding: ItemLine2Binding = ItemLine2Binding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
                ViewHolderLine2(binding)
            }
            LINE3 -> {
                val binding: ItemLine3Binding = ItemLine3Binding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
                ViewHolderLine3(binding)
            }
            else -> {
                throw IllegalStateException("Not Found ViewHolder Type $viewType")
            }
        }
    }

    //뷰홀더에 데이터를 바인딩해줘야 할 때마다 호출되는 함수 => 엄청나게 많이 호출
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is ViewHolderCard -> {
                holder.bind(courseList[position])
                holder.setIsRecyclable(false)
            }
            is ViewHolderLine1 -> {
                holder.setIsRecyclable(false)
            }
            is ViewHolderLine2 -> {
                holder.setIsRecyclable(false)
            }
            is ViewHolderLine3 -> {
                holder.setIsRecyclable(false)
            }
        }
        holder.itemView.setOnClickListener { mItemClickListener.onItemClick(courseList[position]) }
//        holder.binding.itemAlbumTitleTv.setOnClickListener{ mItemClickListener.onRemoveAlbum(position)}
    }

    //데이터 세트 크기를 알려주는 함수 => 리사이클러뷰가 마지막이 언제인지를 알게 된다.
    override fun getItemCount(): Int = courseList.size

    //뷰홀더
    inner class ViewHolderCard(private val binding: ItemCardBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(course: Tripcourse) {
            binding.itemTitleTv.text = course.courseTitle
            binding.itemDateTv.text = course.courseDate
            binding.itemContentTv.text = course.courseContent
            course.courseImg?.let { binding.itemCardPicIv.setImageResource(it) }
        }
    }
    inner class ViewHolderLine1(binding: ItemLine1Binding): RecyclerView.ViewHolder(binding.root) {
    }
    inner class ViewHolderLine2(binding: ItemLine2Binding): RecyclerView.ViewHolder(binding.root) {
    }
    inner class ViewHolderLine3(binding: ItemLine3Binding): RecyclerView.ViewHolder(binding.root) {
    }

//    fun addItems(albums: ArrayList<Album>) {
//        albumList.clear()
//        albumList.addAll(albums)
//        notifyDataSetChanged()
//    }
//
    fun addItem(course: Tripcourse) {
        courseList.add(course)
        courseList.add(course)
        notifyDataSetChanged()
    }
//
//    fun removeItems() {
//        albumList.clear()
//        notifyDataSetChanged()
//    }
//
//    fun removeItem(position: Int) {
//        albumList.removeAt(position)
//        notifyDataSetChanged()
//    }


}