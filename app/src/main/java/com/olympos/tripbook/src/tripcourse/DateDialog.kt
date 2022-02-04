package com.olympos.tripbook.src.tripcourse

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import com.olympos.tripbook.R

open class DateDialog(context: Context) {
    val dialog = Dialog(context)
    protected lateinit var tvTitle : TextView
    protected lateinit var tvMessage : TextView
    protected lateinit var btnOK : TextView
    protected lateinit var btnCancel : ImageView

    var listener : BaseDialogClickListener? = null

    open fun show(title : String, message : String, okMessage : String) {
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_base)
        dialog.setCancelable(false)

        tvTitle = dialog.findViewById(R.id.dialog_base_title_tv)
        tvTitle.text = title

        tvMessage = dialog.findViewById(R.id.dialog_base_message_tv)
        tvMessage.text = message

        btnOK = dialog.findViewById(R.id.dialog_base_ok_btn_tv)
        btnOK.text = okMessage
        btnOK.setOnClickListener {

            listener!!.onOKClicked()

            dialog.dismiss()
        }

        btnCancel = dialog.findViewById(R.id.dialog_base_close_btn_iv)
        btnCancel.setOnClickListener {

            listener!!.onCancelClicked()

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

        tvMessage = dialog.findViewById(R.id.dialog_base_message_tv)
        tvMessage.text = message
        tvMessage.setCompoundDrawablesRelativeWithIntrinsicBounds(0,img,0,0)

        btnOK = dialog.findViewById(R.id.dialog_base_ok_btn_tv)
        btnOK.text = okMessage
        btnOK.setOnClickListener {

            listener!!.onOKClicked()

            dialog.dismiss()
        }

        btnCancel = dialog.findViewById(R.id.dialog_base_close_btn_iv)
        btnCancel.setOnClickListener {

            listener!!.onCancelClicked()

            dialog.dismiss()
        }

        dialog.show()
    }

    interface BaseDialogClickListener {
        fun onOKClicked()
        fun onCancelClicked()
    }
}