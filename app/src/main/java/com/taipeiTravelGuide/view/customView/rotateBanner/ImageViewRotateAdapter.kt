package com.taipeiTravelGuide.view.customView.rotateBanner

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.taipeiTravelGuide.R
import com.taipeiTravelGuide.StringUtils.checkString
import com.taipeiTravelGuide.databinding.ItemAdImageListBinding
import com.taipeiTravelGuide.model.Attractions
import com.taipeiTravelGuide.view.dialog.SingleImageViewDialog

class ImageViewRotateAdapter(
    private val mAdList: ArrayList<Attractions.Image>
) : RecyclerView.Adapter<ImageViewRotateAdapter.IvRotateAdapterViewHolder>() {
    private var mContext: Context? = null

    inner class IvRotateAdapterViewHolder(private val mBinding: ItemAdImageListBinding) :
        RecyclerView.ViewHolder(mBinding.root) {
        fun setData(pItemData: Attractions.Image) {
            mContext?.let { iContext ->  //參考: https://blog.csdn.net/NewActivity/article/details/122704527
                Glide.with(iContext).load(pItemData.src)
                    .transform(RoundedCorners(20))  //切圓角
                    .placeholder(R.drawable.ic_launcher).into(mBinding.ivItemImage)
            }
            setOnClickListener(mBinding.ivItemImage, pItemData)
        }
    }

    private fun setOnClickListener(ivItemImage: ImageView, pItemData: Attractions.Image) {
        ivItemImage.setOnClickListener {
            mContext?.let { iContext ->
                SingleImageViewDialog(iContext, pItemData.src.checkString()).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IvRotateAdapterViewHolder {
        mContext = parent.context
        return IvRotateAdapterViewHolder(
            ItemAdImageListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return mAdList.size
    }

    override fun onBindViewHolder(holder: IvRotateAdapterViewHolder, position: Int) {
        holder.setData(mAdList[position])
    }

}