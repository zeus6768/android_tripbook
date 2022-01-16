package com.olympos.tripbook.src.tripcourse

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.olympos.tripbook.databinding.ActivityTripcourseBinding

class TripcourseActivity : AppCompatActivity() {

    lateinit var binding : ActivityTripcourseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTripcourseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView();
    }

    private fun initView() {

    }
}