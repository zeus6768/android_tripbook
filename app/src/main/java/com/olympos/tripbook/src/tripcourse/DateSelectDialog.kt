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
import java.lang.Integer.max

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

            listener!!.onDateOKClicked()

            dialog.dismiss()
        }

        btnCancel = dialog.findViewById(R.id.dialog_date_close_btn_iv)
        btnCancel.setOnClickListener {

            listener!!.onDateCancelClicked()

            dialog.dismiss()
        }

        dialog.show()
    }

    open fun show(title : String, message : String, okMessage : String, img : Int) {
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_base)
        dialog.setCancelable(false)

        tvTitle = dialog.findViewById(R.id.dialog_base_title_tv)
        tvTitle.text = title


        btnOK = dialog.findViewById(R.id.dialog_base_ok_btn_tv)
        btnOK.text = okMessage
        btnOK.setOnClickListener {

            listener!!.onDateOKClicked()

            dialog.dismiss()
        }

        btnCancel = dialog.findViewById(R.id.dialog_base_close_btn_iv)
        btnCancel.setOnClickListener {

            listener!!.onDateCancelClicked()

            dialog.dismiss()
        }

        dialog.show()
    }

    interface DialogClickListener {
        fun onDateOKClicked()
        fun onDateCancelClicked()
    }

    private fun initView() {
        Log.d("date", getArrivalYear(context).toString())
        year.maxValue = getArrivalYear(context)
        year.minValue = getDepartureYear(context)
        year.value = getDepartureYear(context)
        month.maxValue = maxOf(getDepartureMonth(context), getArrivalMonth(context))
        month.minValue = minOf(getDepartureMonth(context), getArrivalMonth(context))
        month.value = getDepartureMonth(context)
        day.maxValue = maxOf((getDepartureDay(context)), getArrivalDay(context))
        day.minValue = minOf((getDepartureDay(context)), getArrivalDay(context))
        day.value = getDepartureDay(context)

    }
}