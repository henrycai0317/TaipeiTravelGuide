package com.taipeiTravelGuide.view.fragment.travelSpotPage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.taipeiTravelGuide.ClassTrans
import com.taipeiTravelGuide.EAppUtil
import com.taipeiTravelGuide.EAppUtil.serializable
import com.taipeiTravelGuide.R
import com.taipeiTravelGuide.StringUtils.checkString
import com.taipeiTravelGuide.ViewUtils.setViewGone
import com.taipeiTravelGuide.ViewUtils.setViewVisible
import com.taipeiTravelGuide.ViewUtils.setViewVisibleOrGone
import com.taipeiTravelGuide.databinding.FragmentTravelSpotBinding
import com.taipeiTravelGuide.model.Attractions
import com.taipeiTravelGuide.view.customView.rotateBanner.ImageViewRotateBanner

/**
 * 遊憩景點 - 內頁
 * */
class TravelSpotFragment : Fragment() {
    private var mBinding: FragmentTravelSpotBinding? = null
    private val mTravelSpotViewModel: TravelSpotViewModel by lazy {
        ViewModelProvider(this@TravelSpotFragment)[TravelSpotViewModel::class.java]
    }

    companion object {
        private const val EXTRA_ATTRACTIONS_DATA = "EXTRA_ATTRACTIONS_DATA"

        fun newBundle(pData: Attractions) {
            ClassTrans.setData(EXTRA_ATTRACTIONS_DATA, pData)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentTravelSpotBinding.inflate(inflater, container, false)
        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initListener()
    }

    private fun initViews() {
        setAttractionsData()  //設定Attractions API Data
    }

    private fun setAttractionsData() {
        val iAttractionsTampData: Attractions? = ClassTrans.getData(EXTRA_ATTRACTIONS_DATA)
        val iAttractionsData = if (mTravelSpotViewModel.getAttractionsData() != null) {
            mTravelSpotViewModel.getAttractionsData()
        } else {
            mTravelSpotViewModel.setSaveAttractionsData(iAttractionsTampData)
            iAttractionsTampData
        }

        if (iAttractionsData != null) {
            showData(iAttractionsData)
        }
    }

    private fun showData(iAttractionsData: Attractions) {
        mBinding?.apply {
            iAttractionsData.images.let { iImageList ->
                /**輪播初始化*/
                val iCurrentRotatePair = mTravelSpotViewModel.getCurrentImageRotateIndex()
                ivRotateBanner.showRotateBanner(
                    iImageList,
                    iCurrentRotatePair?.first ?: 1,
                    iCurrentRotatePair?.second ?: 0,
                )
            }

            iAttractionsData.apply {
                tvHeaderTitle.text = name.ifEmpty { R.string.travel_spot.toString() }
                /**營業時間*/
                clOpenTime.setViewVisibleOrGone(open_time.isNotEmpty())
                tvOpenTime.text = open_time.checkString()

                /**地址*/
                clAddress.setViewVisibleOrGone(address.isNotEmpty())
                tvAddress.text = address.checkString()

                /**連絡電話*/
                clPhoneCall.setViewVisibleOrGone(tel.isNotEmpty())
                tvPhoneNumber.text = tel.checkString()

                /**網址*/
                clInternet.setViewVisibleOrGone(official_site.isNotEmpty())
                tvInternetUrl.text = official_site.checkString()

                /**文章內容*/
                clContentTxt.setViewVisibleOrGone(introduction.isNotEmpty())
                tvContentTxt.text = introduction.checkString()

            }
        }
    }

    private fun initListener() {
        mBinding?.apply {
            /**網路點擊*/
            clInternet.setOnClickListener {
                findNavController().navigate(
                    R.id.action_TravelSpotFragment_to_travelSpotWebViewFragment,
                    TravelSpotWebViewFragment.newBundle(
                        mTravelSpotViewModel.getAttractionsData()?.official_site.checkString(),
                        mTravelSpotViewModel.getAttractionsData()?.name.checkString(),
                    )
                )
            }

            /**打電話*/
            clPhoneCall.setOnClickListener {
                activity?.let { iAct ->
                    EAppUtil.callTel(
                        iAct,
                        mTravelSpotViewModel.getAttractionsData()?.tel.checkString()
                    )
                }
            }

            /**輪播位置監聽*/
            ivRotateBanner.setOnRotateChangeListener(object :
                ImageViewRotateBanner.ItfRotateBannerCallBack {
                override fun onCurrentIndexChange(pPairImageAndIndicatorIndex: Pair<Int, Int>) {
                    mTravelSpotViewModel.setCurrentPairRotateIndex(pPairImageAndIndicatorIndex)
                }
            })

            /**返回鍵*/
            ivBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

}

class TravelSpotViewModel : ViewModel() {
    private var mCurrentPairRotateIndex: Pair<Int, Int>? = null
    private var mAttractionsData: Attractions? = null

    fun setSaveAttractionsData(pData: Attractions?) {
        mAttractionsData = pData
    }

    fun setCurrentPairRotateIndex(pPairIndex: Pair<Int, Int>) {
        mCurrentPairRotateIndex = pPairIndex
    }

    fun getCurrentImageRotateIndex(): Pair<Int, Int>? = mCurrentPairRotateIndex

    fun getAttractionsData(): Attractions? = mAttractionsData

}
