package com.taipeiTravelGuide.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.taipeiTravelGuide.R
import com.taipeiTravelGuide.databinding.FragmentHomePageBinding
import com.taipeiTravelGuide.view.dialog.MultipleLanguageDialog

/**
 *  首頁
 * */
class HomePageFragment : Fragment() {

    private var mBinding: FragmentHomePageBinding? = null
    private var multipleDialog:MultipleLanguageDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentHomePageBinding.inflate(inflater, container, false)
        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
    }

    private fun initListener() {
        mBinding?.apply {
            ivMultipleLanguage.setOnClickListener {
                activity?.let { iAct ->
                    multipleDialog?.show() ?: kotlin.run{
                        multipleDialog =  MultipleLanguageDialog(iAct)
                        multipleDialog?.show()
                    }
                }

            }
            tvGoToHotNews.setOnClickListener {
                findNavController().navigate(R.id.action_HomePageFragment_to_HotNewsFragment)
            }

            tvGoToTravelSpot.setOnClickListener {
                findNavController().navigate(R.id.action_HomePageFragment_to_TravelSpotFragment)
            }
        }
    }

}