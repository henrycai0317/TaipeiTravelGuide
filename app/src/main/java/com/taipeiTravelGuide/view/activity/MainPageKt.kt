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
        //狀態欄更新
        supportActionBar?.hide()
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)


        val isDarkModeEnabled = TaipeiTravelApplication.isDarkModeEnabled
        // 根據全局主題模式設置主題
        if (isDarkModeEnabled) {
            setTheme(R.style.AppTheme_Dark)
            window.statusBarColor = ContextCompat.getColor(this@MainPageKt, R.color.cl005B4F)
        } else {
            setTheme(R.style.AppTheme)
            window.statusBarColor = ContextCompat.getColor(this@MainPageKt, R.color.cl26a889)
        }

        mBinding = ActivityMainPageBinding.inflate(layoutInflater)
        setContentView(mBinding?.root)

    }
}