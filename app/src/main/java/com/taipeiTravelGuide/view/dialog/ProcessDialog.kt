package com.taipeiTravelGuide.view.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import com.taipeiTravelGuide.databinding.DialogProcessBinding


class ProcessDialog(mActivity: Context) : Dialog(mActivity) {

    private lateinit var mViewBinding: DialogProcessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewBinding = DialogProcessBinding.inflate(LayoutInflater.from(context))
        setContentView(mViewBinding.root)
    }

}