package com.olympos.tripbook.config

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatCheckBox

class HashtagCheckbox(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : AppCompatCheckBox(context, attrs, defStyleAttr) {
//    fun CustomFontCheckBox(context: Context?) {
//        super(context)
//        init()
//    }
//
//    fun CustomFontCheckBox(context: Context?, attrs: AttributeSet?) {
//        super(context, attrs)
//        init()
//    }
//
//    fun CustomFontCheckBox(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) {
//        super(context, attrs, defStyleAttr)
//        init()
//    }
//
//    private fun init() {
//        //set your typeface here.
//        setTypeface("");
//    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
    }


}