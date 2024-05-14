package com.taipeiTravelGuide.view.fragment.travelSpotPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.taipeiTravelGuide.R
import com.taipeiTravelGuide.databinding.FragmentTravelSpotBinding

/**
 * 遊憩景點 - 內頁
 * */
class TravelSpotFragment : Fragment() {
    private var mBinding: FragmentTravelSpotBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        initListener()
    }

    private fun initListener() {
        mBinding?.apply {
            tvGoToWebViewLatestNews.setOnClickListener {
                findNavController().navigate(R.id.action_TravelSpotFragment_to_travelSpotWebViewFragment)
            }

            ivBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }
}