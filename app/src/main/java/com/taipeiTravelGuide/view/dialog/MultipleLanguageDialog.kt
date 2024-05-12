package com.taipeiTravelGuide.view.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import com.taipeiTravelGuide.R
import com.taipeiTravelGuide.databinding.DialogMultipleLanguageBinding

class MultipleLanguageDialog(
    private val mContext: Context,
    private var mSelectedId: Int? = null,
    private var mItfDialogFinish: ItfDialogFinish
) : BaseDialog(mContext, R.style.dialogNobackground) {

    private var mSelectedLanguageType: String = "" //給API用的Type
    private var mViewBinding: DialogMultipleLanguageBinding = DialogMultipleLanguageBinding.inflate(
        LayoutInflater.from(context)
    )

    fun setMultipleLanguageId(pItemId: Int) {
        mSelectedId = pItemId
        findViewById<RadioButton>(pItemId)?.isChecked = true
    }

    private fun initListener() {
        mViewBinding.apply {

            llGapA.setOnClickListener {
                mItfDialogFinish.onFinish(mSelectedLanguageType)
                mSelectedId?.let { itemId -> mItfDialogFinish.onFinish(itemId) }
                this@MultipleLanguageDialog.cancel()
            }

            llFrame.setOnClickListener {
                //內容框click不可以關閉
            }

            llGapB.setOnClickListener {
                mItfDialogFinish.onFinish(mSelectedLanguageType)
                mSelectedId?.let { itemId -> mItfDialogFinish.onFinish(itemId) }
                this@MultipleLanguageDialog.cancel()
            }

            ivClose.setOnClickListener {
                mItfDialogFinish.onFinish(mSelectedLanguageType)
                mSelectedId?.let { itemId -> mItfDialogFinish.onFinish(itemId) }
                this@MultipleLanguageDialog.cancel()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)

        //RadioButton 初始預設
        mSelectedId?.let { iSelectedId ->
            findViewById<RadioButton>(iSelectedId)?.isChecked = true
        } ?: kotlin.run {
            mViewBinding.rbTraditionalCh.isChecked = true
            mSelectedLanguageType = "zh-tw"
        }

        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        window?.attributes?.windowAnimations = R.style.BottomFade
        initView()
        initListener()
    }


    private fun initView() {
        mViewBinding.apply {
            radioGroup.setOnCheckedChangeListener { _, id ->
                mSelectedId = id
                val languageType = when (id) {
                    R.id.rbTraditionalCh -> {
                        mSelectedLanguageType = "zh-tw"
                        "正體中文zh-tw"
                    }

                    R.id.rbSimpleCh -> {
                        mSelectedLanguageType = "zh-cn"
                        "簡體中文zh-cn"
                    }

                    R.id.rbEnglish -> {
                        mSelectedLanguageType = "en"
                        "英文en"
                    }

                    R.id.rbJapanese -> {
                        mSelectedLanguageType = "ja"
                        "日文ja"
                    }

                    R.id.rbKorea -> {
                        mSelectedLanguageType = "ko"
                        "韓文ko"
                    }

                    R.id.rbSpanish -> {
                        mSelectedLanguageType = "es"
                        "西班牙文es"
                    }

                    R.id.rbIndonesian -> {
                        mSelectedLanguageType = "id"
                        "印尼文id"
                    }

                    R.id.rbThai -> {
                        mSelectedLanguageType = "th"
                        "泰文th"
                    }

                    R.id.rbVietnamese -> {
                        mSelectedLanguageType = "vi"
                        "越南文vi"
                    }

                    else -> {
                        mSelectedLanguageType = "zh-tw"
                        "正體中文zh-tw"
                    }
                }
                Toast.makeText(mContext, "您選了 $languageType", Toast.LENGTH_SHORT).show()
            }
        }
    }
}