package com.olympos.tripbook.src.tripcourse.view

import android.annotation.SuppressLint
import android.content.Context
import android.view.*
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.olympos.tripbook.R
import com.olympos.tripbook.config.BaseDialog
import com.olympos.tripbook.databinding.*
import com.olympos.tripbook.src.tripcourse.model.TripCourse

class TripCourseRVAdapter(private val mContext: Context, private val courseList: ArrayList<TripCourse>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val EMPT_CARD = 0
    val FILL_CARD = 1
    val LINE1 = 2
    val LINE2 = 3
    val LINE3 = 4

    private var focusedCardPosition = -1

    //클릭 인터페이스 정의
    interface MyItemClickListener{
        fun onItemClick(course : TripCourse)
    }

    //리스너 객체를 전달받는 함수랑 리스너 객체를 저장할 변수
    private lateinit var mItemClickListener : MyItemClickListener

    fun setMyItemClickListener(itemClickListner: MyItemClickListener) {
        mItemClickListener = itemClickListner
    }

    inner class DialogClass(context: Context) : BaseDialog.BaseDialogClickListener {
        val thisContext = context
        override fun onOKClicked() {
            onRemoveCard(focusedCardPosition)
//            notifyItemRemoved(focusedCardPosition)
//            courseList.removeAt(focusedCardPosition)
        }
        override fun onCancelClicked() {

        }
        fun showDialog(title: String, message: String , okMessage: String) {
            val dig = BaseDialog(thisContext)
            dig.listener = this
            dig.show(title, message, okMessage)
        }
    }

    override fun getItemViewType(position: Int): Int {
        focusedCardPosition = position
        return when {
            (position % 2 == 0) && (courseList[position].courseTitle.isEmpty()) -> EMPT_CARD
            (position % 2 == 0) && (courseList[position].courseTitle.isNotEmpty()) -> FILL_CARD
            position % 8 == 1 -> LINE1
            position % 4 == 3 -> LINE2
            position % 8 == 5 -> LINE3
            else -> throw IllegalStateException("Not Found ViewHolder Type")
        }
    }

    //뷰홀더를 생성해줘야 할 때 호출되는 함수 => 아이템 뷰 객체를 만들어서 뷰홀더에 던져준다.
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            FILL_CARD -> {
                val binding: ItemCardBinding = ItemCardBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
                ViewHolderCardFill(binding)
            }
            EMPT_CARD -> {
                val binding: ItemCardEmptyBinding = ItemCardEmptyBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
                ViewHolderCardEmpty(binding)
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
            is ViewHolderCardFill -> {
                holder.bind(courseList[position])
                holder.setIsRecyclable(false)
            }
            is ViewHolderCardEmpty -> {
                holder.binding.itemCardEmptyDeleteIb.setOnClickListener{
                    onRemoveCard(position)
                }
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
    inner class ViewHolderCardFill(private val binding: ItemCardBinding): RecyclerView.ViewHolder(binding.root), View.OnCreateContextMenuListener {
        fun bind(course: TripCourse) {
//            if( course.courseImg == null ){
//                binding.itemCardPicIv.setImageResource(R.drawable.img_tripcourse_card_ex)
//            } else {
//                //Glide.with(mContext).load(card.imgUrl).into(binding.itemCardFillCoverImg) //context 인자로 받아와야 함
//                course.courseImg?.let { binding.itemCardPicIv.setImageResource(it) }
//            }

            binding.itemTitleTv.text = course.courseTitle
            binding.itemDateTv.text = course.courseDate
            binding.itemContentTv.text = course.courseContent
            course.courseImg?.let { binding.itemCardPicIv.setImageResource(it) }

            binding.root.setOnCreateContextMenuListener(this)
        }

        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            focusedCardPosition = adapterPosition

            val dialog = DialogClass(mContext)
            dialog.showDialog("카드 삭제", "카드를 삭제하시겠습니까?\n삭제된 카드는 저장되지 않습니다.", "확인")
        }
    }
    inner class ViewHolderCardEmpty(val binding : ItemCardEmptyBinding) : RecyclerView.ViewHolder(binding.root) {
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
@SuppressLint("NotifyDataSetChanged")
fun addItem(course: TripCourse) {
        courseList.add(course)
        courseList.add(course)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun onRemoveCard(position: Int){
//        deleteImage(tripCards[focusedCardPosition].imgUrl) //firebase 이미지 삭제

        courseList.removeAt(position)
        courseList.removeAt(position-1)
//        notifyItemRemoved(position)
//        notifyItemRemoved(position-1)
//        notifyItemRangeChanged(position, courseList.size);
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