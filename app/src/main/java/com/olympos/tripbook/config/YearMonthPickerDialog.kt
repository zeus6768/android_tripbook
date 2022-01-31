package com.olympos.tripbook.config
//
//import android.app.AlertDialog
//
//import android.widget.NumberPicker
//
//import android.view.LayoutInflater
//
//import android.os.Bundle
//
//import android.app.DatePickerDialog.OnDateSetListener
//import android.app.Dialog
//import android.content.DialogInterface
//import android.view.View
//import android.widget.Button
//import androidx.fragment.app.DialogFragment
//import com.olympos.tripbook.R
//import java.util.*
//
//
//class YearMonthPickerDialog : DialogFragment() {
//    private var listener: OnDateSetListener? = null
//    var cal: Calendar = Calendar.getInstance()
//    fun setListener(listener: OnDateSetListener?) {
//        this.listener = listener
//    }
//
//    var btnConfirm: Button? = null
//    var btnCancel: Button? = null
//    fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        val builder: AlertDialog.Builder = Builder(getActivity())
//        val inflater: LayoutInflater = getActivity().getLayoutInflater()
//        val dialog: View = inflater.inflate(R.layout.year_month_picker, null)
//        btnConfirm = dialog.findViewById(R.id.btn_confirm)
//        btnCancel = dialog.findViewById(R.id.btn_cancel)
//        val monthPicker = dialog.findViewById(R.id.picker_month) as NumberPicker
//        val yearPicker = dialog.findViewById(R.id.picker_year) as NumberPicker
//        btnCancel.setOnClickListener(object : DialogInterface.OnClickListener() {
//            fun onClick(view: View?) {
//                this@YearMonthPickerDialog.getDialog().cancel()
//            }
//        })
//        btnConfirm.setOnClickListener(object : View.OnClickListener() {
//            fun onClick(view: View?) {
//                listener!!.onDateSet(null, yearPicker.value, monthPicker.value, 0)
//                this@YearMonthPickerDialog.getDialog().cancel()
//            }
//        })
//        monthPicker.minValue = 1
//        monthPicker.maxValue = 12
//        monthPicker.value = cal.get(Calendar.MONTH) + 1
//        val year: Int = cal.get(Calendar.YEAR)
//        yearPicker.minValue = MIN_YEAR
//        yearPicker.maxValue = MAX_YEAR
//        yearPicker.value = year
//        builder.setView(dialog) // Add action buttons
//        /*
//        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int id) {
//                listener.onDateSet(null, yearPicker.getValue(), monthPicker.getValue(), 0);
//            }
//        })
//        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id) {
//                MyYearMonthPickerDialog.this.getDialog().cancel();
//            }
//        })
//        */
//        return builder.create()
//    }
//
//    companion object {
//        private const val MAX_YEAR = 2099
//        private const val MIN_YEAR = 1980
//    }
//
//
//
//}