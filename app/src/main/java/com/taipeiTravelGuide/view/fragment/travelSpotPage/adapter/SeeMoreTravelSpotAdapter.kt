package com.taipeiTravelGuide.view.fragment.travelSpotPage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.taipeiTravelGuide.StringUtils.checkString
import com.taipeiTravelGuide.databinding.ItemViewTravelSpotBinding
import com.taipeiTravelGuide.model.Attractions

class SeeMoreTravelSpotAdapter :
    PagingDataAdapter<Attractions, SeeMoreTravelSpotAdapter.SeeMoreTravelSpotViewHolder>(
        ATTRACTIONS_COMPARATOR
    ) {

    interface ItfTravelSpotItemClick {
        fun onTravelSpotItemClick(pItemData: Attractions)  //點擊景點Card Item
    }


    private var mItfTravelSpotItemClickCallBack: ItfTravelSpotItemClick? = null

    fun setOnClickItf(pItfTravelSpotClick: ItfTravelSpotItemClick) {
        mItfTravelSpotItemClickCallBack = pItfTravelSpotClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeeMoreTravelSpotViewHolder {
        val binding =
            ItemViewTravelSpotBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SeeMoreTravelSpotViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SeeMoreTravelSpotViewHolder, position: Int) {
        val event = getItem(position)
        if (event != null) {
            holder.bind(event)
        }
    }

    companion object {
        private val ATTRACTIONS_COMPARATOR = object : DiffUtil.ItemCallback<Attractions>() {
            override fun areItemsTheSame(oldItem: Attractions, newItem: Attractions): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Attractions, newItem: Attractions): Boolean =
                oldItem == newItem
        }
    }

    inner class SeeMoreTravelSpotViewHolder(private val binding: ItemViewTravelSpotBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var mAttractionsItemData: Attractions? = null

        init {
            binding.root.setOnClickListener {
                mAttractionsItemData?.let { iAttractionsData ->
                    mItfTravelSpotItemClickCallBack?.onTravelSpotItemClick(iAttractionsData)
                }
            }
        }

        fun bind(pItemData: Attractions) {
            mAttractionsItemData = pItemData
            binding.apply {
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

        }

    }
}

