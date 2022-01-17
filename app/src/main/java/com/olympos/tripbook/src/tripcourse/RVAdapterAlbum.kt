package com.olympos.tripbook.src.tripcourse

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.olympos.tripbook.databinding.ItemTripcourseAlbumRightBinding
//
//class RVAdapterAlbum() : RecyclerView.Adapter<RVAdapterAlbum.AlbumViewHolder>(){
//
//    /*---------- 전역 변수 ----------*/
//
//    lateinit var AlbumItemClickListener : AlbumItemClickListener
//
//    /*---------- 내부 클래스 ----------*/
//
//    inner class AlbumViewHolder(val binding : ItemTripcourseAlbumRightBinding) : RecyclerView.ViewHolder(binding.root) {
//        fun bind(){
//
//        }
//    }
//
//    /*---------- 인터페이스 ----------*/
//
//    interface AlbumItemClickListener {
//        fun onItemClick()
//        fun onRemoveSong()
//    }
//
//    /*---------- 오버라이딩 함수 ----------*/
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVAdapterAlbum.AlbumViewHolder {
//        val binding :ItemTripcourseAlbumRightBinding
//            = ItemTripcourseAlbumRightBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//
//        return AlbumViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: RVAdapterAlbum.AlbumViewHolder, position: Int) {
//        holder.itemView.setOnClickListener {
//            AlbumItemClickListener.onItemClick()
//        }
//    }
//
//    override fun getItemCount(): Int =3//임시
//
//
//    /*---------- 추가 함수 ----------*/
//
//    fun setMyItemClickListener(itemClickListener: AlbumItemClickListener) {
//        AlbumItemClickListener = itemClickListener
//    }
//}