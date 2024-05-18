package com.taipeiTravelGuide.view.fragment.homePage.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.taipeiTravelGuide.StringUtils.checkString
import com.taipeiTravelGuide.ViewUtils.setViewGone
import com.taipeiTravelGuide.ViewUtils.setViewVisible
import com.taipeiTravelGuide.connect.response.AttractionsResponse
import com.taipeiTravelGuide.connect.response.EventsResponse
import com.taipeiTravelGuide.databinding.ItemHomePageHotNewsBinding
import com.taipeiTravelGuide.databinding.ItemHomePageTravelSpotBinding
import com.taipeiTravelGuide.databinding.ItemViewHotNewsBinding
import com.taipeiTravelGuide.databinding.ItemViewTravelSpotBinding
import com.taipeiTravelGuide.model.Attractions
import com.taipeiTravelGuide.model.EventsData
import retrofit2.Response

/***
 * 首頁 Adapter
 */

class HomePageAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface ItfHomePageAdapterClick {
        fun onHotNewsItemClick(pWebViewUrl: String)  //點擊消息Card Item
        fun onTravelSpotItemClick(pItemData: Attractions)  //遊憩景點Card Item

        fun onSeeMoreHotNews()
        fun onSeeMoreTravelSpot()
    }

    private val FIRST_HOT_NEWS = 0
    private val SECOND_TRAVEL_SPOT = 1

    private val mTypeArray = arrayListOf<Int>()
    private val mSize get() = mTypeArray.size

    //最新消息Api
    private var mResEvents: Response<EventsResponse>? = null

    //遊憩景點 API
    private var mResAttractions: Response<AttractionsResponse>? = null

    private var mItfItfHomePageViewClick: ItfHomePageAdapterClick? = null //各HolderView 點擊事件callBack


    init {
        mTypeArray.add(FIRST_HOT_NEWS)
        mTypeArray.add(SECOND_TRAVEL_SPOT)
    }

    fun setOnClickItf(pItfHomePage: ItfHomePageAdapterClick) {
        mItfItfHomePageViewClick = pItfHomePage
    }

    fun setResEventsData(pResEvents: Response<EventsResponse>?) {
        mResEvents = pResEvents
        notifyItemChanged(0)
    }

    fun setResAttractionsData(pResAttractions: Response<AttractionsResponse>?) {
        mResAttractions = pResAttractions
        notifyItemChanged(1)
    }

    /**
     * 清除狀態 Show Shimmer 使用
     * */
    @SuppressLint("NotifyDataSetChanged")
    fun setApiDataClearToShowShimmer() {
        mResEvents = null
        mResAttractions = null
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == FIRST_HOT_NEWS) {
            ItemHotNewsView(ItemHomePageHotNewsBinding.inflate(LayoutInflater.from(parent.context)))
        } else {
            ItemTravelSpotView(ItemHomePageTravelSpotBinding.inflate(LayoutInflater.from(parent.context)))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ItemHotNewsView -> {
                holder.checkingApi(mResEvents)
            }

            is ItemTravelSpotView -> {
                holder.checkingApi(mResAttractions)
            }
        }
    }

    override fun getItemCount(): Int {
        return mTypeArray.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < mSize) {
            mTypeArray[position]
        } else {
            position
        }
    }


    /**
     * 顯示 最近消息 固定三筆資料
     */
    inner class ItemHotNewsView(private val mBinding: ItemHomePageHotNewsBinding) :
        RecyclerView.ViewHolder(mBinding.root) {

        init {
            mBinding.tvSeeMoreHotNewsTitle.setOnClickListener {
                mItfItfHomePageViewClick?.onSeeMoreHotNews()
            }
        }

        @Synchronized
        fun checkingApi(pResEvents: Response<EventsResponse>?) {
            if (pResEvents != null) {
                if (pResEvents.isSuccessful) {
                    setData(pResEvents)
                } else {
                    showApiError()
                }
            } else {
                showShimmer()
            }
        }

        private fun setData(pResEvents: Response<EventsResponse>) {
            defaultStatus()
            mBinding.apply {
                tvSeeMoreHotNewsTitle.setViewVisible()
                llHotNewsContainer.setViewVisible()
                llHotNewsContainer.removeAllViews()
                pResEvents.body()?.data?.let { iData ->
                    val iEventDataList = if (iData.size >= 3) {
                        iData.subList(0, 3)
                    } else {
                        iData.subList(0, iData.size)
                    }
                    if (iData.isEmpty()) {
                        showNoData()
                    } else {
                        iEventDataList.forEach { itemData ->
                            val iView = createItemView(itemData)
                            iView.setOnClickListener {
                                mItfItfHomePageViewClick?.onHotNewsItemClick(itemData.url)
                            }
                            llHotNewsContainer.addView(iView)
                        }
                    }
                }
            }
        }

        private fun createItemView(pItemData: EventsData): View {
            val itemViewBinding =
                ItemViewHotNewsBinding.inflate(LayoutInflater.from(mBinding.root.context))
            itemViewBinding.apply {
                tvHotNewsTitle.text = pItemData.title.checkString()
                tvHotNewsContent.text = pItemData.description.checkString()
            }
            return itemViewBinding.root
        }

        private fun showApiError() {
            defaultStatus()
            mBinding.icHotNewsApiError.root.setViewVisible()
        }

        private fun showNoData() {
            defaultStatus()
            mBinding.icNoData.root.setViewVisible()
        }

        private fun showShimmer() {
            defaultStatus()
            mBinding.icHotNewsShimmer.root.setViewVisible()
        }

        private fun defaultStatus() {
            mBinding.apply {
                tvSeeMoreHotNewsTitle.setViewGone()
                icHotNewsShimmer.root.setViewGone()
                icHotNewsApiError.root.setViewGone()
                icNoData.root.setViewGone()
                llHotNewsContainer.setViewGone()
            }
        }
    }

    /**
     * 顯示 遊憩景點 固定三筆資料
     */
    inner class ItemTravelSpotView(private val mBinding: ItemHomePageTravelSpotBinding) :
        RecyclerView.ViewHolder(mBinding.root) {

        init {
            mBinding.tvSeeMoreTravelSpotTitle.setOnClickListener {
                mItfItfHomePageViewClick?.onSeeMoreTravelSpot()
            }
        }

        @Synchronized
        fun checkingApi(pResAttractions: Response<AttractionsResponse>?) {
            if (pResAttractions != null) {
                if (pResAttractions.isSuccessful) {
                    setData(pResAttractions)
                } else {
                    showApiError()
                }
            } else {
                showShimmer()
            }

        }

        private fun setData(pResAttractions: Response<AttractionsResponse>) {
            defaultStatus()
            mBinding.apply {
                tvSeeMoreTravelSpotTitle.setViewVisible()
                llTravelSpotContainer.setViewVisible()
                llTravelSpotContainer.removeAllViews()
                pResAttractions.body()?.data?.let { iData ->
                    val iAttractionsDataList = if (iData.size >= 3) {
                        iData.subList(0, 3)
                    } else {
                        iData.subList(0, iData.size)
                    }
                    if (iData.isEmpty()) {
                        showNoData()
                    } else {
                        iAttractionsDataList.forEach { itemData ->
                            val iView = createItemView(itemData)
                            iView.setOnClickListener {
                                mItfItfHomePageViewClick?.onTravelSpotItemClick(itemData)
                            }
                            llTravelSpotContainer.addView(iView)
                        }
                    }
                }
            }
        }

        private fun createItemView(pItemData: Attractions): View {
            val itemViewBinding =
                ItemViewTravelSpotBinding.inflate(LayoutInflater.from(mBinding.root.context))
            itemViewBinding.apply {
                tvTravelSpotTitle.text = pItemData.name.checkString()
                tvTravelSpotContent.text = pItemData.introduction.checkString()
                val iListImages = pItemData.images
                if (iListImages.isNotEmpty()) {
                    val iMageUrl = iListImages[0].src.checkString()
                    Glide.with(ivTravelSpotImage.context)
                        .load(iMageUrl)
                        .into(ivTravelSpotImage)
                }
            }
            return itemViewBinding.root
        }

        private fun showApiError() {
            defaultStatus()
            mBinding.icTravelSpotApiError.root.setViewVisible()
        }

        private fun showNoData() {
            defaultStatus()
            mBinding.icNoData.root.setViewVisible()
        }

        private fun showShimmer() {
            defaultStatus()
            mBinding.icTravelSpotShimmer.root.setViewVisible()
        }

        private fun defaultStatus() {
            mBinding.apply {
                icTravelSpotShimmer.root.setViewGone()
                icNoData.root.setViewGone()
                llTravelSpotContainer.setViewGone()
                tvSeeMoreTravelSpotTitle.setViewGone()
            }
        }
    }


}