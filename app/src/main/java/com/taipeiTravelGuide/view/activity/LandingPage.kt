package com.taipeiTravelGuide.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.taipeiTravelGuide.R
import com.taipeiTravelGuide.databinding.ActivityLandingPageBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * App 開啟畫面使用
 * */
class LandingPage : AppCompatActivity() {

    private var mBinding: ActivityLandingPageBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //狀態欄更新
        supportActionBar?.hide()
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this@LandingPage, R.color.cl011329)

        mBinding = ActivityLandingPageBinding.inflate(layoutInflater)
        setContentView(mBinding?.root)
        initViews()

    }

    private fun initViews() {
        mBinding?.apply {
            //使用Glide加仔本地GIF圖
            Glide.with(this@LandingPage)
                .load(R.drawable.travel_doggy)
                .into(gifImageView)
        }
    }


    override fun onResume() {
        super.onResume()
        CoroutineScope(Dispatchers.Main).launch {
            delay(6000)
            //開啟首頁
            startActivity(Intent(this@LandingPage, MainPageKt::class.java))
            finish()
        }
    }
}