package com.taipeiTravelGuide.view.fragment.homePage

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.taipeiTravelGuide.R
import com.taipeiTravelGuide.RecyclerViewLayoutManagerUtils.setLinearLayoutManager
import com.taipeiTravelGuide.databinding.FragmentHomePageBinding
import com.taipeiTravelGuide.view.TaipeiTravelApplication
import com.taipeiTravelGuide.view.dialog.BaseDialog
import com.taipeiTravelGuide.view.dialog.MultipleLanguageDialog
import com.taipeiTravelGuide.view.fragment.homePage.adapter.HomePageAdapter
import com.taipeiTravelGuide.viewModel.TaipeiTravelViewModel
import java.util.Locale

/**
 *  首頁
 * */
class HomePageFragment : Fragment() {

    private var mBinding: FragmentHomePageBinding? = null
    private var multipleDialog: MultipleLanguageDialog? = null
    private val mViewModel by activityViewModels<TaipeiTravelViewModel>()
    private var mMultipleLanguageItemId: Int? = null

    //HomePageAdapter
    private val mHomePageAdapter: HomePageAdapter by lazy {
        HomePageAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!mViewModel.mHasInitHomeFragment) {
            callApi("zh-tw")
            mViewModel.mHasInitHomeFragment = true
        }
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
        mBinding?.apply {
            //init RecyclerView
            context?.let { iContext ->
                rvHomeContent.setLinearLayoutManager(iContext)
                rvHomeContent.adapter = mHomePageAdapter
            }
        }
    }

    private fun initListener() {
        mBinding?.apply {
            val iItfHomePageAdapterItemClick = object : HomePageAdapter.ItfHomePageAdapterClick {
                override fun onHotNewsItemClick(pWebViewUrl: String) {
                    findNavController().navigate(R.id.action_HomePageFragment_to_HotNewsFragment)
                }
            }
            mHomePageAdapter.setOnClickItf(iItfHomePageAdapterItemClick)

            ivMultipleLanguage.setOnClickListener {
                activity?.let { iAct ->
                    multipleDialog?.show() ?: kotlin.run {
                        multipleDialog = MultipleLanguageDialog(mContext = iAct,
                            mSelectedId = mMultipleLanguageItemId,
                            object : BaseDialog.ItfDialogFinish {
                                override fun onFinish(pLanguageType: String, pSelectedId: Int) {
                                    //回傳API Language Type
                                    if (mViewModel.tampSelectedId != pSelectedId) {
                                        callApi(pLanguageType)
                                        activity?.let { iAct ->
                                            val newLocale =
                                                Locale(pLanguageType) // 新的語言代碼，例如中文簡體為"zh"
                                            updateLocale(iAct, newLocale)
                                            // 重新加載畫面以更新語言
                                            iAct.recreate()
                                        }
                                        //回傳ViewModel Selected Id
                                        mViewModel.setMultipleLanguageSelectedId(pSelectedId)
                                    }
                                }
                            })
                        multipleDialog?.show()
                    }
                }
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
     * 語系變更
     * */
    private fun updateLocale(context: Context, locale: Locale) {
        Locale.setDefault(locale)
        val configuration = Configuration()
        configuration.setLocale(locale)
        context.resources.updateConfiguration(configuration, context.resources.displayMetrics)
    }

    /**
     *  打API
     * */
    private fun callApi(pLanguageType: String) {
        mHomePageAdapter.setApiDataClearToShowShimmer()
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

            mViewModel.eventsApi.observe(viewLifecycleOwner) { iResEvents ->
                mHomePageAdapter.setResEventsData(iResEvents)
            }

            mViewModel.attractionsApi.observe(viewLifecycleOwner) { iResAttractions ->
                mHomePageAdapter.setResAttractionsData(iResAttractions)
            }
        }
    }

}