package com.taipeiTravelGuide.view.fragment.travelSpotPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.taipeiTravelGuide.ClassTrans
import com.taipeiTravelGuide.EAppUtil.serializable
import com.taipeiTravelGuide.databinding.FragmentTravelSpotBinding
import com.taipeiTravelGuide.model.Attractions

/**
 * 遊憩景點 - 內頁
 * */
class TravelSpotFragment : Fragment() {
    private var mBinding: FragmentTravelSpotBinding? = null
    private var mAttractionsData: Attractions? = null

    companion object {
        private const val EXTRA_ATTRACTIONS_DATA = "EXTRA_ATTRACTIONS_DATA"

        fun newBundle(pData: Attractions) {
            ClassTrans.setData(EXTRA_ATTRACTIONS_DATA, pData)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAttractionsData = ClassTrans.getData(EXTRA_ATTRACTIONS_DATA)
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
        mBinding?.apply {
            mAttractionsData?.images?.let { iImageList ->
                ivRotateBanner.showRotateBanner(iImageList)
            }
        }
    }

    private fun initListener() {
        mBinding?.apply {
//            tvGoToWebViewLatestNews.setOnClickListener {
//                findNavController().navigate(R.id.action_TravelSpotFragment_to_travelSpotWebViewFragment)
//            }

            ivBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    inner class TravelSpotViewModel : ViewModel() {

    }
}