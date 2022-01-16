package com.olympos.tripbook.src.tripcourse

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.olympos.tripbook.databinding.ActivityTripcourseRecordBinding

class TripcourseRecordActivity : AppCompatActivity() {

    lateinit var binding : ActivityTripcourseRecordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTripcourseRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }

}