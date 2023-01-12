package com.olympos.tripbook.src.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.olympos.tripbook.config.BaseFragment
import com.olympos.tripbook.databinding.FragmentMainEmptyHomeBinding

class EmptyHomeFragment : BaseFragment() {

    lateinit var binding: FragmentMainEmptyHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainEmptyHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

}