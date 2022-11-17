package com.olympos.tripbook.config

import android.view.View
import androidx.fragment.app.Fragment

open class BaseFragment: Fragment(), View.OnClickListener, BaseDialog.BaseDialogClickListener {

    override fun onClick(view: View?) {}
    override fun onOKClicked() {}
    override fun onCancelClicked() {}

}