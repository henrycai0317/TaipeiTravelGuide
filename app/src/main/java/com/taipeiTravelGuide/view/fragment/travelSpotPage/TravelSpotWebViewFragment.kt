package com.taipeiTravelGuide.view.fragment.travelSpotPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.taipeiTravelGuide.databinding.FragmentWebViewTravelSpotBinding

/***
 *
 * 遊憩景點WebView - 遊憩景點的內頁
 */
class TravelSpotWebViewFragment : Fragment() {
    private var mBinding: FragmentWebViewTravelSpotBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentWebViewTravelSpotBinding.inflate(inflater, container, false)
        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
    }

    private fun initListener() {
        mBinding?.apply {
            ivBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }
}