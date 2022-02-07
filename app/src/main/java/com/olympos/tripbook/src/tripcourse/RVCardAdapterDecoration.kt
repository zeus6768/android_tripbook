package com.olympos.tripbook.src.tripcourse

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RVCardAdapterDecoration : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildAdapterPosition(view)
        val count = state.itemCount
        val offset = 100
        val otheroffset = 20

        if(position == 0) {
            outRect.offset(20, 20)
        }
        else {
            outRect.top = offset
            outRect.offset(20, 20)
        }
//        else if (position/3 == 1) {
//            outRect.top =offset
//        }
//        else if (position/3 ==2) {
//            outRect
//        }
    }
}