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
import com.taipeiTravelGuide.EAppUtil.serializable
import com.taipeiTravelGuide.databinding.FragmentTravelSpotBinding
import com.taipeiTravelGuide.model.Attractions
import com.taipeiTravelGuide.view.customView.rotateBanner.ImageViewRotateBanner

/**
 * 遊憩景點 - 內頁
 * */
class TravelSpotFragment : Fragment() {
    private var mBinding: FragmentTravelSpotBinding? = null
    private var mAttractionsData: Attractions? = null
    private val mTravelSpotViewModel: TravelSpotViewModel by lazy {
        ViewModelProvider(this@TravelSpotFragment)[TravelSpotViewModel::class.java]
    }

    companion object {
        private const val EXTRA_ATTRACTIONS_DATA = "EXTRA_ATTRACTIONS_DATA"

        fun newBundle(pData: Attractions) {
            ClassTrans.setData(EXTRA_ATTRACTIONS_DATA, pData)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("TravelSpotFragment", "onCreate: ")
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("TravelSpotFragment", "onCreateView: ")
        mBinding = FragmentTravelSpotBinding.inflate(inflater, container, false)
        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("TravelSpotFragment", "onViewCreated: ")

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
                Log.d(
                    "TravelSpotFragment",
                    "initViews: $iImageList, CurrentIndex: ${mTravelSpotViewModel.getCurrentImageRotateIndex()}"
                )
                val iCurrentRotatePair = mTravelSpotViewModel.getCurrentImageRotateIndex()
                ivRotateBanner.showRotateBanner(
                    iImageList,
                    iCurrentRotatePair?.first ?: 1,
                    iCurrentRotatePair?.second ?: 0,
                )
            }
        }
    }

    private fun initListener() {
        mBinding?.apply {
//            tvGoToWebViewLatestNews.setOnClickListener {
//                findNavController().navigate(R.id.action_TravelSpotFragment_to_travelSpotWebViewFragment)
//            }

            ivRotateBanner.setOnRotateChangeListener(object :
                ImageViewRotateBanner.ItfRotateBannerCallBack {
                override fun onCurrentIndexChange(pPairImageAndIndicatorIndex: Pair<Int, Int>) {
                    mTravelSpotViewModel.setCurrentPairRotateIndex(pPairImageAndIndicatorIndex)
                }
            })

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
