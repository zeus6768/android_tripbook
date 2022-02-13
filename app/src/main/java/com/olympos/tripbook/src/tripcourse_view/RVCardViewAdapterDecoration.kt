package com.olympos.tripbook.src.tripcourse_view

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RVCardViewAdapterDecoration : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildAdapterPosition(view)
        val count = state.itemCount
        val offset = 195
        val otheroffset = 20

        if(position == 0) {
            outRect.top = otheroffset
            outRect.right = otheroffset
            outRect.left = otheroffset
            outRect.bottom = otheroffset
        }
        else {
            outRect.top = offset
            outRect.right = otheroffset
            outRect.left = otheroffset
            outRect.bottom = otheroffset
        }
//        else if (position/3 == 1) {
//            outRect.top =offset
//        }
//        else if (position/3 ==2) {
//            outRect
//        }
    }
}