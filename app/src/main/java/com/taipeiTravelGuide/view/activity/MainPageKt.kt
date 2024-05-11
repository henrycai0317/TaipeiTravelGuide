package com.taipeiTravelGuide.view.activity

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.taipeiTravelGuide.R
import com.taipeiTravelGuide.databinding.ActivityMainPageBinding
import com.taipeiTravelGuide.view.TaipeiTravelApplication

class MainPageKt : AppCompatActivity() {

    private var mBinding: ActivityMainPageBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val application = application as TaipeiTravelApplication


        //狀態欄更新
        supportActionBar?.hide()
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this@MainPageKt, R.color.cl26a889)

        mBinding = ActivityMainPageBinding.inflate(layoutInflater)
        setContentView(mBinding?.root)

    }
}