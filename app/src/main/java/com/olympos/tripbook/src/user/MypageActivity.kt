package com.olympos.tripbook.src.user

import android.os.Bundle
import android.os.PersistableBundle
import com.olympos.tripbook.config.BaseActivity
import com.olympos.tripbook.databinding.ActivityUserMypageBinding

class MypageActivity : BaseActivity() {
    private lateinit var binding: ActivityUserMypageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserMypageBinding.inflate(layoutInflater)
    }
}