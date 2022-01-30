package com.olympos.tripbook.src.tripcourse

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RVCardAdapter : RecyclerView.Adapter<RVLookerStorageAdapter.LookerViewHolder>() {

    /*---------- 전역 변수 ----------*/

    private val cards = ArrayList<Song>()

    lateinit var lookerItemClickListener: LookerItemClickListener

    /*---------- 내부 클래스 ----------*/
    /*---------- 인터페이스 ----------*/
    /*---------- 오버라이딩 함수 ----------*/
    /*---------- 추가 함수 ----------*/



    //인터페이스 선언 -> 사용하려면 다시 객채 생성해야 함
    interface LookerItemClickListener{
        fun onRemoveSong(songId : Int)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): LookerViewHolder {
        val binding : ItemLookerStorageAlbumBinding //요게 뭔지 잘 이해가 안가넹
                = ItemLookerStorageAlbumBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return LookerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LookerViewHolder, position: Int) {
        holder.bind(songs[position])
        holder.binding.itemLoockerMoreIv.setOnClickListener{
            removeSong(position)
            lookerItemClickListener.onRemoveSong(songs[position].id)
        }
    }

    override fun getItemCount(): Int = songs.size

    fun setItemClickListener(itemClickListener : LookerItemClickListener) {
        lookerItemClickListener = itemClickListener
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addSongs(songs: ArrayList<Song>) {
        this.songs.clear()
        this.songs.addAll(songs)

        notifyDataSetChanged()
    }

    fun removeSong(position: Int){
        songs.removeAt(position)
        notifyDataSetChanged()
    }

    inner class LookerViewHolder(val binding : ItemLookerStorageAlbumBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(song : Song) {
            binding.itemLookerTitleTv.text = song.title
            binding.itemLookerSingerTv.text = song.singer
            binding.itemLookerAlbumImgIv.setImageResource(song.albumImg!!)
        }
    }


}