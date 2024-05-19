package com.taipeiTravelGuide.view.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.taipeiTravelGuide.R
import com.taipeiTravelGuide.databinding.DialogSingleImageViewBinding

class SingleImageViewDialog(
    private val mContext: Context,
    private val mImageUrl: String
) : BaseDialog(mContext, R.style.dialogNobackground) {

    private var mViewBinding: DialogSingleImageViewBinding = DialogSingleImageViewBinding.inflate(
        LayoutInflater.from(context)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)
        //讓Dialog畫面占滿整個手機螢幕
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        initView()
        initListener()
    }

    private fun initListener() {
        mViewBinding.apply {
            clClosImage.setOnClickListener {
                this@SingleImageViewDialog.cancel()
            }
        }
    }

    private fun initView() {
        mViewBinding.apply {
            Glide.with(mContext).load(mImageUrl)
                .placeholder(R.drawable.ic_launcher)
                .into(mViewBinding.ivImageView)
        }
    }
}