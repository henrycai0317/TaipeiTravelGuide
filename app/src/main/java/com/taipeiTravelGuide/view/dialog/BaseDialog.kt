package com.taipeiTravelGuide.view.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle

open class BaseDialog(pContext: Context, pStyle:Int):
    Dialog(pContext, pStyle) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    interface OnDialogClickListener {

        fun onClick(dialog: BaseDialog) {

        }
    }

    interface  ItfDialogFinish {
        fun onGoto() {

        }

        fun onFinish() {

        }


    }

}