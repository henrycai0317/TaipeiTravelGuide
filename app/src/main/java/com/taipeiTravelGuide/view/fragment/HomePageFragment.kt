package com.taipeiTravelGuide.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.taipeiTravelGuide.R
import com.taipeiTravelGuide.databinding.FragmentHomePageBinding
import com.taipeiTravelGuide.view.TaipeiTravelApplication
import com.taipeiTravelGuide.view.dialog.BaseDialog
import com.taipeiTravelGuide.view.dialog.MultipleLanguageDialog
import com.taipeiTravelGuide.viewModel.TaipeiTravelViewModel

/**
 *  首頁
 * */
class HomePageFragment : Fragment() {

    private var mBinding: FragmentHomePageBinding? = null
    private var multipleDialog: MultipleLanguageDialog? = null
    private val mViewModel by activityViewModels<TaipeiTravelViewModel>()
    private var mMultipleLanguageItemId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        mViewModel.callAttractionsApi("pLanguageType")
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
        initViews()
        initListener()
        initObserver()
    }


    private fun initViews() {

    }

    private fun initListener() {
        mBinding?.apply {
            ivMultipleLanguage.setOnClickListener {
                activity?.let { iAct ->
                    multipleDialog?.show() ?: kotlin.run {
                        multipleDialog = MultipleLanguageDialog(mContext = iAct,
                            mSelectedId = mMultipleLanguageItemId,
                            object : BaseDialog.ItfDialogFinish {

                                override fun onFinish(pLanguageType: String) {
                                    super.onFinish(pLanguageType)
                                    //回傳API Language Type
                                    callApi(pLanguageType)
                                }

                                override fun onFinish(pSelectedId: Int) {
                                    super.onFinish(pSelectedId)
                                    //回傳ViewModel Selected Id
                                    mViewModel.setMultipleLanguageSelectedId(pSelectedId)
                                }
                            })
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

            ivSwitchDarkMode.setOnClickListener {
                var iIsDarkMode = mViewModel.isChangeDarkMode.value ?: false
                iIsDarkMode = !iIsDarkMode
                if (iIsDarkMode) {
                    mViewModel.setIsChangeDarkMode(true)
                } else {
                    mViewModel.setIsChangeDarkMode(false)
                }
                activity?.recreate()
            }
        }
    }

    /**
     *  打API
     * */
    private fun callApi(pLanguageType: String) {
        mViewModel.callAttractionsApi(pLanguageType)
        mViewModel.callEventsApi(pLanguageType)
    }

    private fun initObserver() {
        mBinding?.apply {
            mViewModel.isChangeDarkMode.observe(viewLifecycleOwner) { isChangeDarkMode ->
                if (isChangeDarkMode) {
                    ivSwitchDarkMode.setImageResource(R.drawable.half_moon)
                    TaipeiTravelApplication.isDarkModeEnabled = true
                } else {
                    ivSwitchDarkMode.setImageResource(R.drawable.sun)
                    TaipeiTravelApplication.isDarkModeEnabled = false
                }
            }
            mViewModel.multipleLanguageSelectedId.observe(viewLifecycleOwner) { isSelectedId ->
                multipleDialog?.setMultipleLanguageId(isSelectedId)
                mMultipleLanguageItemId = isSelectedId
            }
        }
    }

}