package com.olympos.tripbook.src.trip_view

import android.os.Bundle
import com.olympos.tripbook.config.BaseActivity
import com.olympos.tripbook.databinding.ActivityPreviousTripBinding

class PreviousTripActivity : BaseActivity() {

    lateinit var binding : ActivityPreviousTripBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding = ActivityPreviousTripBinding.inflate(layoutInflater)


    }
}