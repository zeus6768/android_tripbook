package com.olympos.tripbook.src.tripcourse

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.Window
import android.widget.ImageView
import android.widget.NumberPicker
import android.widget.TextView
import com.olympos.tripbook.R
import com.olympos.tripbook.utils.*

open class DateSelectDialog(val context: Context) {
    val dialog = Dialog(context)
    protected lateinit var tvTitle : TextView
    protected lateinit var year : NumberPicker
    protected lateinit var month : NumberPicker
    protected lateinit var day : NumberPicker
    protected lateinit var btnOK : TextView
    protected lateinit var btnCancel : ImageView

    var listener : DialogClickListener? = null

    open fun show(title : String, okMessage : String) {
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_date)
        dialog.setCancelable(false)

        tvTitle = dialog.findViewById(R.id.dialog_date_title_tv)
        tvTitle.text = title

        year = dialog.findViewById(R.id.dialog_date_picker_year)
        month = dialog.findViewById(R.id.dialog_date_picker_month)
        day = dialog.findViewById(R.id.dialog_date_picker_day)

        initView()

        btnOK = dialog.findViewById(R.id.dialog_date_ok_btn_tv)
        btnOK.text = okMessage
        btnOK.setOnClickListener {
            val selectedYear = year.value
            val selectedMonth = month.value
            val selectedDay = day.value

            listener!!.onDateOKClicked(selectedYear, selectedMonth, selectedDay)

            dialog.dismiss()
        }

        btnCancel = dialog.findViewById(R.id.dialog_date_close_btn_iv)
        btnCancel.setOnClickListener {

            listener!!.onDateCancelClicked()

            dialog.dismiss()
        }

        dialog.show()
    }

    interface DialogClickListener {
        fun onDateOKClicked(selectedYear: Int, selectedMonth: Int, selectedDay: Int)
        fun onDateCancelClicked()
    }

    private fun initView() {
        Log.d("date", getArrivalYear().toString())
        year.maxValue = getArrivalYear()
        year.minValue = getDepartureYear()
        year.value = getDepartureYear()
        month.maxValue = maxOf(getDepartureMonth(), getArrivalMonth())
        month.minValue = minOf(getDepartureMonth(), getArrivalMonth())
        month.value = getDepartureMonth()
        day.maxValue = maxOf((getDepartureDay()), getArrivalDay())
        day.minValue = minOf((getDepartureDay()), getArrivalDay())
        day.value = getDepartureDay()

    }
}