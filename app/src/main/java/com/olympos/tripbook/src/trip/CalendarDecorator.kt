package com.olympos.tripbook.src.trip

//import android.app.PendingIntent.getActivity
//import android.content.Context
//import android.graphics.drawable.Drawable
//import androidx.core.content.ContextCompat
//import com.olympos.tripbook.R
//import com.prolificinteractive.materialcalendarview.CalendarDay
//import com.prolificinteractive.materialcalendarview.DayViewDecorator
//import com.prolificinteractive.materialcalendarview.DayViewFacade
//import java.util.HashSet
//
//class CalendarDecorator(context: Context) : DayViewDecorator {
//    private val list = HashSet<CalendarDay>()
//    private val drawable: Drawable = ContextCompat.getDrawable(getActivity(), R.drawable.selec);
//    override fun shouldDecorate(day: CalendarDay): Boolean {
//        return list.contains(day)
//    }
//
//    override fun decorate(view: DayViewFacade) {
//        view.setSelectionDrawable(drawable)
//    }
//
//    /**
//     * We're changing the dates, so make sure to call [MaterialCalendarView.invalidateDecorators]
//     */
//    fun addFirstAndLast(first: CalendarDay, last: CalendarDay) {
//        list.clear()
//        list.add(first)
//        list.add(last)
//    }
//
//}