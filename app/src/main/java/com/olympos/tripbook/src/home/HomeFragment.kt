package com.olympos.tripbook.src.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.olympos.tripbook.config.BaseFragment
import com.olympos.tripbook.databinding.FragmentHomeBinding
import com.olympos.tripbook.src.home.model.CardsView
import com.olympos.tripbook.src.home.model.HomeService
import com.olympos.tripbook.src.tripcourse.model.Card
import com.olympos.tripbook.src.tripcourse_view.RVCardAdapter

class HomeFragment : BaseFragment() {
    lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

}