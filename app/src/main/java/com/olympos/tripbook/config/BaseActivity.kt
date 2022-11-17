package com.olympos.tripbook.config

import android.view.View
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity: AppCompatActivity(), View.OnClickListener, BaseDialog.BaseDialogClickListener {

    override fun onClick(view: View?) {}
    override fun onOKClicked() {}
    override fun onCancelClicked() {}

    fun showDialog(title: String, message: String , okMessage: String) {
        val dig = BaseDialog(this)
        dig.listener = this
        dig.show(title, message, okMessage)
    }

    fun showImgDialog(title: String, message: String , okMessage: String, img : Int) {
        val dig = BaseDialog(this)
        dig.listener = this
        dig.show(title, message, okMessage, img)
    }

}