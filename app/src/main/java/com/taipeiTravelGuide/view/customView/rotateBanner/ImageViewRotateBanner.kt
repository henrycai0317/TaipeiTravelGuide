package com.taipeiTravelGuide.view.customView.rotateBanner

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.taipeiTravelGuide.utils.EAppUtil
import com.taipeiTravelGuide.R
import com.taipeiTravelGuide.databinding.ViewImageRotateBannerBinding
import com.taipeiTravelGuide.model.Attractions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 *  輪播 Banner View
 * */
class ImageViewRotateBanner(
    context: Context,
    attrs: AttributeSet? = null
) : RelativeLayout(context, attrs) {

    interface ItfRotateBannerCallBack {
        fun onCurrentIndexChange(pPairImageAndIndicatorIndex: Pair<Int, Int>)
    }

    private val mBinding: ViewImageRotateBannerBinding =
        ViewImageRotateBannerBinding.inflate(LayoutInflater.from(context), this, true)
    private var mIsUserDragging = false
    private val mFixSec = 3000L
    private var mAdList = ArrayList<Attractions.Image>()
    private var mAdAdapter: ImageViewRotateAdapter? = null
    private var mRotateJob: Job? = null
    private var mOriginalRotateSize = ArrayList<Attractions.Image>() //原始未加入末、首圖片
    private var mCurrentIndex = 1   //Banner輪播順序控制
    private var mCurrentIndicator = 0 //Banner Indicator Flag
    private var mItfRotateBannerCallBack: ItfRotateBannerCallBack? = null  //Index 回調

    private val mAdOnPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {

        override fun onPageScrollStateChanged(state: Int) {
            super.onPageScrollStateChanged(state)
            //停止滑動 0
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                if (mCurrentIndex == 0) {
                    mBinding.vpAd.setCurrentItem(mAdList.size - 2, false)
                } else if (mCurrentIndex == mAdList.size - 1) {
                    mBinding.vpAd.setCurrentItem(1, false)
                }
            }

            //開始滑動 1
            if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                mRotateJob?.cancel()
                mIsUserDragging = true

            }
            if (state == ViewPager.SCROLL_STATE_SETTLING) {
                if (mIsUserDragging) {
                    startAutoMoving()
                    mIsUserDragging = false
                }
            }
        }

        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            mCurrentIndex = position
            val showIndicatorPosition = when (position) {
                mAdList.size - 1 -> 0
                0 -> mAdList.size - 1
                else -> position - 1
            }
            Log.d(
                "onPageSelected",
                "onPageSelected: $mCurrentIndex vpPosition:${mBinding.vpAd.currentItem} showIndicatorPosition : $showIndicatorPosition mCurrentIndicator: $mCurrentIndicator"
            )
            if (mCurrentIndicator != showIndicatorPosition) {
                setIndicator(showIndicatorPosition)
                mCurrentIndicator = showIndicatorPosition
            }
            mItfRotateBannerCallBack?.onCurrentIndexChange(
                Pair(
                    mCurrentIndex,
                    showIndicatorPosition
                )
            )
        }
    }

    fun setOnRotateChangeListener(pItfRotateCallBack: ItfRotateBannerCallBack) {
        mItfRotateBannerCallBack = pItfRotateCallBack
    }

    /**
     *  有給AD資料，才開始輪播
     *  @param pAdList 輪播Data
     *  @param pDefaultImagePosition 輪播初始化顯示圖位置
     *  @param pDefaultIndicatorPosition 輪播初始化顯示Indicator位置
     * */
    fun showRotateBanner(
        pAdList: ArrayList<Attractions.Image>,
        pDefaultImagePosition: Int,
        pDefaultIndicatorPosition: Int,
    ) {
        if (pAdList.isNotEmpty()) {

            if (pAdList.size <= 1) {
                return
            }

            mAdList.clear()
            mOriginalRotateSize.clear()
            mAdList.addAll(pAdList)
            mOriginalRotateSize.addAll(pAdList)

            // 7,1,2,3,4,5,6,7,1 實現無限循環概念
            mAdList.add(0, pAdList[pAdList.size - 1])//先加入最後一項  -> 7
            mAdList.add(pAdList[0])  //最後補上第一項 -> 1

            //暫時解法，解決Indicator初始化未顯示畫面上，滑動才顯示
            CoroutineScope(Dispatchers.Main).launch {
                delay(100)
                setIndicator(pDefaultIndicatorPosition)
            }

            mAdAdapter = ImageViewRotateAdapter(mAdList)
            mBinding.vpAd.adapter = mAdAdapter
            mBinding.vpAd.registerOnPageChangeCallback(mAdOnPageChangeCallback)
            mBinding.vpAd.setCurrentItem(pDefaultImagePosition, false)
            // 開始協程，定時滑動 ViewPager
            startAutoMoving()
        }
    }

    private fun startAutoMoving() {
        mRotateJob = CoroutineScope(Dispatchers.Main).launch {
            while (true) {
                delay(mFixSec)
                rotateBanner()
            }
        }
    }

    private fun rotateBanner() {
        var iCurrentRotate = mBinding.vpAd.currentItem
        iCurrentRotate++
        mBinding.vpAd.currentItem = iCurrentRotate
    }

    /**
     * Dot 顯示控制
     **/
    @Synchronized
    private fun setIndicator(pPosition: Int) {
        mBinding.llAdIndicator.removeAllViews()
        if (mOriginalRotateSize.isEmpty()) {
            return
        }

        for (i in 0 until mOriginalRotateSize.size) {
            val ivIndicator = ImageView(context)
            ivIndicator.setPadding(5, 0, 5, 0)
            ivIndicator.setImageDrawable(
                EAppUtil.getDrawable(
                    context, if (i == pPosition) {
                        R.drawable.on_dog_foot_print
                    } else {
                        R.drawable.off_dog_foot_print
                    }
                )
            )
            mBinding.llAdIndicator.addView(ivIndicator)
        }

    }

    // 在 View 銷毀時取消協程
    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mRotateJob?.cancel()
        mRotateJob = null
    }
}