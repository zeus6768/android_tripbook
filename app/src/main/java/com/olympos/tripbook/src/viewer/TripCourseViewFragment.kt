package com.olympos.tripbook.src.viewer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.olympos.tripbook.config.BaseFragment
import com.olympos.tripbook.databinding.FragmentTripcourseViewBinding
import com.olympos.tripbook.src.viewer.view.TripCourseViewRVAdapter

class TripCourseViewFragment : BaseFragment() {

    private lateinit var binding: FragmentTripcourseViewBinding
    private lateinit var viewRVAdapter : TripCourseViewRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTripcourseViewBinding.inflate(inflater, container, false)

        initRecyclerView()

        return binding.root
    }

    private fun initRecyclerView() {
        //todo 리사이클러 뷰 추가
    }
}