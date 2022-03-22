package com.olympos.tripbook.src.tripcourse

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RVCardAdapterDecoration(val dividerHeight : Int = 30, val dividerColor : Int = Color.GREEN) : RecyclerView.ItemDecoration() {

    lateinit var paint : Paint

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {

        drawDivider(c, parent, color = Color.RED)

        //super.onDrawOver(c, parent, state)
    }

    private fun drawDivider(c: Canvas, recyclerView: RecyclerView, color : Int) {
        paint = Paint()

        paint.color = color
        for (i in 0 until recyclerView.childCount) {
            val child = recyclerView.getChildAt(i)
            val param = child.layoutParams as RecyclerView.LayoutParams

            val dividerTop = child.bottom + param.bottomMargin
            val dividerBottom = dividerTop + dividerHeight

            c.drawRect(
                child.left.toFloat(),
                dividerTop.toFloat(),
                child.right.toFloat(),
                dividerBottom.toFloat(),
                paint
            )
        }

    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {

        outRect.top = dividerHeight

//        super.getItemOffsets(outRect, view, parent, state)

//        val position = parent.getChildAdapterPosition(view)
//        val count = state.itemCount
//        val offset = 195
//        val otheroffset = 20
//
//        if(position == 0) {
//            outRect.top = otheroffset
//            outRect.right = otheroffset
//            outRect.left = otheroffset
//            outRect.bottom = otheroffset
//        }
//        else {
//            outRect.top = offset
//            outRect.right = otheroffset
//            outRect.left = otheroffset
//            outRect.bottom = otheroffset
//        }
    }
}