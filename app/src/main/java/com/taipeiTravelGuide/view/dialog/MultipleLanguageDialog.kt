package com.taipeiTravelGuide.view.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.taipeiTravelGuide.R
import com.taipeiTravelGuide.databinding.DialogMultipleLanguageBinding

class MultipleLanguageDialog(private val mContext: Context) :
    BaseDialog(mContext, R.style.dialogNobackground) {

    private var mViewBinding: DialogMultipleLanguageBinding = DialogMultipleLanguageBinding.inflate(
        LayoutInflater.from(context)
    )

    private fun initListener() {
        mViewBinding.apply {

            llGapA.setOnClickListener {
                this@MultipleLanguageDialog.cancel()
            }

            llFrame.setOnClickListener {
                //內容框click不可以關閉
            }

            llGapB.setOnClickListener {
                this@MultipleLanguageDialog.cancel()
            }

            ivClose.setOnClickListener {
                this@MultipleLanguageDialog.cancel()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)

        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        window?.attributes?.windowAnimations = R.style.BottomFade
        initView()
        initListener()
    }


    private fun initView() {
        mViewBinding.apply {
            radioGroup.setOnCheckedChangeListener { _, id ->
                val languageType = when (id) {
                    R.id.rbTraditionalCh -> "正體中文zh-tw"
                    R.id.rbSimpleCh -> "簡體中文zh-cn"
                    R.id.rbEnglish -> "英文en"
                    R.id.rbJapanese -> "日文ja"
                    R.id.rbKorea -> "韓文ko"
                    R.id.rbSpanish -> "西班牙文es"
                    R.id.rbIndonesian -> "印尼文id"
                    R.id.rbThai -> "泰文th"
                    R.id.rbVietnamese -> "越南文vi"
                    else -> "正體中文zh-tw"
                }
                Toast.makeText(mContext, "您選了 $languageType", Toast.LENGTH_SHORT).show()
            }
        }
    }
}